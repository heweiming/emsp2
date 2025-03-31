package com.volvotest.emsp.service;

import com.volvotest.emsp.mapper.AccountMapper;
import com.volvotest.emsp.mapper.CardMapper;
import com.volvotest.emsp.model.Account;
import com.volvotest.emsp.model.Card;
import com.volvotest.emsp.model.Token;
import com.volvotest.emsp.valueobject.AccountStatusVO;
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
    private CardMapper cardMapper;


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

    public void updateAccount(AccountStatusVO accountStatusVO) {
        accountMapper.updateAccount(accountStatusVO);
    }

    public void deleteAccount(Long id) {
        accountMapper.deleteAccount(id);
    }

    private String generateRandomString(int length) {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt((int) (Math.random() * candidateChars.length())));
        }
        return sb.toString();
    }

    public List<Card> getCardsByAccountIdAndLastUpdatedAt(Long accountId, Long lastUpdateAt, Long lastId, int pageSize) {
        return cardMapper.getCardsByAccountIdAndLastUpdatedAt(accountId, new Date(lastUpdateAt), lastId, pageSize);
    }
}
