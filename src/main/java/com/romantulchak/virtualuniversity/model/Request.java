package com.romantulchak.virtualuniversity.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.enumes.RequestStatus;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Request {

    @JsonView(Views.LessonStatusRequestView.class)
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @JsonView(Views.LessonStatusRequestView.class)
    private String userFullName;

    @JsonView(Views.LessonStatusRequestView.class)
    private String username;

    public Request() {

    }

    public Request(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
