package com.example.spacecatsmarket.web;

import com.example.spacecatsmarket.exception.CatNotFoundException;
import com.example.spacecatsmarket.service.implementation.CosmoCatServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class CosmoCatsControllerIT {

    @MockBean
    private CosmoCatServiceImpl cosmoCatService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetCosmoCat_ReturnsMessage() throws Exception {
        // Arrange
        String catName = "Luna";
        String catMessage = "Hello, Luna!";

        when(cosmoCatService.getCosmoCats(catName)).thenReturn(catMessage);

        // Act & Assert
        mockMvc.perform(get("/api/v1/cosmo-cats/{name}", catName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(catMessage));

        verify(cosmoCatService, times(1)).getCosmoCats(catName);
    }

    @Test
    void testGetCosmoCat_NotFound() throws Exception {
        // Arrange
        String catName = "UnknownCat";

        when(cosmoCatService.getCosmoCats(catName))
                .thenThrow(new CatNotFoundException(catName));

        // Act & Assert
        mockMvc.perform(get("/api/v1/cosmo-cats/{name}", catName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(cosmoCatService, times(1)).getCosmoCats(catName);
    }
}

