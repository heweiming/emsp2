package com.volvotest.emsp.valueobject;


public class CardVO {
    private long id;
    private String contractId;

    public CardVO(long id, String contractId) {
        this.id = id;
        this.contractId = contractId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getContractId() {
        return contractId;
    }
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }


    @Override
    public String toString() {
        return "CardVO{" +
                "id=" + id +
                ", contractId='" + contractId + '\'' +
                '}';
    }
}
