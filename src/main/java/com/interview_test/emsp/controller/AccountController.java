package com.interview_test.emsp.controller;

import com.interview_test.emsp.model.Account;
import com.interview_test.emsp.model.Card;
import com.interview_test.emsp.service.AccountService;
import com.interview_test.emsp.service.CardService;
import com.interview_test.emsp.valueobject.AccountStatusVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Account Controller", description = "Manage accounts")
public class AccountController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;
    @Autowired
    CardService cardService;

    @Operation(summary = "Get all accounts")
    @GetMapping("/accounts")
    public List<Account> getAccountList() {
        return accountService.getAllAccounts();
    }

    @Operation(summary = "Create a account")
    @PostMapping("/accounts")
    public Long createAccount(@RequestBody Account account) {
        log.info("received account: {}", account);
        //contractId must be EMAID format
        String contractId = account.getContractId();
        if (contractId.matches("^[A-Z]{5}[A-Z0-9]{4}[0-9]{6}$")) {
            account.setContractId(contractId);
        } else {
            throw new RuntimeException("Invalid contractId: " + contractId);
        }
        accountService.addAccount(account);
        log.info("After insert database, account: {}", account);
        return account.getUid();
    }

    @Operation(summary = "Update a account status")
    @PutMapping("/accounts")
    public boolean updateAccountStatus(@RequestBody AccountStatusVO accountVO) {
        log.info("in updateAccount: received AccountStatusVO: {}", accountVO);

        accountService.updateAccount(accountVO);
        return true;
    }

    @Operation(summary = "Get a account by Id")
    @GetMapping("/accounts/{accountId}")
    public Account getAccountById(@PathVariable String accountId) {
        return accountService.getAccountById(Long.parseLong(accountId));
    }

    @Operation(summary = "Link a Card To Account")
    @PostMapping("/accounts/{accountId}/link")
    public Account linkCardToAccount(@PathVariable String accountId, @RequestBody String cardId) {
        log.info("link card: {} to account: {}", cardId, accountId);
        Account account = accountService.getAccountById(Long.parseLong(accountId));
        if (account == null) {
            throw new RuntimeException("Can't found account with id: " + accountId);
        }
        String contractId = account.getContractId();
        if (this.cardService.updateCardContractId(Integer.parseInt(cardId), contractId)) {
            List<Card> cardList = cardService.getCardsByConstractId(contractId);
            cardList.forEach(card -> {
                account.linkCardToAccount(card.getId());
            });
            return account;
        } {
            throw new RuntimeException("cardService.updateCardContractId("+cardId+", "+contractId+"): " + cardId);
        }
    }

    @Operation(summary = "Pagination query card")
    @GetMapping("/accounts/{accountId}/cards/last_updated_at/{lastUpdatedAt}/last_id/{lastId}/page_size/{pageSize}")
    public List<Card> getCardsByAccountIdAndLastUpdatedAt(@PathVariable Long accountId,
                                                          @PathVariable Long lastUpdatedAt,
                                                          @PathVariable long lastId,
                                                          @PathVariable int pageSize) {
        log.info("getTokensByAccountIdAndLastUpdatedAt for account: {} lastUpdatedAt: {} lastId: {} pageSize: {}", accountId, lastUpdatedAt, lastId, pageSize);
        return accountService.getCardsByAccountIdAndLastUpdatedAt(accountId, lastUpdatedAt, lastId, pageSize);
    }
}
