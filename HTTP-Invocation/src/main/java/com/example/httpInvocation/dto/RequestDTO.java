package com.example.httpInvocation.dto;

import java.util.List;
import java.util.Map;

public class RequestDTO {
    private String url;
    private Map<String, String> headerVariables; 
    private List<Map<String, String>> params; 
    private String bodyType;
    private String requestBody;

   
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Map<String, String> getHeaderVariables() {
        return headerVariables;
    }
    public void setHeaderVariables(Map<String, String> headerVariables) {
        this.headerVariables = headerVariables;
    }
    public List<Map<String, String>> getParams() {
        return params;
    }
    public void setParams(List<Map<String, String>> params) {
        this.params = params;
    }
    public String getBodyType() {
        return bodyType;
    }
    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }
    public String getRequestBody() {
        return requestBody;
    }
    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }
}