package com.volvotest.emsp.controller;

import com.volvotest.emsp.model.Account;
import com.volvotest.emsp.common.AccountStatus;
import com.volvotest.emsp.model.Token;
import com.volvotest.emsp.service.AccountService;
import com.volvotest.emsp.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;
    @Autowired
    CardService cardService;
    @GetMapping("/accounts")
    public List<Account> getAccountList() {
        return accountService.getAllAccounts();
        //String contractId = "SEVOLXC40123455";
        //return List.of(new Account("123456", "tohe@qq.com", "heweiming", contractId, AccountStatus.ACTIVED));
    }
    @PostMapping("/accounts")
    public Long createAccount(@RequestBody Account account) {
        log.info("received account: {}", account);
        accountService.addAccount(account);
        log.info("After insert database, account: {}", account);
        return account.getUid();
    }
    @PutMapping("/accounts")
    public boolean updateAccount(@RequestBody Account account) {
        log.info("in updateAccount: received account: {}", account);
        accountService.updateAccount(account);
        return true;
    }

    @GetMapping("/accounts/{accountId}")
    public Account getAccountById(@PathVariable String accountId) {
        return accountService.getAccountById(Long.parseLong(accountId));
    }

    @PostMapping("/accounts/{accountId}/link")
    public boolean linkCardToAccount(@PathVariable String accountId, @RequestBody String cardId) {
        log.info("link card: {} to account: {}", cardId, accountId);
        Account account = accountService.getAccountById(Long.parseLong(accountId));
        if (account == null) {
            throw new RuntimeException("Can't found account with id: " + accountId);
        }
        String contractId = account.getContractId();
        return this.cardService.updateCardContractId(Integer.parseInt(cardId), contractId);
    }

    @PostMapping("/accounts/{accountId}/generate_token")
    public Token generateToken(@PathVariable Long accountId) {
        log.info("generate token for account: {}", accountId);
        return accountService.generateToken(accountId);
    }
    @GetMapping("/accounts/{accountId}/tokens/{tokenId}")
    public Token getTokenByAccountIdAndTokenId(@PathVariable Long accountId, @PathVariable Long tokenId) {
        log.info("getTokenByAccountIdAndTokenId token: {} for account: {}", tokenId, accountId);
        Token token = accountService.getTokenByAccountIdAndTokenId(accountId, tokenId);
        log.info("getTokenByAccountIdAndTokenId token: {}", token);
        return token;
    }

    @GetMapping("/accounts/{accountId}/tokens/last_updated_at/{lastUpdatedAt}/last_id/{lastId}/page_size/{pageSize}")
    public List<Token> getTokensByAccountIdAndLastUpdatedAt(@PathVariable Long accountId,
                                                            @PathVariable Long lastUpdatedAt,
                                                            @PathVariable long lastId,
                                                            @PathVariable int pageSize) {
        log.info("getTokensByAccountIdAndLastUpdatedAt for account: {} lastUpdatedAt: {} lastId: {} pageSize: {}", accountId, lastUpdatedAt, lastId, pageSize);
        return accountService.getTokensByAccountIdAndLastUpdatedAt(accountId, lastUpdatedAt, lastId, pageSize);
    }
}
