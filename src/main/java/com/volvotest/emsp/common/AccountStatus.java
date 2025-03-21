package com.volvotest.emsp.common;

public enum AccountStatus {
    ACTIVED(1),
    DEACTIVED(0);

    private final int status;

    AccountStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
