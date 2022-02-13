package com.ultrawav.dynamodb_test.domain;

import lombok.Data;
import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

/*
aws dynamodb create-table \
--table-name AccountRequestResponse \
--attribute-definitions AttributeName=AccountId,AttributeType=S AttributeName=TransactionId,AttributeType=S \
--key-schema AttributeName=AccountId,KeyType=HASH AttributeName=TransactionId,KeyType=RANGE \
--provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
--region us-east-1 \
--endpoint http://localhost:9100/

 */

@DynamoDbBean
@Data
public class AccountRequestResponse {

    @Getter(onMethod_ = {@DynamoDbPartitionKey, @DynamoDbAttribute(value = "AccountId")})

    private String accountId;
    @Getter(onMethod_ = {@DynamoDbSortKey, @DynamoDbAttribute(value = "TransactionId")})
    private String transactionId;
    @Getter(onMethod_ = {@DynamoDbAttribute(value = "Request")})
    private Account accountRequest;
    @Getter(onMethod_ = {@DynamoDbAttribute(value = "Response")})
    private Account accountResponse;
}
