package com.volvotest.emsp.controller;

import com.volvotest.emsp.model.Card;
import com.volvotest.emsp.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Card Controller", description = "Manage cards")
public class CardController {
    private static final Logger log = LoggerFactory.getLogger(CardController.class);
    @Autowired
    private CardService cardService;

    @Operation(summary = "Get all cards")
    @GetMapping("/cards")
    public List<Card> getCardList() {
        return cardService.getAllCards();
    }

    @Operation(summary = "Create a card")
    @PostMapping("/cards")
    public int createCard(@RequestBody Card card) {
        log.info("createCard with card: {}", card);
        cardService.addCard(card);
        log.info("After insert database, card: {}", card);
        return card.getId();
    }

    @Operation(summary = "Update a card")
    @PutMapping("/cards")
    public boolean updateCard(@RequestBody Card card) {
        log.info("updateCard with card: {}", card);
        cardService.updateCardStatus(card);
        return true;
    }

    @Operation(summary = "Delete a card")
    @DeleteMapping("/cards/{id}")
    public int deleteCard(@PathVariable String id) {
        log.info("delete card with id: {}", id);
        return cardService.deleteCard(Long.parseLong(id));
    }

    @Operation(summary = "Get a card by Id")
    @GetMapping("/cards/{id}")
    public Card getCardById(@PathVariable String id) {
        Card card = cardService.getCardById(Long.parseLong(id));
        log.info("get card by id: {}, card: {}", id, card);
        return card;
    }


}
