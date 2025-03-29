package com.volvotest.emsp.service;

import com.volvotest.emsp.mapper.CardMapper;
import com.volvotest.emsp.model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardMapper cardMapper;

    public void addCard(Card card) {
        cardMapper.insertCard(card);
    }

    public Card getCardById(Long id) {
        return cardMapper.getCardById(id);
    }

    public List<Card> getCardsByConstractId(String constractId) {
        return cardMapper.getCardsByContractId(constractId);
    }

    public boolean updateCardContractId(int id, String constractId) {
        return cardMapper.updateCardContractId(id, constractId) == 1;
    }

    public void updateCardStatus(Card card) {
        cardMapper.updateCardStatus(card);
    }

    public int deleteCard(Long id) {
        return cardMapper.deleteCard(id);
    }

    public List<Card> getAllCards() {
        return cardMapper.getAllCards();
    }
}
