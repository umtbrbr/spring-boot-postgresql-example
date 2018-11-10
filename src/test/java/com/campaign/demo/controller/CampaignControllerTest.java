package com.campaign.demo.controller;

import com.campaign.demo.builder.CampaignBuilder;
import com.campaign.demo.entity.Campaign;
import com.campaign.demo.entity.CampaignType;
import com.campaign.demo.entity.DiscountType;
import com.campaign.demo.service.CampaignService;
import org.hamcrest.Matchers;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class CampaignControllerTest {

    static final String BASE_URI = "/api/v1/campaigns";
    static final String REQUEST_URI = BASE_URI + "?id=1";
    static final String NOT_FOUND_REQUEST_URI = BASE_URI + "?id=2";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CampaignService campaignService;

    @Test
    public void testGetAllCampaigns() throws Exception {
        Campaign campaign1 = new CampaignBuilder()
                .withCampaignId(1)
                .withName("Ucuz Iphone")
                .withCampaignType(CampaignType.PRODUCT)
                .withCampaignTypeId(7)
                .withCampaignTypeName("Apple Iphone 7 64 GB")
                .withDiscountType(DiscountType.RATE)
                .withDiscount(5)
                .withMaxDiscount(100)
                .build();
        Campaign campaign2 = new CampaignBuilder()
                .withCampaignId(2)
                .withName("Mavi T-shirt İndirimde")
                .withCampaignType(CampaignType.PRODUCT)
                .withCampaignTypeId(8)
                .withCampaignTypeName("Mavi T-shirt")
                .withDiscountType(DiscountType.PRICE)
                .withDiscount(50)
                .build();
        when(campaignService.getCampaigns()).thenReturn(Stream.of(campaign1, campaign2).collect(Collectors.toList()));

        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("[{\"campaignId\":1,\"name\":\"Ucuz Iphone\",\"campaignType\":\"PRODUCT\",\"campaignTypeId\":7,\"campaignTypeName\":\"Apple Iphone 7 64 GB\",\"discountType\":\"RATE\",\"discount\":5,\"maxDiscount\":100},{\"campaignId\":2,\"name\":\"Mavi T-shirt \u0130ndirimde\",\"campaignType\":\"PRODUCT\",\"campaignTypeId\":8,\"campaignTypeName\":\"Mavi T-shirt\",\"discountType\":\"PRICE\",\"discount\":50}]")));
    }

    @Test
    public void testCreateCampaignStatusBadRequestWhenDiscountTypeIsRateAndBodyDoesNotIncludeMaxDiscount() throws Exception {
        String campaign = "{\"campaignId\":1,\"name\":\"Ucuz Iphone\",\"campaignType\":\"PRODUCT\",\"campaignTypeId\":7,\"campaignTypeName\":\"Apple Iphone 7 64 GB\",\"discountType\":\"RATE\",\"discount\":5}";

        mockMvc.perform(post(BASE_URI).contentType(MediaType.APPLICATION_JSON).content(campaign))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateCampaignStatusOkWhenDiscountTypeIsPriceAndBodyDoesNotNeedIncludeMaxDiscount() throws Exception {
        String campaign = "{\"campaignId\":1,\"name\":\"Ucuz Iphone\",\"campaignType\":\"PRODUCT\",\"campaignTypeId\":7,\"campaignTypeName\":\"Apple Iphone 7 64 GB\",\"discountType\":\"PRICE\",\"discount\":5}";

        mockMvc.perform(post(BASE_URI).contentType(MediaType.APPLICATION_JSON).content(campaign))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCampaign() throws Exception {
        Campaign currentCampaign = new CampaignBuilder()
                .withCampaignId(1)
                .withName("Ucuz Iphone")
                .withCampaignType(CampaignType.PRODUCT)
                .withCampaignTypeId(7)
                .withCampaignTypeName("Apple Iphone 7 64 GB")
                .withDiscountType(DiscountType.RATE)
                .withDiscount(15)
                .withMaxDiscount(35)
                .build();
        when(campaignService.getCampaign(1)).thenReturn(Optional.of(currentCampaign));

        Campaign updatedCampaign = new CampaignBuilder()
                .withCampaignId(1)
                .withName("Ucuz Iphone")
                .withCampaignType(CampaignType.PRODUCT)
                .withCampaignTypeId(7)
                .withCampaignTypeName("Apple Iphone 7 64 GB")
                .withDiscountType(DiscountType.RATE)
                .withDiscount(10)
                .withMaxDiscount(40)
                .build();

        when(campaignService.saveCampaign(currentCampaign)).thenReturn(updatedCampaign);

        String requestedCampaign = "{\"name\":\"Ucuz Iphone\",\"campaignType\":\"PRODUCT\",\"campaignTypeId\":7,\"campaignTypeName\":\"Apple Iphone 7 64 GB\",\"discountType\":\"PRICE\",\"discount\":5}";

        mockMvc.perform(put(REQUEST_URI).contentType(MediaType.APPLICATION_JSON).content(requestedCampaign))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("{\"campaignId\":1,\"name\":\"Ucuz Iphone\",\"campaignType\":\"PRODUCT\",\"campaignTypeId\":7,\"campaignTypeName\":\"Apple Iphone 7 64 GB\",\"discountType\":\"RATE\",\"discount\":10,\"maxDiscount\":40}")));
    }

    @Test
    public void testUpdateCampaignStatusIsNotFoundWhenGivenCampaignNotFound() throws Exception {
        when(campaignService.getCampaign(2)).thenReturn(Optional.empty());

        String requestedCampaign = "{\"name\":\"Ucuz Iphone\",\"campaignType\":\"PRODUCT\",\"campaignTypeId\":7,\"campaignTypeName\":\"Apple Iphone 7 64 GB\",\"discountType\":\"PRICE\",\"discount\":5}";

        mockMvc.perform(put(NOT_FOUND_REQUEST_URI).contentType(MediaType.APPLICATION_JSON).content(requestedCampaign))
                .andExpect(status().isNotFound())
                .andExpect(content().string(Matchers.containsString("\"message\":\"Campaign not found with id : '2'\"")));

    }

    @Test
    public void testUpdateCampaignStatusIsBadRequestWhenGivenDiscountTypeIsRateAndNotIncludeMaxDiscount() throws Exception {
        Campaign currentCampaign = new CampaignBuilder()
                .withCampaignId(1)
                .withName("Ucuz Iphone")
                .withCampaignType(CampaignType.PRODUCT)
                .withCampaignTypeId(7)
                .withCampaignTypeName("Apple Iphone 7 64 GB")
                .withDiscountType(DiscountType.RATE)
                .withDiscount(15)
                .withMaxDiscount(35)
                .build();
        when(campaignService.getCampaign(1)).thenReturn(Optional.of(currentCampaign));

        String requestedCampaign = "{\"name\":\"Ucuz Iphone\",\"campaignType\":\"PRODUCT\",\"campaignTypeId\":7,\"campaignTypeName\":\"Apple Iphone 7 64 GB\",\"discountType\":\"RATE\",\"discount\":5}";

        mockMvc.perform(put(REQUEST_URI).contentType(MediaType.APPLICATION_JSON).content(requestedCampaign))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("\"message\":\"Error in object 'campaign': codes [Maximum discount is mandatory.campaign,Maximum discount is mandatory]; arguments []; default message [null]\"")));
    }

    @Test
    public void testUpdateCampaignStatusIsBadRequestWhenGivenDiscountTypeIsRateAndMaxDiscountIsGreaterThan100() throws Exception {
        Campaign currentCampaign = new CampaignBuilder()
                .withCampaignId(1)
                .withName("Ucuz Iphone")
                .withCampaignType(CampaignType.PRODUCT)
                .withCampaignTypeId(7)
                .withCampaignTypeName("Apple Iphone 7 64 GB")
                .withDiscountType(DiscountType.RATE)
                .withDiscount(15)
                .withMaxDiscount(35)
                .build();
        when(campaignService.getCampaign(1)).thenReturn(Optional.of(currentCampaign));

        String requestedCampaign = "{\"name\":\"Ucuz Iphone\",\"campaignType\":\"PRODUCT\",\"campaignTypeId\":7,\"campaignTypeName\":\"Apple Iphone 7 64 GB\",\"discountType\":\"RATE\",\"discount\":5, \"maxDiscount\":110}";

        mockMvc.perform(put(REQUEST_URI).contentType(MediaType.APPLICATION_JSON).content(requestedCampaign))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("\"message\":\"Error in object 'campaign': codes [Maximum discount cannot more than 100.campaign,Maximum discount cannot more than 100]; arguments []; default message [null]\"")));
    }

    @Test
    public void testDeleteCampaign() throws Exception {
        Campaign campaign = new CampaignBuilder()
                .withCampaignId(1)
                .withName("Ucuz Iphone")
                .withCampaignType(CampaignType.PRODUCT)
                .withCampaignTypeId(7)
                .withCampaignTypeName("Apple Iphone 7 64 GB")
                .withDiscountType(DiscountType.RATE)
                .withDiscount(5)
                .withMaxDiscount(100)
                .build();
        when(campaignService.getCampaign(1)).thenReturn(Optional.of(campaign));

        mockMvc.perform(delete(REQUEST_URI)).andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteCampaignStatusIsNotFoundWhenGivenCampaignNotFound() throws Exception {
        when(campaignService.getCampaign(2)).thenReturn(Optional.empty());

        String requestedCampaign = "{\"name\":\"Ucuz Iphone\",\"campaignType\":\"PRODUCT\",\"campaignTypeId\":7,\"campaignTypeName\":\"Apple Iphone 7 64 GB\",\"discountType\":\"PRICE\",\"discount\":5}";

        mockMvc.perform(delete(NOT_FOUND_REQUEST_URI).contentType(MediaType.APPLICATION_JSON).content(requestedCampaign))
                .andExpect(status().isNotFound())
                .andExpect(content().string(Matchers.containsString("\"message\":\"Campaign not found with id : '2'\"")));

    }
}
