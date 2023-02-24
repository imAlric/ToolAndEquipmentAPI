package br.com.alec.RestAPI.exception.controller.Staff;

import br.com.alec.RestAPI.exception.Staff.StaffNotCreatedExcep;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class StaffNotCreated {
    @ResponseBody
    @ExceptionHandler(StaffNotCreatedExcep.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String staffNotCreated(StaffNotCreatedExcep e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StaffNotCreatedExcep methodStaffNotCreated(MethodArgumentNotValidException e){
        throw new StaffNotCreatedExcep();
    }
}
