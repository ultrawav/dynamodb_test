package com.ultrawav.dynamodb_test.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.util.List;

/*
 */

@DynamoDbBean
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Account {

    public static final String KeyPrefix = "account-";

    private String accountId;
    private String accountName;
    private String nickname;
    private List<String> props;
}
