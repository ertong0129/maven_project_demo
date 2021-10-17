package com.example.demo.aop;

import com.example.demo.exception.DemoException;
import com.example.demo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ExceptionHandleAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<Void> validExceptionHandler(MethodArgumentNotValidException validException) {
        FieldError fieldError = validException.getBindingResult().getFieldError();
        String message = Objects.nonNull(fieldError) ? fieldError.getDefaultMessage() : "";
        return Result.buildFail(message);
    }

    /**
     * 业务异常处理
     *
     * @param e SharkException
     * @return Result
     */
    @ExceptionHandler(DemoException.class)
    public Result<Void> sharkExceptionHandler(DemoException e) {
        return Result.buildFail(e.getMessage());
    }

    /**
     * 系统异常处理
     *
     * @param e Exception
     * @return ResultVO
     */
    @ExceptionHandler
    public Result<Void> handler(Exception e) {
        log.error("系统异常 message:[{}]", e.getMessage(), e);
        return Result.buildFail("系统异常");
    }

}
