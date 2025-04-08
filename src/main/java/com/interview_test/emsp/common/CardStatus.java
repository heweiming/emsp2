package com.interview_test.emsp.common;

public enum CardStatus {
    ACTIVED(1),
    DEACTIVED(0);

    private final int status;

    CardStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
