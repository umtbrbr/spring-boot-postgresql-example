package com.campaign.demo.controller;

import com.campaign.demo.builder.CampaignBuilder;
import com.campaign.demo.entity.Campaign;
import com.campaign.demo.entity.CampaignType;
import com.campaign.demo.entity.DiscountType;
import com.campaign.demo.service.CalculateDiscountService;
import com.campaign.demo.service.CampaignService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class CalculateControllerTest {

    static final String BASE_URI = "/api/v1/campaigns";
    static final String CALCULATE_URI = BASE_URI + "/calculateDiscounts";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CampaignService campaignService;

    private CalculateDiscountService calculateDiscountService;

    @Before
    public void setUp() {
        calculateDiscountService = new CalculateDiscountService();
        calculateDiscountService.setCampaignService(campaignService);
    }

    @Test
    public void testCalculateDiscountWithCampaignTypeIsProductAndDiscountTypeIsPrice() throws Exception {
        Campaign campaign = new CampaignBuilder()
                .withCampaignType(CampaignType.PRODUCT)
                .withCampaignTypeId(5)
                .withDiscountType(DiscountType.PRICE)
                .withDiscount(50)
                .build();

        when(campaignService.findByCampaignTypeAndCampaignTypeId(CampaignType.PRODUCT, 5))
                .thenReturn(Optional.of(campaign));

        String request = "[{\"productId\":5,\"categoryId\":7,\"price\":100.99}]";

        mockMvc.perform(post(CALCULATE_URI).contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("[{\"productId\":5,\"categoryId\":7,\"price\":100.99,\"discountedPrice\":50.99}]")));
    }

    @Test
    public void testCalculateDiscountWithCampaignTypeIsProductAndDiscountTypeIsRate() throws Exception {
        Campaign campaign = new CampaignBuilder()
                .withCampaignType(CampaignType.PRODUCT)
                .withCampaignTypeId(5)
                .withDiscountType(DiscountType.RATE)
                .withDiscount(10)
                .withMaxDiscount(20)
                .build();

        when(campaignService.findByCampaignTypeAndCampaignTypeId(CampaignType.PRODUCT, 5))
                .thenReturn(Optional.of(campaign));

        String request = "[{\"productId\":5,\"categoryId\":7,\"price\":100.99}]";

        mockMvc.perform(post(CALCULATE_URI).contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("[{\"productId\":5,\"categoryId\":7,\"price\":100.99,\"discountedPrice\":90.89}]")));
    }

    @Test
    public void testCalculateDiscountWithCampaignTypeIsProductAndDiscountTypeIsRateAndDiscountIsGreaterThanMaxDiscount() throws Exception {
        Campaign campaign = new CampaignBuilder()
                .withCampaignType(CampaignType.PRODUCT)
                .withCampaignTypeId(5)
                .withDiscountType(DiscountType.RATE)
                .withDiscount(40)
                .withMaxDiscount(20)
                .build();

        when(campaignService.findByCampaignTypeAndCampaignTypeId(CampaignType.PRODUCT, 5))
                .thenReturn(Optional.of(campaign));

        String request = "[{\"productId\":5,\"categoryId\":7,\"price\":100.99}]";

        mockMvc.perform(post(CALCULATE_URI).contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("[{\"productId\":5,\"categoryId\":7,\"price\":100.99,\"discountedPrice\":80.99}]")));
    }
}
