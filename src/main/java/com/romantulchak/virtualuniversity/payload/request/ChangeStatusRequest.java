package com.romantulchak.virtualuniversity.payload.request;

import com.romantulchak.virtualuniversity.model.enumes.RequestStatus;

public class ChangeStatusRequest {
    private long id;
    private RequestStatus requestStatus;

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
}
