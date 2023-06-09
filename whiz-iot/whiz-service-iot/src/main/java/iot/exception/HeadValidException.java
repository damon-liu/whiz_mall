package iot.exception;

import lombok.Data;

/**
 * @author zhangpeng
 * @title: HeadValidException
 * @date 2020-05-289:39
 */
@Data
public class HeadValidException extends RuntimeException {
    private String message;
    public HeadValidException(String message){
        super(message);
        this.message = message;
    }
}

