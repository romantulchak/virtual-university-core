package com.romantulchak.virtualuniversity.payload.request;

import com.romantulchak.virtualuniversity.model.enumes.RequestStatus;

public class ChangeStatusRequest {
    private long id;
    private RequestStatus status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

}
