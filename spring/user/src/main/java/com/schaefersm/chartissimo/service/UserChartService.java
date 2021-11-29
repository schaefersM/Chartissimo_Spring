package com.schaefersm.chartissimo.service;

import com.schaefersm.chartissimo.exception.ChartNameTakenException;
import com.schaefersm.chartissimo.exception.UserChartNotFoundException;
import com.schaefersm.chartissimo.exception.UserChartsNotFoundException;
import com.schaefersm.chartissimo.model.Links;
import com.schaefersm.chartissimo.model.UserChart;
import com.schaefersm.chartissimo.model.UserCharts;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Service
public class UserChartService {

    @Value("${docker.api.host}")
    private String dockerHostname;

    @Value("${docker.api.port}")
    private String dockerPort = "8080";

    private final String chartCollection = "usercharts";

    private MongoTemplate mongoTemplate;

    @Autowired
    public UserChartService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Page<UserChart> getPaginatedCharts(ObjectId userId, int page, int size, List<String> host, List<String> type) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Query query = new Query();
            query.addCriteria(Criteria.where("user").is(userId));
            if (host != null) {
                query.addCriteria(Criteria.where("hosts").in(host));
            }
            if (type != null) {
                query.addCriteria(Criteria.where("chartType").in(type));
            }
            query.with(paging);
            List<UserChart> userCharts = mongoTemplate.find(query, UserChart.class, chartCollection);
            Page<UserChart> chartPage = PageableExecutionUtils.getPage(userCharts, paging, () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), UserChart.class));
            return chartPage;
        } catch (Exception e) {
            return null;
        }
    }

    public Links generateLinks(ObjectId userId, int page, int size, Page<UserChart> pageContent, HttpServletRequest request) {
        String[] splitQueryParams = request.getQueryString().split("&");
        String[] rangedQueryParams = Arrays.copyOfRange(splitQueryParams, 2, splitQueryParams.length);
        String paramString = "";
        for (String param : rangedQueryParams) {
            paramString += "&" + param;
        }
        String baseUrl = String.format("http://%s:%s/api/user/%s/charts", dockerHostname, dockerPort, userId);
        String self = String.format("%s?page=%s&size=%s%s", baseUrl, page, size, paramString);
        String next = (pageContent.hasNext()) ? String.format("%s?page=%s&size=%s%s", baseUrl, page + 1, size, paramString) : null;
        String prev = (pageContent.isFirst()) ? null : String.format("%s?page=%s&size=%s%s", baseUrl, page - 1, size,
                paramString);
        String first = String.format("%s?page=%s&size=%s%s", baseUrl, 0, size, paramString);
        String last = String.format("%s?page=%s&size=%s%s", baseUrl, pageContent.getTotalPages() - 1, size, paramString);
        return new Links(self, next, prev, first, last);
    }

    public UserCharts readAllCharts(ObjectId userId, int page, int size, List<String> host, List<String> type, HttpServletRequest request) {
        try {
            UserCharts response = new UserCharts();
            List<UserChart> userCharts;
            Links links;
            if (page < 0) {
                Query query = new Query();
                query.addCriteria(Criteria.where("user").is(userId));
                userCharts = mongoTemplate.find(query, UserChart.class, chartCollection);
                response.setResults(userCharts);
            } else {
                Page firstPage = getPaginatedCharts(userId, page, size, host, type);
                links = generateLinks(userId, page, size, firstPage, request);
                userCharts = firstPage.getContent();
                response.setResults(userCharts);
                response.setLinks(links);
            }
            return response;
        } catch (Exception e) {
            throw new UserChartsNotFoundException();
        }
    }

    public UserChart readOneChart(ObjectId userId, Double chartId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(chartId));
        query.addCriteria(Criteria.where("user").is(userId));
        query.fields().exclude("image");
        UserChart userChart = mongoTemplate.findOne(query, UserChart.class, chartCollection);
        if (userChart == null) {
            throw new UserChartNotFoundException();
        }
        return userChart;
    }

    public UserChart searchOneChartByName(ObjectId userId, String name) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("name").is(name));
            query.addCriteria(Criteria.where("user").is(userId));
            query.fields().exclude("image");
            UserChart userChart = mongoTemplate.findOne(query, UserChart.class, chartCollection);
            return userChart;
        } catch (Exception e) {
            return null;
        }
    }

    public void createOneChart(ObjectId userId, UserChart userChart) {
        UserChart nameExists = searchOneChartByName(userId, userChart.getName());
        if (nameExists == null) {
            userChart.setUser(userId);
            mongoTemplate.insert(userChart, chartCollection);
        } else {
            throw new ChartNameTakenException();
        }

    }

    @Async
    public void updateOneChart(ObjectId userId, Double chartId, UserChart newUserChart) {
        UserChart nameExists = searchOneChartByName(userId, newUserChart.getName());
        if (nameExists == null || chartId.equals(nameExists.getId())) {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(chartId));
            query.addCriteria(Criteria.where("user").is(userId));
            UserChart userChart = mongoTemplate.findOne(query, UserChart.class);
            if (userChart != null) {
                newUserChart.setCreatedAt(userChart.getCreatedAt());
                newUserChart.setChartType(userChart.getChartType());
                newUserChart.setUser(userChart.getUser());
                mongoTemplate.findAndReplace(query, newUserChart, chartCollection);
            } else {
                throw new UserChartNotFoundException();
            }
        } else {
            throw new ChartNameTakenException();
        }
    }

    public void deleteOneChart(ObjectId userId, Double chartId) {
        UserChart userChart = readOneChart(userId, chartId);
        mongoTemplate.remove(userChart, chartCollection);
    }
}
