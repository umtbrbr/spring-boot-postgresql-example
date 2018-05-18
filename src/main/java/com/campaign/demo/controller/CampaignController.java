package com.campaign.demo.controller;

import com.campaign.demo.entity.Campaign;
import com.campaign.demo.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @GetMapping(value = "/campaigns")
    public ResponseEntity<List<Campaign>> findAll() {
        List<Campaign> campaigns = campaignService.findAll();

        ResponseEntity<List<Campaign>> response = new ResponseEntity<>(campaigns, HttpStatus.OK);

        return response;
    }
}
