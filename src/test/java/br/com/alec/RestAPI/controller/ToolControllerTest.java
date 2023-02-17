package br.com.alec.RestAPI.controller;

import br.com.alec.RestAPI.model.Tool;
import br.com.alec.RestAPI.model.Status;
import br.com.alec.RestAPI.repository.ToolRepository;
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

@WebMvcTest(ToolController.class)
public class ToolControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    ToolRepository ToolRepo;

    @Test
    public void givenToolExists_whenFindById_thenReturnTool() throws Exception{
        Tool tool = new Tool(1, "Lavadora de Alta Pressão", "WAP", Status.Active);
        Mockito.when(ToolRepo.findById(tool.getId())).thenReturn(Optional.of(tool));
        mvc.perform(get("/tool/find/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void givenToolDontExists_whenFindById_thenReturn404() throws Exception{
        Mockito.when(ToolRepo.findById(1L)).thenReturn(Optional.empty());
        mvc.perform(get("/tool/find/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenThereIsAtLeastOneToolActive_whenFindActive_thenReturnPageListOfTool() throws Exception{
        List<Tool> list = Arrays.asList(
                new Tool(1, "Lavadora de Alta Pressão", "WAP", Status.Active),
                new Tool(2, "Parafusadeira", "Vonder", Status.Active));
        PagedListHolder<Tool> toolPagedListHolder = new PagedListHolder<>(list);
        toolPagedListHolder.setPageSize(10);
        toolPagedListHolder.setPage(0);
        Page<Tool> toolPage = new PageImpl<>(toolPagedListHolder.getPageList(), PageRequest.of(0,10), list.size());
        Pageable pageable = PageRequest.of(0, 10);

        Mockito.when(ToolRepo.findActive(pageable)).thenReturn(toolPage);
        mvc.perform(get("/tool/find/active")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    public void givenNoToolIsActive_whenFindActive_thenReturnEmptyPageList() throws Exception{
        List<Tool> list = List.of();
        PagedListHolder<Tool> toolPagedListHolder = new PagedListHolder<>(list);
        toolPagedListHolder.setPageSize(10);
        toolPagedListHolder.setPage(0);
        Page<Tool> toolPage = new PageImpl<>(toolPagedListHolder.getPageList(), PageRequest.of(0,10), 0);
        Pageable pageable = PageRequest.of(0, 10);

        Mockito.when(ToolRepo.findActive(pageable)).thenReturn(toolPage);
        mvc.perform(get("/tool/find/active")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
