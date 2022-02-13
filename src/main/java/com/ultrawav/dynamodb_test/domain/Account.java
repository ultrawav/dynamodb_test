package com.ultrawav.dynamodb_test.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

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

@EqualsAndHashCode(callSuper = false)
@DynamoDbBean
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Account extends AccountKey {

    private String accountName;
    private String nickname;
    private List<String> props;
}
