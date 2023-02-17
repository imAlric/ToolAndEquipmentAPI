package br.com.alec.RestAPI.controller;

import br.com.alec.RestAPI.exception.Staff.StaffNotFoundExcep;
import br.com.alec.RestAPI.model.Staff;
import br.com.alec.RestAPI.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StaffController {
    @Autowired
    StaffRepository StaffRepo;

    //Encontrar funcionário por um ID.
    @ResponseBody
    @GetMapping(value = "/staff/find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Staff findById(@PathVariable("id") long id){
        return StaffRepo.findById(id).orElseThrow(() -> new StaffNotFoundExcep(id));
    }

    //Encontrar todos os funcionários ativos.
    @ResponseBody
    @GetMapping(value = "/staff/find/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Staff> findActive(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Staff> found = StaffRepo.findActive(pageable);
        return found.getContent();
    }
}
