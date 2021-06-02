package com.romantulchak.virtualuniversity.model;

import com.romantulchak.virtualuniversity.model.enumes.RequestStatus;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Request {

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    private String userFullName;

    public Request() {

    }

    public Request(RequestStatus requestStatus, String userFullName) {
        this.requestStatus = requestStatus;
        this.userFullName = userFullName;
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
}
