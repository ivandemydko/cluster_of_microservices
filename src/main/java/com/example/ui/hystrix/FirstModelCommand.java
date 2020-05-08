package com.example.ui.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import model.firstmodel.FirstModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

/**
 * This Class was inserted just for example of probably using of HystrixCommand
 */
public class FirstModelCommand extends HystrixCommand<FirstModel> {


    private RestTemplate restTemplate;
    private HttpEntity<FirstModel> httpEntity;
    private String firstServiceURL;


    public FirstModelCommand(RestTemplate restTemplate, HttpHeaders httpHeaders, String firstServiceURL) {
        super(HystrixCommandGroupKey.Factory.asKey("default"));
        this.restTemplate = restTemplate;
        this.firstServiceURL = firstServiceURL;
        httpEntity = new HttpEntity<>(httpHeaders);
    }


    @Override
    protected FirstModel run() throws Exception {
        return restTemplate.exchange(firstServiceURL, HttpMethod.GET, httpEntity, FirstModel.class).getBody();
    }

    @Override
    protected FirstModel getFallback() {
        System.out.println("hit on fallback");
        return new FirstModel();
    }
}
