package br.com.alec.RestAPI.exception.controller.Order;

import br.com.alec.RestAPI.exception.Order.OrderNotCreatedExcep;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderNotCreated {
    @ResponseBody
    @ExceptionHandler(OrderNotCreatedExcep.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String orderNotCreated(OrderNotCreatedExcep e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public OrderNotCreatedExcep methodOrderNotCreated(MethodArgumentNotValidException e){
        throw new OrderNotCreatedExcep();
    }
}
