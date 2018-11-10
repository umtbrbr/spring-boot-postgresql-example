package com.campaign.demo.controller;

import com.campaign.demo.entity.Campaign;
import com.campaign.demo.error.ErrorResponse;
import com.campaign.demo.error.ResourceNotFoundException;
import com.campaign.demo.service.CampaignService;
import com.campaign.demo.validator.MaxDiscountValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CampaignController {

    public final static Logger logger = LogManager.getLogger(CampaignController.class);

    static final String BASE_URI = "/api/v1/campaigns";

    @Autowired
    MaxDiscountValidator maxDiscountValidator;

    @Autowired
    private CampaignService campaignService;

    @RequestMapping(value = BASE_URI, method = RequestMethod.GET)
    public ResponseEntity<List<Campaign>> getCampaigns() {
        List<Campaign> campaigns = campaignService.getCampaigns();
        if (campaigns.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(campaigns, HttpStatus.OK);
    }

    @RequestMapping(value = BASE_URI, method = RequestMethod.POST)
    public ResponseEntity<Campaign> createCampaign(@Valid @RequestBody Campaign campaign, Errors errors) {
        maxDiscountValidator.validate(campaign, errors);
        if (errors.hasErrors()) {
            return new ResponseEntity(new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), createErrorString(errors)),
                    HttpStatus.BAD_REQUEST);
        }

        Campaign createCampaign = campaignService.saveCampaign(campaign);

        return new ResponseEntity<>(createCampaign, HttpStatus.OK);
    }

    @RequestMapping(value = BASE_URI, method = RequestMethod.PUT)
    public ResponseEntity<Campaign> updateCampaign(@RequestParam("id") Integer campaignId, @Valid @RequestBody Campaign campaignDetails, Errors errors) {
        Optional<Campaign> campaign = campaignService.getCampaign(campaignId);
        if (!campaign.isPresent()) {
            logger.error("Unable to update. Campaign not found with id: " + campaignId);
            throw new ResourceNotFoundException("Campaign", "id", campaignId);
        }

        maxDiscountValidator.validate(campaignDetails, errors);
        if (errors.hasErrors()) {
            return new ResponseEntity(new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), createErrorString(errors)),
                    HttpStatus.BAD_REQUEST);
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

    @RequestMapping(value = BASE_URI, method = RequestMethod.DELETE)
    public ResponseEntity<Campaign> deleteCampaign(@RequestParam("id") Integer campaignId) {
        Optional<Campaign> campaign = campaignService.getCampaign(campaignId);
        if (!campaign.isPresent()) {
            logger.error("Unable to delete. Campaign not found with id: " + campaignId);
            throw new ResourceNotFoundException("Campaign", "id", campaignId);
        }

        campaignService.deleteCampaign(campaignId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private String createErrorString(Errors errors) {
        return errors.getAllErrors().stream().map(ObjectError::toString).collect(Collectors.joining(","));
    }
}
