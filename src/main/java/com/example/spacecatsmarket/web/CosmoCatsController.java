package com.example.spacecatsmarket.web;

import com.example.spacecatsmarket.featuretoggle.FeatureToggles;
import com.example.spacecatsmarket.featuretoggle.annotation.FeatureToggle;
import com.example.spacecatsmarket.service.implementation.CosmoCatServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/cosmo-cats")
public class CosmoCatsController {

    private final CosmoCatServiceImpl cosmoCatService;

    public CosmoCatsController(CosmoCatServiceImpl cosmoCatService) {
        this.cosmoCatService = cosmoCatService;
    }

    @GetMapping("/{name}")
    @FeatureToggle(FeatureToggles.COSMO_CATS)
    public ResponseEntity<String> getCustomerById(@PathVariable String name) {
        String cosmoCat = cosmoCatService.getCosmoCats(name);
        return ResponseEntity.ok(cosmoCat);
    }
}