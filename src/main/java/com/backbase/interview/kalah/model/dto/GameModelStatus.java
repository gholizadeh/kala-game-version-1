package com.backbase.interview.kalah.model.dto;

import java.util.Map;

public class GameModelStatus {
    private Long id;
    private String uri;
    private Map<Integer, Integer> status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String url) {
        this.uri = url;
    }

    public Map<Integer, Integer> getStatus() {
        return status;
    }

    public void setStatus(Map<Integer, Integer> status) {
        this.status = status;
    }
}
