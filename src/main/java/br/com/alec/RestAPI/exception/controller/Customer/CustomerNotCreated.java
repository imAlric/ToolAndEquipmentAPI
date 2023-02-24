package br.com.alec.RestAPI.exception.controller.Customer;

import br.com.alec.RestAPI.exception.Customer.CustomerNotCreatedExcep;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomerNotCreated {
    @ResponseBody
    @ExceptionHandler(CustomerNotCreatedExcep.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String customerNotCreated(CustomerNotCreatedExcep e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomerNotCreatedExcep methodCustomerNotCreated(MethodArgumentNotValidException e){
        throw new CustomerNotCreatedExcep();
    }
}
