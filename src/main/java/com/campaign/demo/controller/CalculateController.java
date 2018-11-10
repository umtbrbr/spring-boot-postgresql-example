package com.campaign.demo.controller;

import com.campaign.demo.entity.BasketItem;
import com.campaign.demo.service.CalculateDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CalculateController {

    static final String BASE_URI = "/api/v1/campaigns";
    static final String CALCULATE_URI = BASE_URI + "/calculateDiscounts";

    @Autowired
    private CalculateDiscountService calculateDiscountService;

    @RequestMapping(value = CALCULATE_URI, method = RequestMethod.POST)
    public ResponseEntity<List<BasketItem>> calculateDiscounts(@RequestBody List<BasketItem> items) {
        return new ResponseEntity<>(calculateDiscountService.calculateDiscount(items), HttpStatus.OK);
    }

}
