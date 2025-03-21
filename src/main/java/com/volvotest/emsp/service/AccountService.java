package com.volvotest.emsp.service;

import com.volvotest.emsp.mapper.AccountMapper;
import com.volvotest.emsp.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private AccountMapper accountMapper;

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
}
