package br.com.alec.RestAPI.exception.controller.NotFound;

import br.com.alec.RestAPI.exception.Tool.ToolNotFoundExcep;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ToolNotFound {
    @ResponseBody
    @ExceptionHandler(ToolNotFoundExcep.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String orderNotFound(ToolNotFoundExcep e){
        return e.getMessage();
    }
}
