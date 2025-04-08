package com.interview_test.emsp.model;

import com.interview_test.emsp.common.CardStatus;

import java.util.Date;

public class Card {
    private int id;
    private String cardNumber;
    private String contractId;
    private CardStatus status;
    private int balance;
    private Date createdAt;
    private Date lastUpdatedAt;
    private Date expiredAt; // 24hours from now

    public Card(int id, String cardNumber, String contractID, CardStatus status, int balance, Date expiredAt, Date createdAt, Date lastUpdatedAt) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.contractId = contractID;
        this.status = status;
        this.balance = balance;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.expiredAt = expiredAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
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

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", contractId='" + contractId + '\'' +
                ", status=" + status +
                ", balance=" + balance +
                ", createdAt=" + createdAt +
                ", lastUpdatedAt=" + lastUpdatedAt +
                ", expiredAt=" + expiredAt +
                '}';
    }
}
