package com.ultrawav.dynamodb_test.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Account {
    private String accountId;
    private String accountName;
    private String nickname;
}
