package com.esb.bbf.config;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.nio.charset.Charset;

/**
 * Description
 * <p>
 * </p>
 * DATE 2018/5/3.
 *
 * @author yaorui.
 */
public class HystrixErrorDecoder implements ErrorDecoder {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Exception decode(final String s, final Response response) {
        Exception exception = new Exception(String.format("%s,%s", s, response.toString()));
        try {
            if (HttpStatus.BAD_REQUEST.value() <= response.status()
                    && response.status() < HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                exception = new HystrixBadRequestException(IOUtils
                        .toString(response.body().asInputStream(), Charset.forName("UTF-8")));
            } else {
                logger.error(exception.getMessage(), exception);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return exception;
    }
}
