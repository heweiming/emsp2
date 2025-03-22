package com.volvotest.emsp.service;

import com.volvotest.emsp.mapper.AccountMapper;
import com.volvotest.emsp.mapper.TokenMapper;
import com.volvotest.emsp.model.Account;
import com.volvotest.emsp.model.Token;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private TokenMapper tokenMapper;

    public void addAccount(Account account) {
        accountMapper.insertAccount(account);
        log.info("After insert database, added account: {}", account);
    }

    public Account getAccountById(Long id) {
        return accountMapper.getAccountById(id);
    }

    public List<Account> getAllAccounts() {
        return accountMapper.getAllAccounts();
    }

    public void updateAccount(Account account) {
        accountMapper.updateAccount(account);
    }

    public void deleteAccount(Long id) {
        accountMapper.deleteAccount(id);
    }

    public Token generateToken(Long accountId) {
        Account account = this.getAccountById(accountId);
        if (account == null) {
            throw new RuntimeException("Can't found account with id: " + accountId);
        }
        // generate token
        Date now = new Date();
        Date expireAt = new Date(now.getTime() + 1000 * 60 * 60);
        String token = generateRandomString(32);
        Token tokenEntity = new Token(-1L, accountId, token, expireAt, now, now);
        tokenMapper.insertToken(tokenEntity);
        return tokenEntity;
    }
    private String generateRandomString(int length) {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt((int) (Math.random() * candidateChars.length())));
        }
        return sb.toString();
    }

    public Token getTokenByAccountIdAndTokenId(Long accountId, Long tokenId) {
        return tokenMapper.getTokenById(tokenId);
    }

    public List<Token> getTokensByAccountIdAndLastUpdatedAt(Long accountId, Long lastUpdatedAt, int page, int pageSize) {

        return tokenMapper.getTokenByAccountId(accountId);
    }

    public List<Token> getTokensByAccountIdAndLastUpdatedAt(Long accountId, Long lastUpdateAt, Long lastId, int pageSize) {
        return tokenMapper.getTokensByAccountIdAndLastUpdatedAt(accountId, new Date(lastUpdateAt), lastId, pageSize);
    }
}
