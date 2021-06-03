package com.romantulchak.virtualuniversity.payload.response;

import com.romantulchak.virtualuniversity.model.enumes.RequestStatus;

public class ChangeStatusResponse {
    private long id;
    private RequestStatus requestStatus;
    private String userFullName;
    private String username;

    public ChangeStatusResponse(long id, RequestStatus requestStatus, String userFullName, String username) {
        this.id = id;
        this.requestStatus = requestStatus;
        this.userFullName = userFullName;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
