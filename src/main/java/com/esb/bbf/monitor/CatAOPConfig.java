package com.esb.bbf.monitor;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.esb.bbf.api.serverless.ApiContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CatAOPConfig {

    /**
     * aop
     *
     * @param pjp        pjp
     * @param catProfile catProfile
     * @return Object Object
     * @throws Throwable Throwable
     */
    @Around("@annotation(catProfile)")
    public Object aroundManagerLogPoint(final ProceedingJoinPoint pjp, final CatProfile catProfile) throws Throwable {
        StringBuffer transNameBuffer = new StringBuffer();
        transNameBuffer.append(pjp.getSignature().getDeclaringType().getSimpleName())
                .append(".")
                .append(pjp.getSignature().getName())
                .append(".")
                .append(ApiContext.apiId());
        String transName = transNameBuffer.toString();
        if (!catProfile.name().isEmpty()) {
            transName = catProfile.name();
        }
        Transaction t = Cat.newTransaction(catProfile.type(), transName);
        try {
            Object result = pjp.proceed();
            t.setStatus(Transaction.SUCCESS);
            return result;
        } catch (Throwable e) {
            t.setStatus(e);
            Cat.logError(e);
            throw e;
        } finally {
            t.complete();
        }
    }
}
