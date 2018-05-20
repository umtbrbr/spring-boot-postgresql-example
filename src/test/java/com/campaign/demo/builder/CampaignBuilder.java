package com.campaign.demo.builder;

import com.campaign.demo.entity.Campaign;
import com.campaign.demo.entity.CampaignType;
import com.campaign.demo.entity.DiscountType;

public class CampaignBuilder {

    private Campaign campaign;

    public CampaignBuilder() {
        campaign = new Campaign();
    }

    public CampaignBuilder withCampaignId(Integer campaignId) {
        campaign.setCampaignId(campaignId);
        return this;
    }

    public CampaignBuilder withName(String name) {
        campaign.setName(name);
        return this;
    }

    public CampaignBuilder withCampaignType(CampaignType campaignType) {
        campaign.setCampaignType(campaignType);
        return this;
    }

    public CampaignBuilder withCampaignTypeId(Integer campaignTypeId) {
        campaign.setCampaignTypeId(campaignTypeId);
        return this;
    }

    public CampaignBuilder withCampaignTypeName(String campaignTypeName) {
        campaign.setCampaignTypeName(campaignTypeName);
        return this;
    }

    public CampaignBuilder withDiscountType(DiscountType discountType) {
        campaign.setDiscountType(discountType);
        return this;
    }

    public CampaignBuilder withDiscount(Integer discount) {
        campaign.setDiscount(discount);
        return this;
    }

    public CampaignBuilder withMaxDiscount(Integer maxDiscount) {
        campaign.setMaxDiscount(maxDiscount);
        return this;
    }

    public Campaign build() {
        return campaign;
    }
}
