package com.ultrawav.dynamodb_test.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestResponse {
    private String accountId;
    private String transactionId;
    private Account accountRequest;
    private Account accountResponse;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("AccountId")
    public String getAccountId() {
        return accountId;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("TransactionId")
    public String getTransactionId() {
        return transactionId;
    }

    @DynamoDbAttribute(value = "Request")
    public Account getCustomerRequest() {
        return accountRequest;
    }

    @DynamoDbAttribute(value = "Response")
    public Account getCustomerResponse() {
        return accountResponse;
    }

}
