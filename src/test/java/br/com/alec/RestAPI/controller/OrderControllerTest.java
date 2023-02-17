package br.com.alec.RestAPI.controller;

import br.com.alec.RestAPI.exception.Order.*;
import br.com.alec.RestAPI.model.*;
import br.com.alec.RestAPI.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    OrderRepository OrderRepo;
    @MockBean
    StaffRepository StaffRepo;
    @MockBean
    ToolRepository ToolRepo;
    @MockBean
    CustomerRepository CustomerRepo;
    @MockBean
    LogRepository LogRepo;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void givenThereIsAtLeastOneOrderPending_whenFindPending_thenReturnPageListOfOrder() throws Exception{
        Tool tool = new Tool(1,"Martelo", "Tramontina", Status.Active);
        Staff staff = new Staff(1,"Alec Fernando", "12413329919", "Atendente", Status.Active);
        Customer customer = new Customer(1,"José Rodrigues", "51938740963", "jose@gmail.com", "41987944006", Status.Active);

        Order order1 = new Order("Cabo partido no meio, cabeça do martelo caiu.", Date.valueOf("2023-02-13"), tool, customer, staff);
        order1.setId(1);
        order1.setStatus(Status.Pending);

        Order order2 = new Order("Cabeça achatada.", Date.valueOf("2023-02-13"), tool, customer, staff);
        order2.setId(2);
        order2.setStatus(Status.Pending);

        List<Order> list = Arrays.asList(order1, order2);
        PagedListHolder<Order> orderPagedListHolder = new PagedListHolder<>(list);
        orderPagedListHolder.setPageSize(10);
        orderPagedListHolder.setPage(0);
        Page<Order> orderPage = new PageImpl<>(orderPagedListHolder.getPageList(), PageRequest.of(0,10), list.size());
        Pageable pageable = PageRequest.of(0, 10);

        when(OrderRepo.findPending(pageable)).thenReturn(orderPage);
        mvc.perform(get("/order/find/pending")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    public void givenNoOrderIsPending_whenFindPending_thenReturnEmptyPageList() throws Exception{
        List<Order> list = List.of();
        PagedListHolder<Order> orderPagedListHolder = new PagedListHolder<>(list);
        orderPagedListHolder.setPageSize(10);
        orderPagedListHolder.setPage(0);
        Page<Order> orderPage = new PageImpl<>(orderPagedListHolder.getPageList(), PageRequest.of(0,10), 0);
        Pageable pageable = PageRequest.of(0, 10);

        when(OrderRepo.findPending(pageable)).thenReturn(orderPage);
        mvc.perform(get("/order/find/pending")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void givenThereIsAtLeastOneOrderClosed_whenFindClosed_thenReturnPageListOfOrder() throws Exception{
        Tool tool = new Tool(1,"Martelo", "Tramontina", Status.Active);
        Customer customer = new Customer(1,"José Rodrigues", "51938740963", "jose@gmail.com", "41987944006", Status.Active);
        Staff staff = new Staff(1,"Alec Fernando", "12413329919", "Atendente", Status.Active);

        Order order1 = new Order("Cabo partido no meio, cabeça do martelo caiu.", Date.valueOf("2023-02-13"), tool, customer, staff);
        order1.setId(1);
        order1.setStatus(Status.Closed);

        Order order2 = new Order("Cabeça achatada.", Date.valueOf("2023-02-13"), tool, customer, staff);
        order2.setId(2);
        order2.setStatus(Status.Closed);

        List<Order> list = Arrays.asList(order1, order2);
        PagedListHolder<Order> orderPagedListHolder = new PagedListHolder<>(list);
        orderPagedListHolder.setPageSize(10);
        orderPagedListHolder.setPage(0);
        Page<Order> orderPage = new PageImpl<>(orderPagedListHolder.getPageList(), PageRequest.of(0,10), list.size());
        Pageable pageable = PageRequest.of(0, 10);

        when(OrderRepo.findClosed(pageable)).thenReturn(orderPage);
        mvc.perform(get("/order/find/closed")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    public void givenNoOrderIsClosed_whenFindClosed_thenReturnEmptyPageList() throws Exception{
        List<Order> list = List.of();
        PagedListHolder<Order> orderPagedListHolder = new PagedListHolder<>(list);
        orderPagedListHolder.setPageSize(10);
        orderPagedListHolder.setPage(0);
        Page<Order> orderPage = new PageImpl<>(orderPagedListHolder.getPageList(), PageRequest.of(0,10), 0);
        Pageable pageable = PageRequest.of(0, 10);

        when(OrderRepo.findClosed(pageable)).thenReturn(orderPage);
        mvc.perform(get("/order/find/closed")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void givenOrderExist_whenFindById_thenReturnOrder() throws Exception{
        Order order = new Order("Cabo partido no meio, cabeça do martelo caiu.", Date.valueOf("2023-02-13"),
        new Tool(1,"Martelo", "Tramontina", Status.Active),
        new Customer(1,"José Rodrigues", "51938740963", "jose@gmail.com", "41987944006", Status.Active),
        new Staff(1,"Alec Fernando", "12413329919", "Atendente", Status.Active));
        order.setId(1);
        order.setStatus(Status.Pending);
        when(OrderRepo.findById(order.getId())).thenReturn(Optional.of(order));
        mvc.perform(get("/order/find/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void givenOrderDontExist_whenFindById_thenReturn404() throws Exception{
        when(OrderRepo.findById(1L)).thenReturn(Optional.empty());
        mvc.perform(get("/order/find/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenThereIsAtLeastOneOrder_whenFindByCustomerId_thenReturnPageListOfOrder() throws Exception{
        Tool tool = new Tool(1,"Martelo", "Tramontina", Status.Active);
        Staff staff = new Staff(1,"Alec Fernando", "12413329919", "Atendente", Status.Active);
        Customer customer = new Customer(1,"José Rodrigues", "51938740963", "jose@gmail.com", "41987944006", Status.Active);

        Order order1 = new Order("Cabo partido no meio, cabeça do martelo caiu.", Date.valueOf("2023-02-13"), tool, customer, staff);
        order1.setId(1);
        order1.setStatus(Status.Pending);

        Order order2 = new Order("Cabeça achatada.", Date.valueOf("2023-02-13"), tool, customer, staff);
        order2.setId(2);
        order2.setStatus(Status.Pending);

        List<Order> list = Arrays.asList(order1, order2);
        PagedListHolder<Order> orderPagedListHolder = new PagedListHolder<>(list);
        orderPagedListHolder.setPageSize(10);
        orderPagedListHolder.setPage(0);
        Page<Order> orderPage = new PageImpl<>(orderPagedListHolder.getPageList(), PageRequest.of(0,10), list.size());
        Pageable pageable = PageRequest.of(0, 10);

        when(OrderRepo.findByCustomerId(order2.getCustomer().getId(),pageable)).thenReturn(orderPage);
        mvc.perform(get("/order/find/customer/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].customer.id", is(1)));
    }

    @Test
    public void givenNoOrder_whenFindByCustomerId_thenReturn404AndOrderByCustomerNotFound() throws Exception{
        List<Order> list = List.of();
        PagedListHolder<Order> orderPagedListHolder = new PagedListHolder<>(list);
        orderPagedListHolder.setPageSize(10);
        orderPagedListHolder.setPage(0);
        Page<Order> orderPage = new PageImpl<>(orderPagedListHolder.getPageList(), PageRequest.of(0,10), 0);
        Pageable pageable = PageRequest.of(0, 10);

        when(OrderRepo.findByCustomerId(1L,pageable)).thenReturn(orderPage);
        mvc.perform(get("/order/find/customer/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderByCustomerNotFoundExcep));
    }

    @Test
    public void givenThereIsAtLeastOneOrderPending_whenFindPendingByCustomerId_thenReturnPageListOfOrder() throws Exception{
        Tool tool = new Tool(1,"Martelo", "Tramontina", Status.Active);
        Staff staff = new Staff(1,"Alec Fernando", "12413329919", "Atendente", Status.Active);
        Customer customer = new Customer(1,"José Rodrigues", "51938740963", "jose@gmail.com", "41987944006", Status.Active);

        Order order1 = new Order("Cabo partido no meio, cabeça do martelo caiu.", Date.valueOf("2023-02-13"), tool, customer, staff);
        order1.setId(1);
        order1.setStatus(Status.Pending);

        Order order2 = new Order("Cabeça achatada.", Date.valueOf("2023-02-13"), tool, customer, staff);
        order2.setId(2);
        order2.setStatus(Status.Pending);

        List<Order> list = Arrays.asList(order1, order2);
        PagedListHolder<Order> orderPagedListHolder = new PagedListHolder<>(list);
        orderPagedListHolder.setPageSize(10);
        orderPagedListHolder.setPage(0);
        Page<Order> orderPage = new PageImpl<>(orderPagedListHolder.getPageList(), PageRequest.of(0,10), list.size());
        Pageable pageable = PageRequest.of(0, 10);

        when(OrderRepo.findPendingByCustomerId(order2.getCustomer().getId(),pageable)).thenReturn(orderPage);
        mvc.perform(get("/order/find/customer/1/pending")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].customer.id", is(1)));
    }

    @Test
    public void givenNoOrderIsPending_whenFindPendingByCustomerId_thenReturn404AndOrderByCustomerNotFound() throws Exception{
        List<Order> list = List.of();
        PagedListHolder<Order> orderPagedListHolder = new PagedListHolder<>(list);
        orderPagedListHolder.setPageSize(10);
        orderPagedListHolder.setPage(0);
        Page<Order> orderPage = new PageImpl<>(orderPagedListHolder.getPageList(), PageRequest.of(0,10), 0);
        Pageable pageable = PageRequest.of(0, 10);

        when(OrderRepo.findPendingByCustomerId(1L,pageable)).thenReturn(orderPage);
        mvc.perform(get("/order/find/customer/1/pending")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderByCustomerNotFoundExcep));
    }

    @Test
    public void givenThereIsAtLeastOneOrderClosed_whenFindClosedByCustomerId_thenReturnPageListOfOrder() throws Exception{
        Tool tool = new Tool(1,"Martelo", "Tramontina", Status.Active);
        Staff staff = new Staff(1,"Alec Fernando", "12413329919", "Atendente", Status.Active);
        Customer customer = new Customer(1,"José Rodrigues", "51938740963", "jose@gmail.com", "41987944006", Status.Active);

        Order order1 = new Order("Cabo partido no meio, cabeça do martelo caiu.", Date.valueOf("2023-02-13"), tool, customer, staff);
        order1.setId(1);
        order1.setStatus(Status.Closed);

        Order order2 = new Order("Cabeça achatada.", Date.valueOf("2023-02-13"), tool, customer, staff);
        order2.setId(2);
        order2.setStatus(Status.Closed);

        List<Order> list = Arrays.asList(order1, order2);
        PagedListHolder<Order> orderPagedListHolder = new PagedListHolder<>(list);
        orderPagedListHolder.setPageSize(10);
        orderPagedListHolder.setPage(0);
        Page<Order> orderPage = new PageImpl<>(orderPagedListHolder.getPageList(), PageRequest.of(0,10), list.size());
        Pageable pageable = PageRequest.of(0, 10);

        when(OrderRepo.findClosedByCustomerId(order2.getCustomer().getId(),pageable)).thenReturn(orderPage);
        mvc.perform(get("/order/find/customer/1/closed")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].customer.id", is(1)));
    }

    @Test
    public void givenNoOrderIsClosed_whenFindClosedByCustomerId_thenReturn404AndOrderByCustomerNotFound() throws Exception{
        List<Order> list = List.of();
        PagedListHolder<Order> orderPagedListHolder = new PagedListHolder<>(list);
        orderPagedListHolder.setPageSize(10);
        orderPagedListHolder.setPage(0);
        Page<Order> orderPage = new PageImpl<>(orderPagedListHolder.getPageList(), PageRequest.of(0,10), 0);
        Pageable pageable = PageRequest.of(0, 10);

        when(OrderRepo.findClosedByCustomerId(1L,pageable)).thenReturn(orderPage);
        mvc.perform(get("/order/find/customer/1/closed")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderByCustomerNotFoundExcep));
    }

    @Test
    public void givenThereIsAtLeastOneOrder_whenFindByStaffId_thenReturnPageListOfOrder() throws Exception{
        Tool tool = new Tool(1,"Martelo", "Tramontina", Status.Active);
        Staff staff = new Staff(1,"Alec Fernando", "12413329919", "Atendente", Status.Active);
        Customer customer = new Customer(1,"José Rodrigues", "51938740963", "jose@gmail.com", "41987944006", Status.Active);

        Order order1 = new Order("Cabo partido no meio, cabeça do martelo caiu.", Date.valueOf("2023-02-13"), tool, customer, staff);
        order1.setId(1);
        order1.setStatus(Status.Pending);

        Order order2 = new Order("Cabeça achatada.", Date.valueOf("2023-02-13"), tool, customer, staff);
        order2.setId(2);
        order2.setStatus(Status.Pending);

        List<Order> list = Arrays.asList(order1, order2);
        PagedListHolder<Order> orderPagedListHolder = new PagedListHolder<>(list);
        orderPagedListHolder.setPageSize(10);
        orderPagedListHolder.setPage(0);
        Page<Order> orderPage = new PageImpl<>(orderPagedListHolder.getPageList(), PageRequest.of(0,10), list.size());
        Pageable pageable = PageRequest.of(0, 10);

        when(OrderRepo.findByStaffId(order2.getStaff().getId(),pageable)).thenReturn(orderPage);
        mvc.perform(get("/order/find/staff/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].staff.id", is(1)));
    }

    @Test
    public void givenNoOrder_whenFindByStaffId_thenReturn404AndOrderByStaffNotFound() throws Exception{
        List<Order> list = List.of();
        PagedListHolder<Order> orderPagedListHolder = new PagedListHolder<>(list);
        orderPagedListHolder.setPageSize(10);
        orderPagedListHolder.setPage(0);
        Page<Order> orderPage = new PageImpl<>(orderPagedListHolder.getPageList(), PageRequest.of(0,10), 0);
        Pageable pageable = PageRequest.of(0, 10);

        when(OrderRepo.findByStaffId(1L,pageable)).thenReturn(orderPage);
        mvc.perform(get("/order/find/staff/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderByStaffNotFoundExcep));
    }

    @Test
    public void givenThereIsAtLeastOneOrderPending_whenFindPendingByStaffId_thenReturnPageListOfOrder() throws Exception{
        Tool tool = new Tool(1,"Martelo", "Tramontina", Status.Active);
        Staff staff = new Staff(1,"Alec Fernando", "12413329919", "Atendente", Status.Active);
        Customer customer = new Customer(1,"José Rodrigues", "51938740963", "jose@gmail.com", "41987944006", Status.Active);

        Order order1 = new Order("Cabo partido no meio, cabeça do martelo caiu.", Date.valueOf("2023-02-13"), tool, customer, staff);
        order1.setId(1);
        order1.setStatus(Status.Pending);

        Order order2 = new Order("Cabeça achatada.", Date.valueOf("2023-02-13"), tool, customer, staff);
        order2.setId(2);
        order2.setStatus(Status.Pending);

        List<Order> list = Arrays.asList(order1, order2);
        PagedListHolder<Order> orderPagedListHolder = new PagedListHolder<>(list);
        orderPagedListHolder.setPageSize(10);
        orderPagedListHolder.setPage(0);
        Page<Order> orderPage = new PageImpl<>(orderPagedListHolder.getPageList(), PageRequest.of(0,10), list.size());
        Pageable pageable = PageRequest.of(0, 10);

        when(OrderRepo.findPendingByStaffId(order2.getStaff().getId(),pageable)).thenReturn(orderPage);
        mvc.perform(get("/order/find/staff/1/pending")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].staff.id", is(1)));
    }

    @Test
    public void givenNoOrderIsPending_whenFindPendingByStaffId_thenReturn404AndOrderByStaffNotFound() throws Exception{
        List<Order> list = List.of();
        PagedListHolder<Order> orderPagedListHolder = new PagedListHolder<>(list);
        orderPagedListHolder.setPageSize(10);
        orderPagedListHolder.setPage(0);
        Page<Order> orderPage = new PageImpl<>(orderPagedListHolder.getPageList(), PageRequest.of(0,10), 0);
        Pageable pageable = PageRequest.of(0, 10);

        when(OrderRepo.findPendingByStaffId(1L,pageable)).thenReturn(orderPage);
        mvc.perform(get("/order/find/staff/1/pending")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderByStaffNotFoundExcep));
    }

    @Test
    public void givenThereIsAtLeastOneOrderClosed_whenFindClosedByStaffId_thenReturnPageListOfOrder() throws Exception{
        Tool tool = new Tool(1,"Martelo", "Tramontina", Status.Active);
        Staff staff = new Staff(1,"Alec Fernando", "12413329919", "Atendente", Status.Active);
        Customer customer = new Customer(1,"José Rodrigues", "51938740963", "jose@gmail.com", "41987944006", Status.Active);

        Order order1 = new Order("Cabo partido no meio, cabeça do martelo caiu.", Date.valueOf("2023-02-13"), tool, customer, staff);
        order1.setId(1);
        order1.setStatus(Status.Closed);

        Order order2 = new Order("Cabeça achatada.", Date.valueOf("2023-02-13"), tool, customer, staff);
        order2.setId(2);
        order2.setStatus(Status.Closed);

        List<Order> list = Arrays.asList(order1, order2);
        PagedListHolder<Order> orderPagedListHolder = new PagedListHolder<>(list);
        orderPagedListHolder.setPageSize(10);
        orderPagedListHolder.setPage(0);
        Page<Order> orderPage = new PageImpl<>(orderPagedListHolder.getPageList(), PageRequest.of(0,10), list.size());
        Pageable pageable = PageRequest.of(0, 10);

        when(OrderRepo.findClosedByStaffId(order2.getStaff().getId(),pageable)).thenReturn(orderPage);
        mvc.perform(get("/order/find/staff/1/closed")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].staff.id", is(1)));
    }

    @Test
    public void givenNoOrderIsClosed_whenFindClosedByStaffId_thenReturn404AndOrderByStaffNotFound() throws Exception{
        List<Order> list = List.of();
        PagedListHolder<Order> orderPagedListHolder = new PagedListHolder<>(list);
        orderPagedListHolder.setPageSize(10);
        orderPagedListHolder.setPage(0);
        Page<Order> orderPage = new PageImpl<>(orderPagedListHolder.getPageList(), PageRequest.of(0,10), 0);
        Pageable pageable = PageRequest.of(0, 10);

        when(OrderRepo.findClosedByStaffId(1L,pageable)).thenReturn(orderPage);
        mvc.perform(get("/order/find/staff/1/closed")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderByStaffNotFoundExcep));
    }

    @Test
    public void givenNewOrderIsCreated_whenSaveOrder_thenReturn201() throws Exception{
        Order order = new Order("Cabo partido no meio, cabeça do martelo caiu.", Date.valueOf("2023-02-13"),
        new Tool("Martelo", "Tramontina", Status.Active),
        new Customer("José Rodrigues", "51938740963", "jose@gmail.com", "41987944006", Status.Active),
        new Staff("Alec Fernando", "12413329919", "Atendente", Status.Active));

        mvc.perform(post("/order/new")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(order)))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenNewOrderIsNotCreated_whenSaveOrder_thenReturn400AndOrderNotCreated() throws Exception{
        Order order = new Order();

        mvc.perform(post("/order/new")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(order)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderNotCreatedExcep));
    }

    @Test
    public void givenNoOrder_whenInterruptOrder_thenReturn404AndOrderNotFound() throws Exception{
        when(OrderRepo.findById(1L)).thenReturn(Optional.empty());

        mvc.perform(put("/order/interrupt/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"action_desc\":\"Teste\",\n\"staff\":{\"id\":\"1\"}}"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderNotFoundExcep));
    }

    @Test
    public void givenOrderIsAlreadyInterrupted_whenInterruptOrder_thenReturn409AndOrderAlreadyInterrupted() throws Exception{
        Order order = new Order("Cabo partido no meio, cabeça do martelo caiu.", Date.valueOf("2023-02-13"),
        new Tool("Martelo", "Tramontina", Status.Active),
        new Customer("José Rodrigues", "51938740963", "jose@gmail.com", "41987944006", Status.Active),
        new Staff("Alec Fernando", "12413329919", "Atendente", Status.Active));
        order.setId(1L);
        order.setStatus(Status.Interrupted);

        when(OrderRepo.findById(1L)).thenReturn(Optional.of(order));

        mvc.perform(put("/order/interrupt/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"action_desc\":\"Teste\",\n\"staff\":{\"id\":\"1\"}}"))
                .andExpect(status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderAlreadyInterruptedExcep));
    }

    @Test
    public void givenOrderIsAlreadyClosed_whenInterruptOrder_thenReturn409AndOrderAlreadyClosed() throws Exception{
        Order order = new Order("Cabo partido no meio, cabeça do martelo caiu.", Date.valueOf("2023-02-13"),
        new Tool("Martelo", "Tramontina", Status.Active),
        new Customer("José Rodrigues", "51938740963", "jose@gmail.com", "41987944006", Status.Active),
        new Staff("Alec Fernando", "12413329919", "Atendente", Status.Active));
        order.setId(1L);
        order.setStatus(Status.Closed);

        when(OrderRepo.findById(1L)).thenReturn(Optional.of(order));

        mvc.perform(put("/order/interrupt/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"action_desc\":\"Teste\",\n\"staff\":{\"id\":\"1\"}}"))
                .andExpect(status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderAlreadyClosedExcep));
    }

    @Test
    public void givenOrderWasInterrupted_whenInterruptOrder_thenReturn200() throws Exception{
        Order order = new Order("Cabo partido no meio, cabeça do martelo caiu.", Date.valueOf("2023-02-13"),
        new Tool("Martelo", "Tramontina", Status.Active),
        new Customer("José Rodrigues", "51938740963", "jose@gmail.com", "41987944006", Status.Active),
        new Staff("Alec Fernando", "12413329919", "Atendente", Status.Active));
        order.setId(1L);
        order.setStatus(Status.Pending);

        when(OrderRepo.findById(1L)).thenReturn(Optional.of(order));

        mvc.perform(put("/order/interrupt/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"action_desc\":\"Teste\",\n\"staff\":{\"id\":\"1\"}}"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenOrderThrowsException_whenInterruptOrder_thenReturn400AndOrderNotUpdated() throws Exception{
        when(OrderRepo.findById(1L)).thenThrow(OrderNotUpdatedExcep.class);

        mvc.perform(put("/order/interrupt/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"action_desc\":\"Teste\",\n\"staff\":{\"id\":\"1\"}}"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderNotUpdatedExcep));
    }

    @Test
    public void givenNoOrder_whenResumeOrder_thenReturn404AndOrderNotFound() throws Exception{
        when(OrderRepo.findById(1L)).thenReturn(Optional.empty());

        mvc.perform(put("/order/resume/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"action_desc\":\"Teste\",\n\"staff\":{\"id\":\"1\"}}"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderNotFoundExcep));
    }

    @Test
    public void givenOrderIsNotInterrupted_whenResumeOrder_thenReturn409AndOrderNotInterrupted() throws Exception{
        Order order = new Order("Cabo partido no meio, cabeça do martelo caiu.", Date.valueOf("2023-02-13"),
        new Tool("Martelo", "Tramontina", Status.Active),
        new Customer("José Rodrigues", "51938740963", "jose@gmail.com", "41987944006", Status.Active),
        new Staff("Alec Fernando", "12413329919", "Atendente", Status.Active));
        order.setId(1L);
        order.setStatus(Status.Pending);

        when(OrderRepo.findById(1L)).thenReturn(Optional.of(order));

        mvc.perform(put("/order/resume/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"action_desc\":\"Teste\",\n\"staff\":{\"id\":\"1\"}}"))
                .andExpect(status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderNotInterruptedExcep));
    }

    @Test
    public void givenOrderWasResumed_whenResumeOrder_thenReturn200() throws Exception{
        Order order = new Order("Cabo partido no meio, cabeça do martelo caiu.", Date.valueOf("2023-02-13"),
        new Tool("Martelo", "Tramontina", Status.Active),
        new Customer("José Rodrigues", "51938740963", "jose@gmail.com", "41987944006", Status.Active),
        new Staff("Alec Fernando", "12413329919", "Atendente", Status.Active));
        order.setId(1L);
        order.setStatus(Status.Interrupted);

        when(OrderRepo.findById(1L)).thenReturn(Optional.of(order));

        mvc.perform(put("/order/resume/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"action_desc\":\"Teste\",\n\"staff\":{\"id\":\"1\"}}"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenOrderThrowsException_whenResumeOrder_thenReturn400AndOrderNotUpdated() throws Exception{
        when(OrderRepo.findById(1L)).thenThrow(OrderNotUpdatedExcep.class);

        mvc.perform(put("/order/resume/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"action_desc\":\"Teste\",\n\"staff\":{\"id\":\"1\"}}"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderNotUpdatedExcep));
    }

    @Test
    public void givenNoOrder_whenCloseOrder_thenReturn404AndOrderNotFound() throws Exception{
        when(OrderRepo.findById(1L)).thenReturn(Optional.empty());

        mvc.perform(put("/order/close/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"action_desc\":\"Teste\",\n\"staff\":{\"id\":\"1\"}}"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderNotFoundExcep));
    }

    @Test
    public void givenOrderIsAlreadyClosed_whenCloseOrder_thenReturn409AndOrderAlreadyClosed() throws Exception{
        Order order = new Order("Cabo partido no meio, cabeça do martelo caiu.", Date.valueOf("2023-02-13"),
        new Tool("Martelo", "Tramontina", Status.Active),
        new Customer("José Rodrigues", "51938740963", "jose@gmail.com", "41987944006", Status.Active),
        new Staff("Alec Fernando", "12413329919", "Atendente", Status.Active));
        order.setId(1L);
        order.setStatus(Status.Closed);

        when(OrderRepo.findById(1L)).thenReturn(Optional.of(order));

        mvc.perform(put("/order/close/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"action_desc\":\"Teste\",\n\"staff\":{\"id\":\"1\"}}"))
                .andExpect(status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderAlreadyClosedExcep));
    }

    @Test
    public void givenOrderWasClosed_whenCloseOrder_thenReturn200() throws Exception{
        Order order = new Order("Cabo partido no meio, cabeça do martelo caiu.", Date.valueOf("2023-02-13"),
        new Tool("Martelo", "Tramontina", Status.Active),
        new Customer("José Rodrigues", "51938740963", "jose@gmail.com", "41987944006", Status.Active),
        new Staff("Alec Fernando", "12413329919", "Atendente", Status.Active));
        order.setId(1L);
        order.setStatus(Status.Pending);

        when(OrderRepo.findById(1L)).thenReturn(Optional.of(order));

        mvc.perform(put("/order/close/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"action_desc\":\"Teste\",\n\"staff\":{\"id\":\"1\"}}"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenOrderThrowsException_whenCloseOrder_thenReturn400AndOrderNotUpdated() throws Exception{
        when(OrderRepo.findById(1L)).thenThrow(OrderNotUpdatedExcep.class);

        mvc.perform(put("/order/close/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"action_desc\":\"Teste\",\n\"staff\":{\"id\":\"1\"}}"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderNotUpdatedExcep));
    }

    @Test
    public void givenNoOrder_whenDeleteOrder_thenReturn404AndOrderNotFound() throws Exception{
        when(OrderRepo.findById(1L)).thenReturn(Optional.empty());

        mvc.perform(delete("/order/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderNotFoundExcep));
    }

    @Test
    public void givenOrderWasDeleted_whenDeleteOrder_thenReturn200() throws Exception{
        Order order = new Order("Cabo partido no meio, cabeça do martelo caiu.", Date.valueOf("2023-02-13"),
        new Tool("Martelo", "Tramontina", Status.Active),
        new Customer("José Rodrigues", "51938740963", "jose@gmail.com", "41987944006", Status.Active),
        new Staff("Alec Fernando", "12413329919", "Atendente", Status.Active));
        order.setId(1L);
        order.setStatus(Status.Pending);

        when(OrderRepo.findById(1L)).thenReturn(Optional.of(order));

        mvc.perform(delete("/order/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void givenOrderThrowsException_whenDeleteOrder_thenReturn400AndOrderNotUpdated() throws Exception{
        when(OrderRepo.findById(1L)).thenThrow(OrderNotUpdatedExcep.class);

        mvc.perform(delete("/order/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"action_desc\":\"Teste\",\n\"staff\":{\"id\":\"1\"}}"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderNotUpdatedExcep));
    }
}
