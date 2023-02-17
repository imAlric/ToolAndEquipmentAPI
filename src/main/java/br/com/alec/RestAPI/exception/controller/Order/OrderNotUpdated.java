package br.com.alec.RestAPI.exception.controller.Order;

import br.com.alec.RestAPI.exception.Order.OrderNotUpdatedExcep;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderNotUpdated {
    @ResponseBody
    @ExceptionHandler(OrderNotUpdatedExcep.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String orderNotUpdated(OrderNotUpdatedExcep e){
        return e.getMessage();
    }}