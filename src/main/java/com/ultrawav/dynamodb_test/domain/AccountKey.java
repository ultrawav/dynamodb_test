package com.ultrawav.dynamodb_test.domain;

import lombok.Data;
import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Data
public class AccountKey {
    public static final String KeyPrefix = "account-";

    @Getter(onMethod_ = {@DynamoDbPartitionKey, @DynamoDbAttribute(value = "key")})
    private String key;
    @Getter(onMethod_ = {@DynamoDbSortKey, @DynamoDbAttribute(value = "sk")})
    private String sk;

}
