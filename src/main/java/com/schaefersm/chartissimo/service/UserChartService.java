package com.schaefersm.chartissimo.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mongodb.client.result.DeleteResult;
import com.schaefersm.chartissimo.model.UserChart;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class UserChartService {
    
    private String dockerHost = "192.168.72.132";
    private String dockerPort = "30005";

    @Autowired
    private MongoTemplate mongoTemplate;

    
    public Map<String, Object> readAllChartsPaginated(ObjectId userId, int page, int size, List<String> host, List<String> type, HttpServletRequest test) {
        try {
            Map<String, Object> response = new HashMap<>();
            Pageable paging = PageRequest.of(page, size);
            Query query = new Query();
            query.addCriteria(Criteria.where("user").is(userId));
            if(host != null){
                query.addCriteria(Criteria.where("hosts").in(host));
            }
            if(type != null) {
                query.addCriteria(Criteria.where("chartType").in(type));
            }
            query.with(paging);
            List<UserChart> chartPages = mongoTemplate.find(query, UserChart.class, "userchart");
            Page<UserChart> chartPage = PageableExecutionUtils.getPage(chartPages, paging, () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), UserChart.class));
            Map<String, Object> links = new HashMap<>();
            if(!chartPages.isEmpty()) {
                links = generateLinks(userId, page, size, chartPage, test);
            } else {
                return null;
            } 
            response.put("results", chartPage.getContent());
            response.put("links", links);
            return response;
        } catch (Exception e) {
            return null;
        }
    }
    
    public Map<String, Object> generateLinks(ObjectId userId, int page, int size, Page<UserChart> pageContent, HttpServletRequest test) {
        String [] splittedQueryParams = test.getQueryString().split("&");
        String [] rangedQueryParams = Arrays.copyOfRange(splittedQueryParams, 2, splittedQueryParams.length);
        String paramString = "";
        for(String param : rangedQueryParams) {
            System.out.println(param);
            paramString += "&" + param;
        }
        Map<String, Object> links = new HashMap<>();
        String baseUrl = String.format("http://%s:%s/api/user/%s/charts", dockerHost, dockerPort, userId);
        String self = String.format("%s?page=%s&size=%s", baseUrl, page, size);
        String next = (pageContent.hasNext()) ? String.format("%s?page=%s&size=%s&%s", baseUrl, page, size, paramString) : null;
        String prev = (pageContent.isFirst()) ? null : String.format("%s?page=%s&size=%s&%s", baseUrl, page-1, size, 
                paramString);
        String first = String.format("%s?page=%s&size=%s&%s", baseUrl, 0, size, paramString);
        String last = String.format("%s?page=%s&size=%s&%s", baseUrl, pageContent.getTotalPages()-1, size, 
                paramString);
        links.put("self", self);
        links.put("next", next);
        links.put("prev", prev);
        links.put("first", first);
        links.put("last", last);
        return links;
    }

    public Map<String, Object> readAllCharts(ObjectId userId, int page, int size, List<String> host, List<String> type, HttpServletRequest test) {

        //notPaginated
        if (page < 0) {
            try {
                Map<String, Object> response = new HashMap<>();
                Query query = new Query();
                query.addCriteria(Criteria.where("user").is(userId));
                List<UserChart> chartPages = mongoTemplate.find(query, UserChart.class, "userchart");
                response.put("results", chartPages);
                return response;
            } catch (Exception e) {
                return null;
            }
        } else {
            Map<String, Object> response = readAllChartsPaginated(userId, page, size, host, type, test);
            return response;
        }
    }

    public UserChart readOneChart(ObjectId userId, Double chartId) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(chartId)); 
            query.addCriteria(Criteria.where("user").is(userId));
            query.fields().exclude("image");
            UserChart userChart = mongoTemplate.findOne(query, UserChart.class, "userchart");
            return userChart;
        } catch (Exception e) {
            return null;
        }
    }

    public UserChart searchOneChartByName(ObjectId userId, String name) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("name").is(name));
            query.addCriteria(Criteria.where("user").is(userId));
            query.fields().exclude("image");
            UserChart userChart = mongoTemplate.findOne(query, UserChart.class, "userchart");
            return userChart;
        } catch (Exception e) {
            return null;
        }
    }

	public Map<String, Object> createOneChart(ObjectId userId, UserChart userChart) {
        UserChart nameExists = searchOneChartByName(userId, userChart.getName());
        Map<String, Object> response = new HashMap<>();
        if(nameExists == null) {
            try{
                userChart.setUser(userId);
                mongoTemplate.insert(userChart, "userchart");
                response.put("successMessage", "Chart created successfully");
                return response;
            } catch (Exception e) {
                return null;
            }
        } else {
            response.put("errorMessage", "Name was already taken");
            return response;
        }

	}

    @Async
    public Map<String, Object> updateOneChart(ObjectId userId, Double chartId, UserChart newUserChart) {
        UserChart nameExists = searchOneChartByName(userId, newUserChart.getName());
        Map<String, Object> response = new HashMap<>();
        if(nameExists == null || chartId.equals(nameExists.getId())) {
            try{
                Query query = new Query();
                query.addCriteria(Criteria.where("id").is(chartId));
                query.addCriteria(Criteria.where("user").is(userId));
                UserChart userChart = mongoTemplate.findOne(query, UserChart.class);
                if (userChart != null) {
                    newUserChart.setCreatedAt(userChart.getCreatedAt());
                    newUserChart.setChartType(userChart.getChartType());
                    newUserChart.setUser(userChart.getUser());
                    UserChart replacedChart = mongoTemplate.findAndReplace(query, newUserChart, "userchart");
                    if (replacedChart != null) {
                        response.put("successMessage", "Chart updated successfully");
                        return response;
                    }
                }
                response.put("errorMessage", "Can not find chart in database");
                return response;
            } catch (Exception e) {
                return null;
            }
        } else {
            response.put("errorMessage", "Name was already taken");
            return response;
        }
    }

    public Map<String, Object> deleteOneChart(ObjectId userId, Double chartId, Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();
        UserChart chartExists = readOneChart(userId, chartId);
        if (chartExists != null) {
            try{
                Query query = new Query();
                query.addCriteria(Criteria.where("user").is(userId));
                query.addCriteria(Criteria.where("id").is(chartId));
                query.addCriteria(Criteria.where("name").is(body.get("name")));
                DeleteResult deletedChart = mongoTemplate.remove(query, UserChart.class, "userchart");
                if (deletedChart.getDeletedCount() >= 1) {
                    response.put("successMessage", "Chart deleted successfully");
                    return response;
                } else {
                    response.put("errorMessage", "Can not find chart in database");
                    return response;
                }
            } catch (Exception e) {
                return null;
            }
        } else {
            response.put("errorMessage", "Can not find chart in database");
            return response;
        }
    }
}
