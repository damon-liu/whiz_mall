package com.damon.oauth.exception;

import com.damon.common.exception.DefaultExceptionAdvice;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-15 15:03
 */
@ControllerAdvice
@Configuration
public class ExceptionAdvice extends DefaultExceptionAdvice {
}
