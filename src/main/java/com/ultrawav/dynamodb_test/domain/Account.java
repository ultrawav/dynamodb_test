package com.ultrawav.dynamodb_test.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.List;

/*
aws dynamodb create-table \
 --table-name Account \
 --attribute-definitions AttributeName=key,AttributeType=S AttributeName=sk,AttributeType=S \
 --key-schema AttributeName=key,KeyType=HASH AttributeName=sk,KeyType=RANGE \
 --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
 --region us-east-1 \
 --endpoint http://localhost:9100/

 */

@DynamoDbBean
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Account {

    public static final String KeyPrefix = "account-";

    @Getter(onMethod_={@DynamoDbPartitionKey, @DynamoDbAttribute(value = "key")})
    private String key;
    @Getter(onMethod_={@DynamoDbSortKey, @DynamoDbAttribute(value = "sk")})
    private String sk;
    private String accountName;
    private String nickname;
    private List<String> props;
}
