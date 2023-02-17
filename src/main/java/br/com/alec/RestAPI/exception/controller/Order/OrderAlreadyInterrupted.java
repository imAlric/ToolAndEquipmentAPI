package br.com.alec.RestAPI.exception.controller.Order;

import br.com.alec.RestAPI.exception.Order.OrderAlreadyInterruptedExcep;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderAlreadyInterrupted {
    @ResponseBody
    @ExceptionHandler(OrderAlreadyInterruptedExcep.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String orderAlreadyClosed(OrderAlreadyInterruptedExcep e){
        return e.getMessage();
    }}