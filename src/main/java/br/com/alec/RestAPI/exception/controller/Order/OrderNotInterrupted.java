package br.com.alec.RestAPI.exception.controller.Order;

import br.com.alec.RestAPI.exception.Order.OrderNotInterruptedExcep;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderNotInterrupted {
    @ResponseBody
    @ExceptionHandler(OrderNotInterruptedExcep.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String orderAlreadyClosed(OrderNotInterruptedExcep e){
        return e.getMessage();
    }}