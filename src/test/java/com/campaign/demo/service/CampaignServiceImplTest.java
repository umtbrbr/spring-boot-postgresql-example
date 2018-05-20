package com.campaign.demo.service;

import com.campaign.demo.builder.CampaignBuilder;
import com.campaign.demo.entity.Campaign;
import com.campaign.demo.entity.CampaignType;
import com.campaign.demo.entity.DiscountType;
import com.campaign.demo.repository.CampaignRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CampaignServiceImplTest {

    @Mock
    CampaignRepository campaignRepository;

    @Mock
    private Campaign campaign;

    @Captor
    private ArgumentCaptor<Campaign> campaignCaptor;

    CampaignServiceImpl serviceImpl;

    @Before
    public void setUp() {
        serviceImpl = new CampaignServiceImpl();
        serviceImpl.setCampaignRepository(campaignRepository);
    }

    @Test
    public void shouldReturnCampaignsWhenGetCampaignsIsCalled() {
        when(campaignRepository.findAll()).thenReturn(generateMockCampaigns());

        List<Campaign> expectedCampaigns = serviceImpl.getCampaigns();
        Optional<Campaign> result = expectedCampaigns.stream().findFirst();

        assertTrue(result.isPresent());
        assertEquals(result.get().getCampaignId().intValue(), 1);
        assertEquals(result.get().getName(), "Ucuz Iphone");
        assertEquals(result.get().getCampaignType().name(), "PRODUCT");
        assertEquals(result.get().getCampaignTypeId().intValue(), 7);
        assertEquals(result.get().getCampaignTypeName(), "Apple Iphone 7 64 GB");
        assertEquals(result.get().getDiscountType().name(), "RATE");
        assertEquals(result.get().getDiscount().intValue(), 5);
        assertEquals(result.get().getMaxDiscount().intValue(), 100);
    }

    @Test
    public void shouldReturnCampaignWhenCreateCampaignIsCalled() {
        when(campaignRepository.save(campaign)).thenReturn(campaign);
        Campaign savedCampaign = serviceImpl.saveCampaign(campaign);
        assertThat(savedCampaign, is(equalTo(campaign)));
    }

    @Test
    public void shouldCallDeleteByIdMethodOfCampaignRepositoryWhenDeleteCampaignIsCalled() {
        doNothing().when(campaignRepository).deleteById(1);
        serviceImpl.deleteCampaign(1);
        verify(campaignRepository, times(1)).deleteById(1);
    }

    @Test
    public void shouldUpdateCampaignWithGivenCampaign() {
        Campaign givenCampaign = new CampaignBuilder()
                .withCampaignId(1)
                .withName("Ucuz Iphone")
                .withCampaignType(CampaignType.PRODUCT)
                .withCampaignTypeId(7)
                .withCampaignTypeName("Apple Iphone 7 64 GB")
                .withDiscountType(DiscountType.RATE)
                .withDiscount(5)
                .withMaxDiscount(100)
                .build();

        serviceImpl.saveCampaign(givenCampaign);

        verify(campaignRepository).save(campaignCaptor.capture());
        Campaign capturedCampaign = campaignCaptor.getValue();

        assertThat(capturedCampaign.getDiscount(), is(5));
        assertThat(capturedCampaign.getDiscountType(), is(DiscountType.RATE));
    }

    private List<Campaign> generateMockCampaigns() {
        Campaign campaign = generateMockCampaign();

        return Stream.of(campaign).collect(Collectors.toList());
    }

    private Campaign generateMockCampaign() {
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

        return campaign;
    }
}
