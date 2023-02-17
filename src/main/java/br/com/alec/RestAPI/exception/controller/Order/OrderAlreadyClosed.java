package br.com.alec.RestAPI.exception.controller.Order;

import br.com.alec.RestAPI.exception.Order.OrderAlreadyClosedExcep;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderAlreadyClosed {
    @ResponseBody
    @ExceptionHandler(OrderAlreadyClosedExcep.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String orderAlreadyClosed(OrderAlreadyClosedExcep e){
        return e.getMessage();
    }}