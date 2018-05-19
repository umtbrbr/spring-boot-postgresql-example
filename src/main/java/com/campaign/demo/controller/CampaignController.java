package com.campaign.demo.controller;

import com.campaign.demo.entity.Campaign;
import com.campaign.demo.error.ResourceNotFoundException;
import com.campaign.demo.service.CampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/demo")
public class CampaignController {
    public static final Logger logger = LoggerFactory.getLogger(CampaignController.class);

    @Autowired
    private CampaignService campaignService;

    @GetMapping(value = "/campaigns")
    public ResponseEntity<List<Campaign>> getCampaigns() {
        List<Campaign> campaigns = campaignService.getCampaigns();
        if (campaigns.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(campaigns, HttpStatus.OK);
    }

    @PostMapping(value = "/campaign/create")
    public ResponseEntity<Campaign> createCampaign(@Valid @RequestBody Campaign campaign) {
        Campaign createCampaign = campaignService.saveCampaign(campaign);

        return new ResponseEntity<>(createCampaign, HttpStatus.OK);
    }

    @PutMapping(value = "/campaign/update")
    public ResponseEntity<Campaign> updateCampaign(@RequestParam("id") Integer campaignId, @Valid @RequestBody Campaign campaignDetails) {
        Optional<Campaign> campaign = campaignService.getCampaign(campaignId);
        if (!campaign.isPresent()) {
            logger.error("Unable to update. Campaign not found with id: " + campaignId);
            throw new ResourceNotFoundException("Campaign", "id", campaignId);
        }

        Campaign currentCampaign = campaign.get();
        currentCampaign.setDiscountType(campaignDetails.getDiscountType());
        currentCampaign.setDiscount(campaignDetails.getDiscount());
        currentCampaign.setName(campaignDetails.getName());
        currentCampaign.setMaxDiscount(campaignDetails.getMaxDiscount());
        currentCampaign.setCampaignTypeName(campaignDetails.getCampaignTypeName());
        currentCampaign.setCampaignType(campaignDetails.getCampaignType());
        currentCampaign.setCampaignTypeId(campaignDetails.getCampaignTypeId());

        Campaign updatedCampaign = campaignService.saveCampaign(currentCampaign);

        ResponseEntity<Campaign> response = new ResponseEntity<>(updatedCampaign, HttpStatus.OK);

        return response;
    }

    @DeleteMapping(value = "/campaign/delete")
    public ResponseEntity<Campaign> deleteCampaign(@RequestParam("id") Integer campaignId) {
        Optional<Campaign> campaign = campaignService.getCampaign(campaignId);
        if (!campaign.isPresent()) {
            logger.error("Unable to delete. Campaign not found with id: " + campaignId);
            throw new ResourceNotFoundException("Campaign", "id", campaignId);
        }

        campaignService.deleteCampaign(campaignId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
