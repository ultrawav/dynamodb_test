package com.ultrawav.dynamodb_test.service;

import com.ultrawav.dynamodb_test.domain.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    public Account register(Account account) {
        return account;
    }
}
