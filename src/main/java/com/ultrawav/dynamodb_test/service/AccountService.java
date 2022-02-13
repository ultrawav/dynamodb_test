package com.ultrawav.dynamodb_test.service;

import com.ultrawav.dynamodb_test.domain.Account;
import com.ultrawav.dynamodb_test.domain.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.Arrays;
import java.util.UUID;

@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private static final String modelPrefix = "account";

    private final DynamoDbEnhancedAsyncClient dbEnhancedAsyncClient;

    public AccountService(DynamoDbEnhancedAsyncClient dbEnhancedAsyncClient) {
        this.dbEnhancedAsyncClient = dbEnhancedAsyncClient;
    }

    public Account register(Account account) {
        account.setKey(modelPrefix + "-" + UUID.randomUUID());
        account.setSk("USERINFO");
        account.setProps(Arrays.asList("guest", "test"));

        DynamoDbAsyncTable<Account> accountTable = dbEnhancedAsyncClient.table("Account", TableSchema.fromBean(Account.class));
        accountTable.putItem(account);

        return account;
    }

    public Device registerDevice(Device device) {

        logger.info("registerDevice({})", device);
        device.setSk("DEVICE");
        DynamoDbAsyncTable<Device> accountTable = dbEnhancedAsyncClient.table("Account", TableSchema.fromBean(Device.class));
        accountTable.putItem(device);

        return device;
    }
}
