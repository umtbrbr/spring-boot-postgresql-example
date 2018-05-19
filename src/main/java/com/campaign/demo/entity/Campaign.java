package com.campaign.demo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Campaign implements Serializable {

    private static final long serialVersionUID = -2753301445154585720L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer campaignId;

    @NotNull(message = "Campaign name is mandatory")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Campaign type is mandatory")
    @Column(name = "campaign_type")
    private String campaignType;

    @NotNull(message = "Campaign type id is mandatory")
    @Column(name = "campaign_type_id")
    private Integer campaignTypeId;

    @NotNull(message = "Campaign type name is mandatory")
    @Column(name = "campaign_type_name")
    private String campaignTypeName;

    @NotNull(message = "Discount type is mandatory")
    @Column(name = "discount_type")
    private String discountType;

    @NotNull(message = "Discount is mandatory")
    @Column(name = "discount")
    private Integer discount;

    @Column(name = "max_discount")
    private Integer maxDiscount;

    public Campaign() {
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(String campaignType) {
        this.campaignType = campaignType;
    }

    public Integer getCampaignTypeId() {
        return campaignTypeId;
    }

    public void setCampaignTypeId(Integer campaignTypeId) {
        this.campaignTypeId = campaignTypeId;
    }

    public String getCampaignTypeName() {
        return campaignTypeName;
    }

    public void setCampaignTypeName(String campaignTypeName) {
        this.campaignTypeName = campaignTypeName;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(Integer maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

}
