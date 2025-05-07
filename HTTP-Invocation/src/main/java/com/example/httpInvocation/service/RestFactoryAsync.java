package com.example.httpInvocation.service;

import java.time.Duration;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.httpInvocation.dto.RequestDTO;

import reactor.core.publisher.Mono;

@Service
public class RestFactoryAsync {
	private static final Logger log = LoggerFactory.getLogger(RestFactoryAsync.class);
    private final WebClient webClient;

    public RestFactoryAsync(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<String> execute(RequestDTO request, int timeout) {
        String url = request.getUrl();
        if (url == null || url.isEmpty()) {
            return Mono.error(new IllegalArgumentException("URL cannot be null or empty"));
        }

        WebClient.RequestBodySpec requestSpec = webClient.post()
                .uri(url)
                .contentType(MediaType.parseMediaType(request.getBodyType() != null ? request.getBodyType() : "application/json"));

      
        if (request.getHeaderVariables() != null) {
            HttpHeaders headers = new HttpHeaders();
            for (Entry<String, String> entry : request.getHeaderVariables().entrySet()) {
                headers.add(entry.getKey(), entry.getValue());
            }
            requestSpec.headers(httpHeaders -> httpHeaders.addAll(headers));
        }

        String requestBody = request.getRequestBody() != null ? request.getRequestBody() : "";
        requestSpec.bodyValue(requestBody);

        return requestSpec.retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofMillis(timeout))
                .doOnError(error -> log.error("Error during HTTP call: {}", error.getMessage()));
    }
}