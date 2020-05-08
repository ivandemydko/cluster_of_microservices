package com.example.ui.service;

import model.AllModels;
import model.firstmodel.FirstModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope
public class AllModelsService {

    @Value("${firstServiceURL}")
    private String firstServiceURL;

    @Value("${secondServiceURL}")
    private String secondServiceURL;

    @Autowired
   private AccessTokenService accessTokenService;

    @Autowired
    private RestTemplate restTemplate;

    public AllModels getAllModelsViaFirstService() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", accessTokenService.getAccessToken());
        HttpEntity<FirstModel> httpEntity = new HttpEntity<>(httpHeaders);
        String url = firstServiceURL + "/allModels";
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, AllModels.class).getBody();
    }

    public AllModels getAllModelsViaSecondService() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", accessTokenService.getAccessToken());
        HttpEntity<FirstModel> httpEntity = new HttpEntity<>(httpHeaders);
        String url = secondServiceURL + "/allModels";
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, AllModels.class).getBody();
    }

}
