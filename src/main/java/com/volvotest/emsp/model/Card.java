package com.volvotest.emsp.model;

import com.volvotest.emsp.common.CardStatus;

public class Card {
    private int id;
    private String cardNumber;
    private String contractId;
    private CardStatus status;
    private int balance;

    public Card(int id, String cardNumber, String contractID, CardStatus status, int balance) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.contractId = contractID;
        this.status = status;
        this.balance = balance;
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

    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", contractId='" + contractId + '\'' +
                ", status=" + status +
                ", balance=" + balance +
                '}';
    }
}
