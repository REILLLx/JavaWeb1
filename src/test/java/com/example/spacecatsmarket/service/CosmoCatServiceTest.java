package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.exception.CatNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.spacecatsmarket.config.CosmoCatProperties;
import com.example.spacecatsmarket.config.CosmoCatProperties.CatGreeting;
import com.example.spacecatsmarket.service.implementation.CosmoCatServiceImpl;
import org.mockito.InjectMocks;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CosmoCatServiceTest {

    @Mock
    private CosmoCatProperties cosmoCatProperties;

    @InjectMocks
    private CosmoCatServiceImpl cosmoCatService;

    @Test
    void testGetCosmoCats_ReturnsMessage() {
        // Arrange
        String catName = "Luna";
        String message = "Hello, Luna!";
        CatGreeting catGreeting = new CatGreeting();
        catGreeting.setMessage(message);

        Map<String, CatGreeting> greetings = Map.of(catName, catGreeting);
        when(cosmoCatProperties.getGreetings()).thenReturn(greetings);

        // Act
        String result = cosmoCatService.getCosmoCats(catName);

        // Assert
        assertEquals(message, result);
        verify(cosmoCatProperties, times(1)).getGreetings();
    }

    @Test
    void testGetCosmoCats_ThrowsCatNotFoundException() {
        // Arrange
        String catName = "UnknownCat";

        Map<String, CatGreeting> greetings = Map.of(); // No greeting for the given cat name
        when(cosmoCatProperties.getGreetings()).thenReturn(greetings);

        // Act & Assert
        CatNotFoundException exception = assertThrows(
                CatNotFoundException.class,
                () -> cosmoCatService.getCosmoCats(catName)
        );

        assertEquals("Exception " + catName + ", that cat you trying to reach, hasn't greeting", exception.getMessage());
        verify(cosmoCatProperties, times(1)).getGreetings();
    }
}

