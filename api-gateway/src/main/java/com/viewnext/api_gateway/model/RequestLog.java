package com.viewnext.api_gateway.model;

import java.time.LocalDateTime;

public class RequestLog {
    private String method;
    private String url;
    private LocalDateTime timestamp;
    private String body;

    public RequestLog() {
    }

    public RequestLog(String method, String url, LocalDateTime timestamp, String body) {
        this.method = method;
        this.url = url;
        this.timestamp = timestamp;
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}