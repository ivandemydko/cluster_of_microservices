package com.example.ui.service;

import model.firstmodel.FirstModel;
import model.secondmodel.SecondModel;
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
public class SecondModelService {

    @Value("${secondServiceURL}")
    private String secondServiceURL;

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private RestTemplate restTemplate;


    public SecondModel getSecondModel() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", accessTokenService.getAccessToken());
        System.out.println("Access token: " + accessTokenService.getAccessToken());
        HttpEntity<FirstModel> httpEntity = new HttpEntity<>(httpHeaders);
        String url = secondServiceURL + "/secondModel/second_model_1";
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, SecondModel.class).getBody();
    }


}
