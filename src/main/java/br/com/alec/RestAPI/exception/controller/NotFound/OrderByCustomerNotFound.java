package br.com.alec.RestAPI.exception.controller.NotFound;

import br.com.alec.RestAPI.exception.Order.OrderByCustomerNotFoundExcep;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderByCustomerNotFound {
    @ResponseBody
    @ExceptionHandler(OrderByCustomerNotFoundExcep.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String orderByCustomerNotFound(OrderByCustomerNotFoundExcep e){
        return e.getMessage();
    }
}