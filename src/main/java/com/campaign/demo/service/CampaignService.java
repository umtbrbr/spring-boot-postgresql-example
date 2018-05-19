package com.campaign.demo.service;

import com.campaign.demo.entity.Campaign;
import com.campaign.demo.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignService {

    private CampaignRepository campaignRepository;

    @Autowired
    public void setCampaignRepository(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    public List<Campaign> getCampaigns() {
        return campaignRepository.findAll();
    }

    public Optional<Campaign> getCampaign(Integer id) {
        return campaignRepository.findById(id);
    }

    public Campaign saveCampaign(Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    public void deleteCampaign(Integer id) {
        campaignRepository.deleteById(id);
    }

}
