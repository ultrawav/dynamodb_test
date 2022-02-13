package com.ultrawav.dynamodb_test.advice;

import com.ultrawav.dynamodb_test.domain.Account;
import com.ultrawav.dynamodb_test.domain.AccountRequestResponse;
import com.ultrawav.dynamodb_test.domain.DomainConstants;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.concurrent.CompletableFuture;

@ControllerAdvice
public class ControllerResponseSaver implements ResponseBodyAdvice<Object> {
    private static final Logger logger = LoggerFactory.getLogger(ControllerResponseSaver.class);

    private final DynamoDbEnhancedAsyncClient dbEnhancedAsyncClient;

    public ControllerResponseSaver(DynamoDbEnhancedAsyncClient dbEnhancedAsyncClient) {
        this.dbEnhancedAsyncClient = dbEnhancedAsyncClient;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        saveResponse(body);
        return body;
    }

    private void saveResponse(Object object) {
        logger.info("saveResponse object is {}", object.getClass());
        if (object instanceof Account) {
            Account response = (Account) object;

            DynamoDbAsyncTable<AccountRequestResponse> accountRequestResponse
                    = dbEnhancedAsyncClient.table("AccountRequestResponse", TableSchema.fromBean(AccountRequestResponse.class));
            if (ThreadContext.get(DomainConstants.ACCOUNT_ID) != null) {
                CompletableFuture<AccountRequestResponse> item = accountRequestResponse.getItem(itemData -> {
                    itemData.consistentRead(true);
                    itemData.key(Key.builder().partitionValue(ThreadContext.get(DomainConstants.ACCOUNT_ID)).sortValue(ThreadContext.get(DomainConstants.TRANSACTION_ID)).build());

                    logger.info("itemData: {}", itemData);
                });

                item.whenCompleteAsync((data, exception) -> {
                    try {
                        if (data != null) {
                            data.setAccountResponse(response);
                            // accountRequestResponse.putItem(data);
                            accountRequestResponse.updateItem(data);
                        } else {
                            logger.error("No record Available for the accountId: {}",
                                    ThreadContext.get(DomainConstants.ACCOUNT_ID));
                        }
                    } catch (Exception e) {
                        logger.error("Error occured while updating response body for accountId: {}",
                                ThreadContext.get(DomainConstants.ACCOUNT_ID));
                    } finally {
                        removeThreadContext();
                    }
                });
            }
        } else {
            removeThreadContext();
        }
    }

    private void removeThreadContext() {
        logger.debug("Removing Thread Context");
        ThreadContext.remove(DomainConstants.ACCOUNT_ID);
        ThreadContext.remove(DomainConstants.TRANSACTION_ID);
    }
}
