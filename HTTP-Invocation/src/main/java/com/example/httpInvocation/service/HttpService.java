package com.example.httpInvocation.service;


import com.example.httpInvocation.dto.RequestDTO;
import com.example.httpInvocation.model.ApiMethod;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class HttpService {

    private static final Logger log = LoggerFactory.getLogger(HttpService.class);

    //private final WebClient.Builder webClientBuilder;
    private final WebClient.Builder webClientBuilder = WebClient.builder();

 //   @Async
//    public Mono<String> execute(ApiMethod apiMethod, RequestDTO request, int timeout) {
//        WebClient.RequestBodySpec req = webClientBuilder.build()
//                .method(org.springframework.http.HttpMethod.valueOf(apiMethod.name()))
//                .uri(request.getUrl())
//                .headers(headers -> {
//                    if (request.getHeaderVariables() != null) {
//                        request.getHeaderVariables().forEach(headers::add);
//                    }
//                });
//
//        Mono<String> responseMono;
//
//        if (apiMethod == ApiMethod.GET || apiMethod == ApiMethod.DELETE || apiMethod == ApiMethod.OPTIONS) {
//            responseMono = req.retrieve().bodyToMono(String.class);
//        } else {
//            responseMono = req
//                    .contentType(MediaType.parseMediaType(request.getBodyType() != null ? request.getBodyType() : "application/json"))
//                    .bodyValue(request.getRequestBody() != null ? request.getRequestBody() : "")
//                    .retrieve()
//                    .bodyToMono(String.class);
//        }
//
//        return responseMono
//                .timeout(Duration.ofMillis(timeout))
//                .doOnNext(body -> log.info("Received response from {}: {}", request.getUrl(), body))
//                .doOnError(error -> log.error("Error during HTTP call to {}: {}", request.getUrl(), error.getMessage(), error));
//    }
    
    //@Async
    public Mono<String> execute(ApiMethod apiMethod, RequestDTO request, int timeout) {
        String url = request.getUrl();
        if (url == null || url.isEmpty()) {
            log.error("URL is null or empty");
            return Mono.error(new IllegalArgumentException("URL cannot be null or empty"));
        }

        WebClient.RequestBodySpec req = webClientBuilder.build()
                .method(org.springframework.http.HttpMethod.valueOf(apiMethod.name()))
                .uri(url) // Use the validated URL
                .headers(headers -> {
                    if (request.getHeaderVariables() != null) {
                        request.getHeaderVariables().forEach(headers::add);
                    }
                });

            Mono<String> responseMono;

            if (apiMethod == ApiMethod.GET || apiMethod == ApiMethod.DELETE || apiMethod == ApiMethod.OPTIONS) {
                responseMono = req.retrieve().bodyToMono(String.class);
            } else {
                responseMono = req
                        .contentType(MediaType.parseMediaType(request.getBodyType() != null ? request.getBodyType() : "application/json"))
                        .bodyValue(request.getRequestBody() != null ? request.getRequestBody() : "")
                        .retrieve()
                        .bodyToMono(String.class);
            }

            return responseMono
                    .timeout(Duration.ofMillis(timeout))
                    .doOnNext(body -> log.info("Received response from {}: {}", request.getUrl(), body))
                    .doOnError(error -> log.error("Error during HTTP call to {}: {}", request.getUrl(), error.getMessage(), error));
       
    }
}

