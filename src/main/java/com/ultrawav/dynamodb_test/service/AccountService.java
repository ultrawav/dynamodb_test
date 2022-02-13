package com.ultrawav.dynamodb_test.service;

import com.ultrawav.dynamodb_test.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private static final String modelPrefix = "account";

    public Account register(Account account) {
        account.setAccountId(modelPrefix + "-" + UUID.randomUUID());
        account.setProps(Arrays.asList("guest", "test"));

        return account;
    }
}
