package com.example.httpInvocation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.httpInvocation.dto.RequestDTO;
import com.example.httpInvocation.model.ApiMethod;
import com.example.httpInvocation.service.HttpService;
import com.example.httpInvocation.service.RestFactoryAsync;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api")
public class Controller {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);

//    private final HttpService httpService;
//
//    public Controller(HttpService httpService) {
//        this.httpService = httpService;
//    }

    private final RestFactoryAsync restFactoryAsync;

    public Controller(RestFactoryAsync restFactoryAsync) {
        this.restFactoryAsync = restFactoryAsync;
    }

   
    @PostMapping("/invoke")
    public Mono<ResponseEntity<String>> invoke(@RequestBody RequestDTO requestDTO,
                                               @RequestParam(defaultValue = "5000") int timeout) {
        log.info("Received request to invoke URL: {}", requestDTO.getUrl());
        return restFactoryAsync.execute(requestDTO, timeout)
                .map(ResponseEntity::ok) // This line should work if execute returns Mono<String>
                .onErrorResume(ex -> {
                    log.error("Error invoking URL: {}", ex.getMessage());
                    return Mono.just(ResponseEntity.internalServerError().body("Error: " + ex.getMessage()));
                });
    }
  }

