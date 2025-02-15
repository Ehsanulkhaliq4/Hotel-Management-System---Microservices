package com.lcwd.gateway.controllers;

import com.lcwd.gateway.models.AuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private Logger logger= LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/login")
    private ResponseEntity<AuthResponse> login(
           @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client
           ,@AuthenticationPrincipal OidcUser user,
           Model model
    )
    {
        logger.info("User email id : {}"+user.getEmail());
        //creating auth response object
        AuthResponse response =new AuthResponse();
        //setting email to auth response
        response.setUserId(user.getEmail());
        //setting token to auth response
        response.setAccessToken(client.getAccessToken().getTokenValue());

        response.setRefreshToken(client.getRefreshToken().getTokenValue());

        response.setExpireAt(client.getAccessToken().getExpiresAt().getEpochSecond());

        List<String> authorties = user.getAuthorities().stream().map(grantedAuthority -> {
            return grantedAuthority.getAuthority();
        }).collect(Collectors.toList());
        response.setAuthorities(authorties);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
