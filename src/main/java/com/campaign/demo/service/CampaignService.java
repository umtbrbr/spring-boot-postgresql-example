package com.campaign.demo.service;

import com.campaign.demo.entity.Campaign;

import java.util.List;
import java.util.Optional;

public interface CampaignService {

    List<Campaign> getCampaigns();

    Optional<Campaign> getCampaign(Integer id);

    Campaign saveCampaign(Campaign campaign);

    void deleteCampaign(Integer id);
}
