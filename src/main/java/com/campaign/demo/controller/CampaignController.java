package com.campaign.demo.controller;

import com.campaign.demo.entity.Campaign;
import com.campaign.demo.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @GetMapping(value = "demo/campaigns")
    public ResponseEntity<List<Campaign>> getCampaigns() {
        List<Campaign> campaigns = campaignService.getCampaigns();

        ResponseEntity<List<Campaign>> response = new ResponseEntity<>(campaigns, HttpStatus.OK);

        return response;
    }

    @PostMapping(value = "demo/campaign/create")
    public ResponseEntity<Campaign> createCampaign(@Valid @RequestBody Campaign campaign) {
        Campaign createCampaign = campaignService.createCampaign(campaign);

        ResponseEntity<Campaign> response = new ResponseEntity<>(createCampaign, HttpStatus.OK);

        return response;
    }
}
