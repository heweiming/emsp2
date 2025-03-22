package com.volvotest.emsp.model;

import java.util.Date;

public class Token {
    private Long id;
    private Long accountId;
    private String token;
    private Date expiredAt;
    private Date createdAt;
    private Date lastUpdatedAt;

    public Token(Long id, Long accountId, String token, Date expiredAt, Date createdAt, Date lastUpdatedAt) {
        this.id = id;
        this.accountId = accountId;
        this.token = token;
        this.expiredAt = expiredAt;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", token='" + token + '\'' +
                ", expiredAt=" + expiredAt +
                ", createdAt=" + createdAt +
                ", lastUpdatedAt=" + lastUpdatedAt +
                '}';
    }
}
