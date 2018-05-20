package com.campaign.demo.service;

import com.campaign.demo.builder.BasketItemBuilder;
import com.campaign.demo.builder.CampaignBuilder;
import com.campaign.demo.entity.BasketItem;
import com.campaign.demo.entity.Campaign;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalculateDiscountServiceTest {

    @Mock
    private CampaignService campaignService;

    private CalculateDiscountService calculateDiscountService;

    @Before
    public void setUp() {
        calculateDiscountService = new CalculateDiscountService();
        calculateDiscountService.setCampaignService(campaignService);
    }

    @Test
    public void testCalculateDiscountWithCampaignTypeIsProductAndDiscountTypeIsPrice() {
        BasketItem item = new BasketItemBuilder()
                .withProductId(5)
                .withCategoryId(7)
                .withPrice(BigDecimal.valueOf(100.99))
                .build();

        List<BasketItem> items = Stream.of(item).collect(Collectors.toList());

        Campaign campaign = new CampaignBuilder()
                .withCampaignType("PRODUCT")
                .withCampaignTypeId(5)
                .withDiscountType("PRICE")
                .withDiscount(50)
                .build();

        BasketItem expectedItem = new BasketItemBuilder()
                .withProductId(5)
                .withCategoryId(7)
                .withPrice(BigDecimal.valueOf(100.99))
                .withDiscountedPrice(BigDecimal.valueOf(50.99))
                .build();

        when(campaignService.findByCampaignTypeAndCampaignTypeId("PRODUCT", 5))
                .thenReturn(Optional.of(campaign));

        List<BasketItem> calculatedItems = calculateDiscountService.calculateDiscount(items);
        Assert.assertThat(calculatedItems.stream().findFirst().get().getDiscountedPrice(), equalTo(expectedItem.getDiscountedPrice()));
    }

    @Test
    public void testCalculateDiscountWithCampaignTypeIsProductAndDiscountTypeIsRate() {
        BasketItem item = new BasketItemBuilder()
                .withProductId(5)
                .withCategoryId(7)
                .withPrice(BigDecimal.valueOf(100.99))
                .build();

        List<BasketItem> items = Stream.of(item).collect(Collectors.toList());

        Campaign campaign = new CampaignBuilder()
                .withCampaignType("PRODUCT")
                .withCampaignTypeId(5)
                .withDiscountType("RATE")
                .withDiscount(10)
                .withMaxDiscount(20)
                .build();

        BasketItem expectedItem = new BasketItemBuilder()
                .withProductId(5)
                .withCategoryId(7)
                .withPrice(BigDecimal.valueOf(100.99))
                .withDiscountedPrice(BigDecimal.valueOf(90.89))
                .build();

        when(campaignService.findByCampaignTypeAndCampaignTypeId("PRODUCT", 5))
                .thenReturn(Optional.of(campaign));

        List<BasketItem> calculatedItems = calculateDiscountService.calculateDiscount(items);
        Assert.assertThat(calculatedItems.stream().findFirst().get().getDiscountedPrice(), equalTo(expectedItem.getDiscountedPrice()));
    }

    @Test
    public void testCalculateDiscountWithCampaignTypeIsProductAndDiscountTypeIsRateAndDiscountIsGreaterThanMaxDiscount() {
        BasketItem item = new BasketItemBuilder()
                .withProductId(5)
                .withCategoryId(7)
                .withPrice(BigDecimal.valueOf(400.99))
                .build();

        List<BasketItem> items = Stream.of(item).collect(Collectors.toList());

        Campaign campaign = new CampaignBuilder()
                .withCampaignType("PRODUCT")
                .withCampaignTypeId(5)
                .withDiscountType("RATE")
                .withDiscount(10)
                .withMaxDiscount(20)
                .build();

        BasketItem expectedItem = new BasketItemBuilder()
                .withProductId(5)
                .withCategoryId(7)
                .withPrice(BigDecimal.valueOf(400.99))
                .withDiscountedPrice(BigDecimal.valueOf(380.99))
                .build();

        when(campaignService.findByCampaignTypeAndCampaignTypeId("PRODUCT", 5))
                .thenReturn(Optional.of(campaign));

        List<BasketItem> calculatedItems = calculateDiscountService.calculateDiscount(items);
        Assert.assertThat(calculatedItems.stream().findFirst().get().getDiscountedPrice(), equalTo(expectedItem.getDiscountedPrice()));
    }

    @Test
    public void testCalculateDiscountWithCampaignTypeIsCategoryAndDiscountTypeIsPriceIncludeSameCategoryInBasket() {
        BasketItem item1 = new BasketItemBuilder()
                .withProductId(5)
                .withCategoryId(7)
                .withPrice(BigDecimal.valueOf(400.99))
                .build();

        BasketItem item2 = new BasketItemBuilder()
                .withProductId(8)
                .withCategoryId(7)
                .withPrice(BigDecimal.valueOf(300.99))
                .build();

        List<BasketItem> items = Stream.of(item1, item2).collect(Collectors.toList());

        Campaign campaign = new CampaignBuilder()
                .withCampaignType("CATEGORY")
                .withCampaignTypeId(7)
                .withDiscountType("PRICE")
                .withDiscount(50)
                .build();

        BasketItem expectedItem1 = new BasketItemBuilder()
                .withProductId(5)
                .withCategoryId(7)
                .withPrice(BigDecimal.valueOf(400.99))
                .withDiscountedPrice(BigDecimal.valueOf(350.99))
                .build();

        BasketItem expectedItem2 = new BasketItemBuilder()
                .withProductId(8)
                .withCategoryId(7)
                .withPrice(BigDecimal.valueOf(300.99))
                .withDiscountedPrice(BigDecimal.valueOf(300.99))
                .build();

        when(campaignService.findByCampaignTypeAndCampaignTypeId("CATEGORY", 7))
                .thenReturn(Optional.of(campaign));

        List<BasketItem> calculatedItems = calculateDiscountService.calculateDiscount(items);
        Assert.assertThat(calculatedItems.stream().findFirst().get().getDiscountedPrice(), equalTo(expectedItem1.getDiscountedPrice()));
        Assert.assertThat(calculatedItems.stream().skip(1).findFirst().get().getDiscountedPrice(), equalTo(expectedItem2.getDiscountedPrice()));
    }

    @Test
    public void testCalculateDiscountWithCampaignTypeIsProductAndCategory() {
        BasketItem item = new BasketItemBuilder()
                .withProductId(5)
                .withCategoryId(7)
                .withPrice(BigDecimal.valueOf(80.99))
                .build();

        List<BasketItem> items = Stream.of(item).collect(Collectors.toList());

        Campaign productCampaign = new CampaignBuilder()
                .withCampaignType("PRODUCT")
                .withCampaignTypeId(5)
                .withDiscountType("PRICE")
                .withDiscount(10)
                .build();

        Campaign categoryCampaign = new CampaignBuilder()
                .withCampaignType("CATEGORY")
                .withCampaignTypeId(7)
                .withDiscountType("PRICE")
                .withDiscount(20)
                .build();

        BasketItem expectedItem = new BasketItemBuilder()
                .withProductId(5)
                .withCategoryId(7)
                .withPrice(BigDecimal.valueOf(80.99))
                .withDiscountedPrice(BigDecimal.valueOf(50.99))
                .build();

        when(campaignService.findByCampaignTypeAndCampaignTypeId("PRODUCT", 5))
                .thenReturn(Optional.of(productCampaign));

        when(campaignService.findByCampaignTypeAndCampaignTypeId("CATEGORY", 7))
                .thenReturn(Optional.of(categoryCampaign));

        List<BasketItem> calculatedItems = calculateDiscountService.calculateDiscount(items);
        Assert.assertThat(calculatedItems.stream().findFirst().get().getDiscountedPrice(), equalTo(expectedItem.getDiscountedPrice()));
    }
}
