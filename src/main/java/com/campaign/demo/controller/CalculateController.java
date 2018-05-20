package com.campaign.demo.controller;

import com.campaign.demo.entity.BasketItem;
import com.campaign.demo.service.CalculateDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/demo")
public class CalculateController {

    @Autowired
    private CalculateDiscountService calculateDiscountService;

    @PostMapping(value = "/campaign/calculateDiscounts")
    public ResponseEntity<List<BasketItem>> calculateDiscounts(@RequestBody List<BasketItem> items) {
        return new ResponseEntity<>(calculateDiscountService.calculateDiscount(items), HttpStatus.OK);
    }

}
