package com.romantulchak.virtualuniversity.payload.request;

import com.romantulchak.virtualuniversity.model.enumes.RequestDecision;

public class ChangeDecisionRequest {
    private long id;
    private RequestDecision requestDecision;
    private String comment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RequestDecision getRequestDecision() {
        return requestDecision;
    }

    public void setRequestDecision(RequestDecision requestDecision) {
        this.requestDecision = requestDecision;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
