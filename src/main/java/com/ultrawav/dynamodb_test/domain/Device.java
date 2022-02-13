package com.ultrawav.dynamodb_test.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

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
@EqualsAndHashCode(callSuper = false)
@Data
public class Device extends AccountKey {
    @Getter(onMethod_ = {@DynamoDbAttribute(value = "DeviceType")})
    private String deviceType;
    @Getter(onMethod_ = {@DynamoDbAttribute(value = "DeviceName")})
    private String deviceName;
}
