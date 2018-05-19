package com.campaign.demo.service;

import com.campaign.demo.entity.Campaign;
import com.campaign.demo.repository.CampaignRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CampaignServiceTest {

    @Mock
    CampaignRepository campaignRepository;

    @Mock
    private Campaign campaign;

    CampaignService service;

    @Before
    public void setUp() {
        service = new CampaignService();
        service.setCampaignRepository(campaignRepository);
    }

    @Test
    public void shouldReturnCampaignsWhenGetCampaignsIsCalled() {
        when(campaignRepository.findAll()).thenReturn(generateMockCampaigns());

        List<Campaign> expectedCampaigns = service.getCampaigns();
        Optional<Campaign> result = expectedCampaigns.stream().findFirst();

        assertTrue(result.isPresent());
        assertEquals(result.get().getCampaignId().intValue(), 1);
        assertEquals(result.get().getName(), "Ucuz Iphone");
        assertEquals(result.get().getCampaignType(), "PRODUCT");
        assertEquals(result.get().getCampaignTypeId().intValue(), 7);
        assertEquals(result.get().getCampaignTypeName(), "Apple Iphone 7 64 GB");
        assertEquals(result.get().getDiscountType(), "RATE");
        assertEquals(result.get().getDiscount().intValue(), 5);
        assertEquals(result.get().getMaxDiscount().intValue(), 100);
    }

    @Test
    public void shouldReturnCampaignWhenCreateCampaignIsCalled() {
        when(campaignRepository.save(campaign)).thenReturn(campaign);
        Campaign savedCampaign = service.createCampaign(campaign);
        assertThat(savedCampaign, is(equalTo(campaign)));
    }

    private List<Campaign> generateMockCampaigns() {
        Campaign campaign = generateMockCampaign();

        return Stream.of(campaign).collect(Collectors.toList());
    }

    private Campaign generateMockCampaign() {
        Campaign campaign = new Campaign();
        campaign.setCampaignId(1);
        campaign.setName("Ucuz Iphone");
        campaign.setCampaignType("PRODUCT");
        campaign.setCampaignTypeId(7);
        campaign.setCampaignTypeName("Apple Iphone 7 64 GB");
        campaign.setDiscountType("RATE");
        campaign.setDiscount(5);
        campaign.setMaxDiscount(100);

        return campaign;
    }
}
