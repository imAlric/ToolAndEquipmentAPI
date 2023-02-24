package br.com.alec.RestAPI.exception.controller.Tool;

import br.com.alec.RestAPI.exception.Tool.ToolNotCreatedExcep;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ToolNotCreated {
    @ResponseBody
    @ExceptionHandler(ToolNotCreatedExcep.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String toolNotCreated(ToolNotCreatedExcep e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ToolNotCreatedExcep methodToolNotCreated(MethodArgumentNotValidException e){
        throw new ToolNotCreatedExcep();
    }
}
