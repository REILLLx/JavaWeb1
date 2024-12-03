package com.example.spacecatsmarket.service.implementation;

import com.example.spacecatsmarket.config.CosmoCatProperties;
import com.example.spacecatsmarket.config.CosmoCatProperties.CatGreeting;
import com.example.spacecatsmarket.exception.CatNotFoundException;
import com.example.spacecatsmarket.service.CosmoCatService;
import org.springframework.stereotype.Service;


import static java.util.Optional.ofNullable;

@Service
public class CosmoCatServiceImpl implements CosmoCatService {
    private final CosmoCatProperties cosmoCatProperties;

    public CosmoCatServiceImpl(CosmoCatProperties cosmoCatProperties) {
        this.cosmoCatProperties = cosmoCatProperties;
    }

    @Override
    public String getCosmoCats(String name) {
        return ofNullable(cosmoCatProperties.getGreetings()
                .get(name))
                .map(CatGreeting::getMessage)
                .orElseThrow(() -> new CatNotFoundException(name));
    }
}