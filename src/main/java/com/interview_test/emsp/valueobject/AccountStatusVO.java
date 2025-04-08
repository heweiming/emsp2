package com.interview_test.emsp.valueobject;

import com.interview_test.emsp.common.AccountStatus;

public class AccountStatusVO {
    private long id;
    private AccountStatus status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public AccountStatusVO(long id, AccountStatus status) {
        this.id = id;
        this.status = status;
    }

    @Override
    public String toString() {
        return "AccountStatusVO{" +
                "id=" + id +
                ", state='" + status + '\'' +
                '}';
    }
}
