package com.campaign.demo.service;

import com.campaign.demo.entity.Campaign;
import com.campaign.demo.entity.CampaignType;
import com.campaign.demo.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignServiceImpl implements CampaignService {

    private CampaignRepository campaignRepository;

    @Autowired
    public void setCampaignRepository(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    @Override
    public List<Campaign> getCampaigns() {
        return campaignRepository.findAll();
    }

    @Override
    public Optional<Campaign> getCampaign(Integer id) {
        return campaignRepository.findById(id);
    }

    @Override
    public Campaign saveCampaign(Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    @Override
    public void deleteCampaign(Integer id) {
        campaignRepository.deleteById(id);
    }

    @Override
    public Optional<Campaign> findByCampaignTypeAndCampaignTypeId(CampaignType campaignType, Integer campaignTypeId) {
        return campaignRepository.findByCampaignTypeAndCampaignTypeId(campaignType, campaignTypeId);
    }
}
