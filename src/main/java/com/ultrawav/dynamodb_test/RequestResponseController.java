package com.ultrawav.dynamodb_test;

import com.ultrawav.dynamodb_test.domain.Account;
import com.ultrawav.dynamodb_test.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestResponseController {

    private final AccountService accountService;

    public RequestResponseController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("sendAccount")
    public Account getAccount(@RequestBody Account account) {
        return accountService.register(account);

    }
}
