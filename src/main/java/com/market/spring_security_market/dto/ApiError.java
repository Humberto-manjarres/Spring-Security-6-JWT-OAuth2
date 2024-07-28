package com.market.spring_security_market.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class ApiError implements Serializable {

    private String backenMessage;
    private String message;
    private String url;
    private String method;
    private LocalDateTime timeStamp;
    private List<String> messages;

    public String getBackenMessage() {
        return backenMessage;
    }

    public void setBackenMessage(String backenMessage) {
        this.backenMessage = backenMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
