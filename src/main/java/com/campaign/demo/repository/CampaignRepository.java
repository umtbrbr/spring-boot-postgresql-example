package com.campaign.demo.repository;

import com.campaign.demo.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {

    Optional<Campaign> findByCampaignTypeAndCampaignTypeId(String campaignType, Integer campaignTypeId);
}
