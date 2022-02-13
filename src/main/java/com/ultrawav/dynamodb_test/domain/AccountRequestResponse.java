package com.ultrawav.dynamodb_test.domain;

import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

/*
aws dynamodb create-table --table-name AccountRequestResponse --attribute-definitions AttributeName=AccountId,AttributeType=S AttributeName=TransactionId,AttributeType=S --key-schema AttributeName=AccountId,KeyType=HASH AttributeName=TransactionId,KeyType=RANGE --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 --region us-east-1 --endpoint http://localhost:9100/

 */

@DynamoDbBean
@Setter
public class AccountRequestResponse {
    private String accountId;
    private String transactionId;
    private Account accountRequest;
    private Account accountResponse;

    @DynamoDbPartitionKey
    @DynamoDbAttribute(value = "AccountId")
    public String getAccountId() {
        return accountId;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute(value = "TransactionId")
    public String getTransactionId() {
        return transactionId;
    }

    @DynamoDbAttribute(value = "Request")
    public Account getAccountRequest() {
        return accountRequest;
    }

    @DynamoDbAttribute(value = "Response")
    public Account getAccountResponse() {
        return accountResponse;
    }
}
