package br.com.alec.RestAPI.controller;

import br.com.alec.RestAPI.exception.Order.*;
import br.com.alec.RestAPI.model.Log;
import br.com.alec.RestAPI.model.Order;
import br.com.alec.RestAPI.model.Status;
import br.com.alec.RestAPI.repository.*;
import br.com.alec.RestAPI.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderRepository OrderRepo;
    @Autowired
    OrderService orderService;
    @Autowired
    StaffRepository StaffRepo;
    @Autowired
    ToolRepository ToolRepo;
    @Autowired
    CustomerRepository CustomerRepo;
    @Autowired
    LogRepository LogRepo;

    //Encontrar todas as ordens de serviço pendentes.
    @ResponseBody
    @GetMapping(value = "/order/find/pending", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> findPend(@RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> found = OrderRepo.findPending(pageable);
        return found.getContent();
    }

    //Encontrar todas as ordens de serviços baixadas.
    @ResponseBody
    @GetMapping(value = "/order/find/closed", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> findClosed(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> found = OrderRepo.findClosed(pageable);
        return found.getContent();
    }

    //Encontrar ordem de serviço por ID.
    @ResponseBody
    @GetMapping(value = "/order/find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Order findOrder(@PathVariable("id") long id){
        return OrderRepo.findById(id).orElseThrow(() -> new OrderNotFoundExcep(id));
    }

    //Encontrar todas as ordens de serviço por ID de um cliente.
    @ResponseBody
    @GetMapping(value = "/order/find/customer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> findOrderByCustomer(@PathVariable("id") long id,
                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> found = OrderRepo.findByCustomerId(id, pageable);
        if(!found.isEmpty()) return found.getContent(); else throw new OrderByCustomerNotFoundExcep(id);
    }

    //Encontrar todas as ordens de serviço pendentes por ID de um cliente.
    @ResponseBody
    @GetMapping(value = "/order/find/customer/{id}/pending", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> findPendingOrderByCustomer(@PathVariable("id") long id,
                                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> found = OrderRepo.findPendingByCustomerId(id, pageable);
        if(!found.isEmpty()) return found.getContent(); else throw new OrderByCustomerNotFoundExcep(id);
    }

    //Encontrar todas as ordens de serviço baixadas por ID de um cliente.
    @ResponseBody
    @GetMapping(value = "/order/find/customer/{id}/closed", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> findClosedOrderByCustomer(@PathVariable("id") long id,
                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(value = "size", defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> found = OrderRepo.findClosedByCustomerId(id, pageable);
        if(!found.isEmpty()) return found.getContent(); else throw new OrderByCustomerNotFoundExcep(id);
    }

    //Encontrar todas as ordens de serviço por ID de um funcionário.
    @ResponseBody
    @GetMapping(value = "/order/find/staff/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> findOrderByStaff(@PathVariable("id") long id,
                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> found = OrderRepo.findByStaffId(id, pageable);
        if(!found.isEmpty()) return found.getContent(); else throw new OrderByStaffNotFoundExcep(id);
    }
    //Encontrar todas as ordens de serviço pendentes por ID de um funcionário.
    @ResponseBody
    @GetMapping(value = "/order/find/staff/{id}/pending", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> findPendingOrderByStaff(@PathVariable("id") long id,
                                               @RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> found = OrderRepo.findPendingByStaffId(id, pageable);
        if(!found.isEmpty()) return found.getContent(); else throw new OrderByStaffNotFoundExcep(id);
    }

    //Encontrar todas as ordens de serviço baixadas por ID de um funcionário.
    @ResponseBody
    @GetMapping(value = "/order/find/staff/{id}/closed", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> findClosedOrderByStaff(@PathVariable("id") long id,
                                              @RequestParam(value = "page", defaultValue = "0") int page,
                                              @RequestParam(value = "size", defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> found = OrderRepo.findClosedByStaffId(id, pageable);
        if(!found.isEmpty()) return found.getContent(); else throw new OrderByStaffNotFoundExcep(id);
    }

    //Criar uma ordem de serviço.
    @ResponseBody
    @PostMapping(value = "/order/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order newOrder, BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new OrderNotCreatedExcep();
        if(OrderRepo.findById(newOrder.getId()).isPresent()) throw new OrderNotCreatedExcep();
        return new ResponseEntity<>(orderService.save(newOrder), HttpStatus.CREATED);
    }

    //Interromper uma ordem de serviço.
    @ResponseBody
    @PutMapping(value="/order/interrupt/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> interruptOrder(@PathVariable("id") long id, @RequestBody Log newLog){
        Order order = OrderRepo.findById(id).orElseThrow(() -> new OrderNotFoundExcep(id));
        if(order.getStatus().equals(Status.Closed)) throw new OrderAlreadyClosedExcep();
        if(order.getStatus().equals(Status.Interrupted)) throw new OrderAlreadyInterruptedExcep();
        return new ResponseEntity<>(orderService.interrupt(order, newLog), HttpStatus.OK);
    }

    //Retomar uma ordem de serviço.
    @ResponseBody
    @PutMapping(value="/order/resume/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> resumeOrder(@PathVariable("id") long id, @RequestBody Log newLog){
        Order order = OrderRepo.findById(id).orElseThrow(() -> new OrderNotFoundExcep(id));
        if(!order.getStatus().equals(Status.Interrupted)) throw new OrderNotInterruptedExcep();
        return new ResponseEntity<>(orderService.resume(order, newLog), HttpStatus.OK);
    }

    //Baixar uma ordem de serviço.
    @ResponseBody
    @PutMapping(value="/order/close/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> closeOrder(@PathVariable("id") long id, @RequestBody Log newLog){
        Order order = OrderRepo.findById(id).orElseThrow(() -> new OrderNotFoundExcep(id));
        if(order.getStatus().equals(Status.Closed)) throw new OrderAlreadyClosedExcep();
        return new ResponseEntity<>(orderService.close(order, newLog), HttpStatus.OK);
    }

    //Excluír uma ordem de serviço.
    @ResponseBody
    @DeleteMapping(value = "/order/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> deleteOrder(@PathVariable("id") long id){
        Order order = OrderRepo.findById(id).orElseThrow(() -> new OrderNotFoundExcep(id));
        return new ResponseEntity<>(orderService.delete(order), HttpStatus.OK);
    }
}
