package br.com.alec.RestAPI.controller;

import br.com.alec.RestAPI.exception.Customer.CustomerNotFoundExcep;
import br.com.alec.RestAPI.model.Customer;
import br.com.alec.RestAPI.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    CustomerRepository CustomerRepo;

    //Encontrar cliente por um ID.
    @ResponseBody
    @GetMapping(value = "/customer/find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer findById(@PathVariable("id") long id){
        return CustomerRepo.findById(id).orElseThrow(() -> new CustomerNotFoundExcep(id));
    }

    //Encontrar todos os clientes ativos.
    @ResponseBody
    @GetMapping(value = "/customer/find/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> findActive(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> found = CustomerRepo.findActive(pageable);
        return found.getContent();
    }
}
