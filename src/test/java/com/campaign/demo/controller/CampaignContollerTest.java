//package com.campaign.demo.controller;
//
//import com.campaign.demo.builder.CampaignBuilder;
//import com.campaign.demo.entity.Campaign;
//import com.campaign.demo.entity.CampaignType;
//import com.campaign.demo.entity.DiscountType;
//import com.campaign.demo.service.CampaignService;
//import org.hamcrest.Matchers;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Optional;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@AutoConfigureMockMvc
//@SpringBootTest
//public class CampaignContollerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CampaignService campaignService;
//
//    @Test
//    public void testGetAllCampaigns() throws Exception {
//        Campaign campaign1 = new CampaignBuilder()
//                .withCampaignId(1)
//                .withName("Ucuz Iphone")
//                .withCampaignType(CampaignType.PRODUCT)
//                .withCampaignTypeId(7)
//                .withCampaignTypeName("Apple Iphone 7 64 GB")
//                .withDiscountType(DiscountType.RATE)
//                .withDiscount(5)
//                .withMaxDiscount(100)
//                .build();
//        Campaign campaign2 = new CampaignBuilder()
//                .withCampaignId(2)
//                .withName("Mavi T-shirt İndirimde")
//                .withCampaignType(CampaignType.PRODUCT)
//                .withCampaignTypeId(8)
//                .withCampaignTypeName("Mavi T-shirt")
//                .withDiscountType(DiscountType.PRICE)
//                .withDiscount(50)
//                .build();
//        when(campaignService.getCampaigns()).thenReturn(Stream.of(campaign1, campaign2).collect(Collectors.toList()));
//
//        mockMvc.perform(get("/demo/campaigns"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(Matchers.containsString("[{\"campaignId\":1,\"name\":\"Ucuz Iphone\",\"campaignType\":\"PRODUCT\",\"campaignTypeId\":7,\"campaignTypeName\":\"Apple Iphone 7 64 GB\",\"discountType\":\"RATE\",\"discount\":5,\"maxDiscount\":100},{\"campaignId\":2,\"name\":\"Mavi T-shirt \u0130ndirimde\",\"campaignType\":\"PRODUCT\",\"campaignTypeId\":8,\"campaignTypeName\":\"Mavi T-shirt\",\"discountType\":\"PRICE\",\"discount\":50}]")));
//    }
//
//    @Test
//    public void testCreateCampaignStatusBadRequestWhenDiscountTypeIsRateAndBodyDoesNotIncludeMaxDiscount() throws Exception {
//        String campaign = "{\"campaignId\":1,\"name\":\"Ucuz Iphone\",\"campaignType\":\"PRODUCT\",\"campaignTypeId\":7,\"campaignTypeName\":\"Apple Iphone 7 64 GB\",\"discountType\":\"RATE\",\"discount\":5}";
//
//        mockMvc.perform(post("/demo/campaign/create").contentType(MediaType.APPLICATION_JSON).content(campaign))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void testCreateCampaignStatusOkWhenDiscountTypeIsPriceAndBodyDoesNotNeedIncludeMaxDiscount() throws Exception {
//        String campaign = "{\"campaignId\":1,\"name\":\"Ucuz Iphone\",\"campaignType\":\"PRODUCT\",\"campaignTypeId\":7,\"campaignTypeName\":\"Apple Iphone 7 64 GB\",\"discountType\":\"PRICE\",\"discount\":5}";
//
//        mockMvc.perform(post("/demo/campaign/create").contentType(MediaType.APPLICATION_JSON).content(campaign))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testDeleteCampaign() throws Exception {
//        Campaign campaign = new CampaignBuilder()
//                .withCampaignId(1)
//                .withName("Ucuz Iphone")
//                .withCampaignType(CampaignType.PRODUCT)
//                .withCampaignTypeId(7)
//                .withCampaignTypeName("Apple Iphone 7 64 GB")
//                .withDiscountType(DiscountType.RATE)
//                .withDiscount(5)
//                .withMaxDiscount(100)
//                .build();
//        when(campaignService.getCampaign(1)).thenReturn(Optional.of(campaign));
//
//        mockMvc.perform(delete("/demo/campaign/delete?id=1")).andExpect(status().isNoContent());
//    }
//}
