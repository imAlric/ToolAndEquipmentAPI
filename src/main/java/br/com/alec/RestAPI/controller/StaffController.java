package br.com.alec.RestAPI.controller;

import br.com.alec.RestAPI.exception.Staff.StaffNotCreatedExcep;
import br.com.alec.RestAPI.exception.Staff.StaffNotFoundExcep;
import br.com.alec.RestAPI.model.Staff;
import br.com.alec.RestAPI.repository.StaffRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    //Encontrar funcionário por CPF.
    @ResponseBody
    @GetMapping(value="/staff/find/cpf/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Staff findByCPF(@PathVariable("cpf") String cpf){
        return StaffRepo.findByCPF(cpf).orElseThrow(() -> new StaffNotFoundExcep(cpf));
    }

    //Cadastrar um novo funcionário.
    @ResponseBody
    @PostMapping(value = "/staff/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Staff> createStaff(@Valid @RequestBody Staff newStaff, BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new StaffNotCreatedExcep();
        if((StaffRepo.findById(newStaff.getId()).isPresent()) || (StaffRepo.findByCPF(newStaff.getCpf())).isPresent()) throw new StaffNotCreatedExcep();
        return new ResponseEntity<>(StaffRepo.save(newStaff), HttpStatus.CREATED);
    }
}
