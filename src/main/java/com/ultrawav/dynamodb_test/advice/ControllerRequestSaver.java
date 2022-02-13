package com.ultrawav.dynamodb_test.advice;

import com.ultrawav.dynamodb_test.domain.Account;
import com.ultrawav.dynamodb_test.domain.AccountRequestResponse;
import com.ultrawav.dynamodb_test.domain.DomainConstants;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.UUID;

@ControllerAdvice
public class ControllerRequestSaver extends RequestBodyAdviceAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ControllerRequestSaver.class);

    private final DynamoDbEnhancedAsyncClient dbEnhancedAsyncClient;

    public ControllerRequestSaver(DynamoDbEnhancedAsyncClient dbEnhancedAsyncClient) {
        this.dbEnhancedAsyncClient = dbEnhancedAsyncClient;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }


    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        setThreadContext(body);
        saveRequest(body);
        return body;
    }

    private void saveRequest(Object body) {
        DynamoDbAsyncTable<AccountRequestResponse> accountRequestResponse = dbEnhancedAsyncClient.table("AccountRequestResponse", TableSchema.fromBean(AccountRequestResponse.class));
        logger.info("Request body for accountId: {} saving", ThreadContext.get(DomainConstants.ACCOUNT_ID));
        logger.info("Request body: {} saving", body);

        if (ThreadContext.get(DomainConstants.ACCOUNT_ID) != null) {
            AccountRequestResponse request = new AccountRequestResponse();
            request.setAccountId(ThreadContext.get(DomainConstants.ACCOUNT_ID));
            request.setTransactionId(ThreadContext.get(DomainConstants.TRANSACTION_ID));
            request.setAccountRequest((Account) body);
            accountRequestResponse.putItem(request);
            logger.debug("Request body for accountId: {} saved", ThreadContext.get(DomainConstants.ACCOUNT_ID));
        }

    }

    private void setThreadContext(Object body) {
        if (body instanceof Account) {
            Account account = (Account) body;
            if (account.getKey() == null) account.setKey(Account.KeyPrefix + UUID.randomUUID());
            ThreadContext.put(DomainConstants.ACCOUNT_ID, account.getKey());
            ThreadContext.put(DomainConstants.TRANSACTION_ID, String.valueOf(Instant.now().getEpochSecond()));
        }
    }
}
