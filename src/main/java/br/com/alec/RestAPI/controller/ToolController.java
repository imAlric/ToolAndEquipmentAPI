package br.com.alec.RestAPI.controller;

import br.com.alec.RestAPI.exception.Tool.ToolNotFoundExcep;
import br.com.alec.RestAPI.model.Tool;
import br.com.alec.RestAPI.repository.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ToolController {
    @Autowired
    ToolRepository ToolRepo;

    //Encontrar ferramenta por um ID.
    @ResponseBody
    @GetMapping(value = "/tool/find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Tool findById(@PathVariable("id") long id){
        return ToolRepo.findById(id).orElseThrow(() -> new ToolNotFoundExcep(id));
    }

    //Encontrar todas as ferramentas ativas.
    @ResponseBody
    @GetMapping(value = "/tool/find/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tool> findActive(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Tool> found = ToolRepo.findActive(pageable);
        return found.getContent();
    }
}
