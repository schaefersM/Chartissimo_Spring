package com.schaefersm.chartissimo.controller;

import java.util.HashMap;

import com.schaefersm.chartissimo.dto.UserConfigDto;
import com.schaefersm.chartissimo.model.Config;
import com.schaefersm.chartissimo.model.UserConfig;
import com.schaefersm.chartissimo.service.UserConfigService;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserConfigController {

    @Autowired
    private UserConfigService userConfigService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{userId}/config")
    public ResponseEntity<Config> readUserConfig(@PathVariable("userId") ObjectId userId) {
        UserConfig userConfig = userConfigService.getUserConfig(userId);
        Config userConfigDto = modelMapper.map(userConfig.getConfig(), Config.class);
        return ResponseEntity.status(HttpStatus.OK).body(userConfigDto);
    }

    @PostMapping("/{userId}/config")
    public ResponseEntity<?> createUserConfig(@PathVariable("userId") ObjectId userId,
                                              @RequestBody UserConfig userConfig) {
        userConfigService.createUserConfig(userId, userConfig.getConfig());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
