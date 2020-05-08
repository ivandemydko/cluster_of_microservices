package com.example.ui.service;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;


@Service
public class AccessTokenService {

    public String getAccessToken() {
        OAuth2AuthenticationDetails authenticationDetails = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return authenticationDetails.getTokenType().concat(" ").concat(authenticationDetails.getTokenValue());

    }

}
