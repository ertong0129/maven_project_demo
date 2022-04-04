package com.example.demo.business.config.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.function.Consumer;

/**
 * Created by ChangJie on 2021/9/14 21:07.
 */
@Component
public class TransactionTemplateHelper {

    private static Logger log = LoggerFactory.getLogger(TransactionTemplateHelper.class);

    @Resource
    private TransactionTemplate transactionTemplate;

    public boolean doTransaction(Consumer consumer){
        return transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                try {
                    consumer.accept(null);
                    return true;
                } catch (Exception e) {
                    status.setRollbackOnly();
                    log.error("事务执行出错", e);
                    throw e;
                }
            }
        });


    }
}
