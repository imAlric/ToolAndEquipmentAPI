package br.com.alec.RestAPI.controller;

import br.com.alec.RestAPI.model.Staff;
import br.com.alec.RestAPI.model.Status;
import br.com.alec.RestAPI.repository.StaffRepository;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StaffController.class)
public class StaffControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    StaffRepository StaffRepo;

    @Test
    public void givenStaffExists_whenFindById_thenReturnStaff() throws Exception{
        Staff staff = new Staff(1,"Alec Fernando", "12413329919", "Atendente", Status.Active);
        Mockito.when(StaffRepo.findById(staff.getId())).thenReturn(Optional.of(staff));
        mvc.perform(get("/staff/find/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void givenStaffDontExists_whenFindById_thenReturn404() throws Exception{
        Mockito.when(StaffRepo.findById(1L)).thenReturn(Optional.empty());
        mvc.perform(get("/staff/find/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenThereIsAtLeastOneStaffActive_whenFindActive_thenReturnPageListOfStaff() throws Exception{
        List<Staff> list = Arrays.asList(
                new Staff(1,"Alec Fernando", "12413329919", "Atendente", Status.Active),
                new Staff(2,"José Rodrigues", "23456789212", "Atendente", Status.Active));
        PagedListHolder<Staff> staffPagedListHolder = new PagedListHolder<>(list);
        staffPagedListHolder.setPageSize(10);
        staffPagedListHolder.setPage(0);
        Page<Staff> staffPage = new PageImpl<>(staffPagedListHolder.getPageList(), PageRequest.of(0,10), list.size());
        Pageable pageable = PageRequest.of(0, 10);

        Mockito.when(StaffRepo.findActive(pageable)).thenReturn(staffPage);
        mvc.perform(get("/staff/find/active")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    public void givenNoStaffIsActive_whenFindActive_thenReturnEmptyPageList() throws Exception{
        List<Staff> list = List.of();
        PagedListHolder<Staff> staffPagedListHolder = new PagedListHolder<>(list);
        staffPagedListHolder.setPageSize(10);
        staffPagedListHolder.setPage(0);
        Page<Staff> staffPage = new PageImpl<>(staffPagedListHolder.getPageList(), PageRequest.of(0,10), 0);
        Pageable pageable = PageRequest.of(0, 10);

        Mockito.when(StaffRepo.findActive(pageable)).thenReturn(staffPage);
        mvc.perform(get("/staff/find/active")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
