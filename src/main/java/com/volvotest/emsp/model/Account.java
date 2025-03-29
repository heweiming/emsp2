package com.volvotest.emsp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.volvotest.emsp.common.AccountStatus;
import com.volvotest.emsp.valueobject.CardVO;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private Long uid;
    private String email;
    private String name;
    private String contractId;
    private AccountStatus status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CardVO> cardList= new ArrayList<>();

    public Account(long uid, String email, String name, String contractId, AccountStatus status) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.contractId = contractId;
        this.status = status;
    }
    public String getEmail() {
        return email;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public boolean linkCardToAccount(long cardId) {
        for (CardVO card : cardList) {
            if (card.getId() == cardId) {
                return false; // Card already linked
            }
        }
        CardVO newCard = new CardVO(cardId, contractId);
        cardList.add(newCard);
        return true;
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public List<CardVO> getCardList() {
        return cardList;
    }

    @Override
    public String toString() {
        return "Account{" +
                "uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", name='" + contractId + '\'' +
                ", status=" + status +
                '}';
    }
}
