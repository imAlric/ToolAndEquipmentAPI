package br.com.alec.RestAPI.controller;

import br.com.alec.RestAPI.exception.Customer.CustomerNotCreatedExcep;
import br.com.alec.RestAPI.exception.Customer.CustomerNotFoundExcep;
import br.com.alec.RestAPI.model.Customer;
import br.com.alec.RestAPI.model.Status;
import br.com.alec.RestAPI.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static br.com.alec.RestAPI.controller.OrderControllerTest.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    CustomerRepository CustomerRepo;

    @Test
    public void givenCustomerExists_whenFindById_thenReturnCustomer() throws Exception{
        Customer customer = new Customer(1,"Alec Fernando", "12345678901", "alec@gmail.com", "41999999999", Status.Active);
        Mockito.when(CustomerRepo.findById(customer.getId())).thenReturn(Optional.of(customer));
        mvc.perform(get("/customer/find/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void givenCustomerDontExists_whenFindById_thenReturn404() throws Exception{
        Mockito.when(CustomerRepo.findById(1L)).thenReturn(Optional.empty());
        mvc.perform(get("/customer/find/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomerNotFoundExcep));
    }

    @Test
    public void givenCustomerExists_whenFindByCPF_thenReturnCustomer() throws Exception{
        Customer customer = new Customer(1,"Alec Fernando", "12345678901", "alec@gmail.com", "41999999999", Status.Active);
        Mockito.when(CustomerRepo.findByCPF("12345678901")).thenReturn(Optional.of(customer));
        mvc.perform(get("/customer/find/cpf/12345678901")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void givenCustomerDontExists_whenFindByCPF_thenReturn404() throws Exception{
        Mockito.when(CustomerRepo.findByCPF("12345678901")).thenReturn(Optional.empty());
        mvc.perform(get("/customer/find/cpf/12345678901")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomerNotFoundExcep));
    }

    @Test
    public void givenThereIsAtLeastOneCustomerActive_whenFindActive_thenReturnPageListOfCustomer() throws Exception{
        List<Customer> list = Arrays.asList(
        new Customer(1,"Alec Fernando", "12345678901", "alec@gmail.com", "41999999999", Status.Active),
        new Customer(2,"José Rodrigues", "23456789212", "JOSE@gmail.com", "41999999999", Status.Active));
        PagedListHolder<Customer> customerPagedListHolder = new PagedListHolder<>(list);
        customerPagedListHolder.setPageSize(10);
        customerPagedListHolder.setPage(0);
        Page<Customer> customerPage = new PageImpl<>(customerPagedListHolder.getPageList(), PageRequest.of(0,10), list.size());
        Pageable pageable = PageRequest.of(0, 10);

        Mockito.when(CustomerRepo.findActive(pageable)).thenReturn(customerPage);
        mvc.perform(get("/customer/find/active")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    public void givenNoCustomerIsActive_whenFindActive_thenReturnEmptyPageList() throws Exception{
        List<Customer> list = List.of();
        PagedListHolder<Customer> customerPagedListHolder = new PagedListHolder<>(list);
        customerPagedListHolder.setPageSize(10);
        customerPagedListHolder.setPage(0);
        Page<Customer> customerPage = new PageImpl<>(customerPagedListHolder.getPageList(), PageRequest.of(0,10), 0);
        Pageable pageable = PageRequest.of(0, 10);

        Mockito.when(CustomerRepo.findActive(pageable)).thenReturn(customerPage);
        mvc.perform(get("/customer/find/active")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void givenCustomerIsCreated_whenCreateCustomer_thenReturn201() throws Exception{
        Customer customer = new Customer(1,"Alec Fernando", "12345678901", "alec@gmail.com", "41999999999", Status.Active);
        Mockito.when(CustomerRepo.save(customer)).thenReturn(customer);
        mvc.perform(post("/customer/new")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(customer)))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenCustomerIsNotCreated_whenCreateCustomer_thenReturn400AndCustomerNotCreated() throws Exception{
        Customer customer = new Customer(1,"Alec Fernando", "1234567890", "alec@gmail.com", "41999999999", Status.Active);
        Mockito.when(CustomerRepo.save(customer)).thenReturn(customer);
        mvc.perform(post("/customer/new")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(customer)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomerNotCreatedExcep));
    }
}
