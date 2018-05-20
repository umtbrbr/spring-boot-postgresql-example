package com.campaign.demo.service;

import com.campaign.demo.entity.BasketItem;
import com.campaign.demo.entity.Campaign;
import com.campaign.demo.util.BigDecimalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;

@Service
public class CalculateDiscountService {

    private CampaignService campaignService;

    @Autowired
    public void setCampaignService(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    public List<BasketItem> calculateDiscount(List<BasketItem> items) {

        items.forEach((BasketItem currentBasketItem) -> {
            BigDecimal discountedPrice;
            BigDecimal categoryDiscountedPrice = null;
            BigDecimal productDiscountPrice = null;

            Optional<Campaign> categoryCampaign = campaignService.findByCampaignTypeAndCampaignTypeId("CATEGORY", currentBasketItem.getCategoryId());
            if (categoryCampaign.isPresent()) {
                BasketItem maxPriceItem = findMaxPriceItemInBasket(items, currentBasketItem.getCategoryId());
                if (maxPriceItem.equals(currentBasketItem)) {
                    categoryDiscountedPrice = getDiscountedPrice(currentBasketItem, categoryCampaign.get());
                }
            }

            Optional<Campaign> productCampaign = campaignService.findByCampaignTypeAndCampaignTypeId("PRODUCT", currentBasketItem.getProductId());
            if (productCampaign.isPresent()) {
                productDiscountPrice = getDiscountedPrice(currentBasketItem, productCampaign.get());
            }

            discountedPrice = calculateDiscountedPrice(currentBasketItem, categoryDiscountedPrice, productDiscountPrice);
            currentBasketItem.setDiscountedPrice(discountedPrice);
        });

        return items;
    }

    private BasketItem findMaxPriceItemInBasket(List<BasketItem> itemsToProcess, Integer categoryId) {
        return itemsToProcess.stream()
                .filter(bi -> bi.getCategoryId().equals(categoryId))
                .max(comparing(BasketItem::getPrice))
                .get();
    }

    private BigDecimal getDiscountedPrice(BasketItem basketItem, Campaign campaign) {
        if ("PRICE".equalsIgnoreCase(campaign.getDiscountType())) {
            return BigDecimal.valueOf(campaign.getDiscount());
        }

        BigDecimal percentageCalc = BigDecimalUtil.getPercentage(basketItem.getPrice(), BigDecimal.valueOf(campaign.getDiscount()));
        if (percentageCalc.compareTo(BigDecimal.valueOf(campaign.getMaxDiscount())) > 0) {
            return BigDecimal.valueOf(campaign.getMaxDiscount());
        }

        return percentageCalc;
    }

    private BigDecimal calculateDiscountedPrice(BasketItem basketItem, BigDecimal categoryDiscountPrice, BigDecimal productDiscountPrice) {
        if (categoryDiscountPrice == null && productDiscountPrice == null) {
            return basketItem.getPrice();
        }

        if (categoryDiscountPrice == null) {
            return BigDecimalUtil.getDifference(basketItem.getPrice(), productDiscountPrice);
        }

        if (productDiscountPrice == null) {
            return BigDecimalUtil.getDifference(basketItem.getPrice(), categoryDiscountPrice);
        }

        return BigDecimalUtil.getDifference(basketItem.getPrice(), categoryDiscountPrice.add(productDiscountPrice));

    }
}
