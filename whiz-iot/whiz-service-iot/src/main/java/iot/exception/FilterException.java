package iot.exception;

import lombok.Data;

/**
 * @author zhangpeng
 * @title: HeadValidException
 * @date 2020-05-289:39
 */
@Data
public class FilterException extends RuntimeException {
    private String message;
    public FilterException(String message){
        super(message);
        this.message = message;
    }
}

