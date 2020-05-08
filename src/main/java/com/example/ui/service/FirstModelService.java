package com.example.ui.service;


import com.example.ui.hystrix.CommonHystrixCommand;
import com.example.ui.hystrix.FirstModelCommand;
import com.netflix.hystrix.HystrixCommand;
import model.firstmodel.FirstModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@RefreshScope
public class FirstModelService {


    @Value("${firstServiceURL}")
    private String firstServiceURL;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AccessTokenService tokenService;

    @Autowired
    private HystrixCommand.Setter setter;


    public FirstModel getFirstModel() throws ExecutionException, InterruptedException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", tokenService.getAccessToken());
        HttpEntity<FirstModel> httpEntity = new HttpEntity<>(httpHeaders);
        String url = firstServiceURL + "/firstModel/first_model_1";


        CommonHystrixCommand<FirstModel> firstModelCommonHystrixCommand = new CommonHystrixCommand<>(setter,
                () -> restTemplate.exchange(url, HttpMethod.GET, httpEntity, FirstModel.class).getBody(),
                () -> {
                    FirstModel firstModel = new FirstModel();
                    firstModel.setName("fallback model");
                    return firstModel;
                });

        Future<FirstModel> firstModelFuture = firstModelCommonHystrixCommand.queue();
        return firstModelFuture.get();

    }


//    public FirstModel getFirstModel() {                   //todo              provides same function, but without circuit breaker pattern
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization", tokenService.getAccessToken());
//        HttpEntity<FirstModel> httpEntity = new HttpEntity<>(httpHeaders);
//        String url = firstServiceURL + "/firstModel/first_model_1";
//        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, FirstModel.class).getBody();
//    }


//    public FirstModel getFirstModel() {                //todo              provides same function by using FirstModelCommand
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization", tokenService.getAccessToken());
//        FirstModelCommand firstModelCommand = new FirstModelCommand(restTemplate,httpHeaders,firstServiceURL+"/firstModel/first_model_1");
//        return firstModelCommand.execute();
//    }

}
