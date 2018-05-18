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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CampaignServiceTest {

    @Mock
    CampaignRepository campaignRepository;

    CampaignService service;

    @Before
    public void setUp() {
        service = new CampaignService();
        service.setCampaignRepository(campaignRepository);
    }

    @Test
    public void testFindAll() {
        when(campaignRepository.findAll()).thenReturn(generateMockCampaigns());

        List<Campaign> expectedCampaigns = service.findAll();
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

    private List<Campaign> generateMockCampaigns() {
        Campaign campaign1 = new Campaign();
        campaign1.setCampaignId(1);
        campaign1.setName("Ucuz Iphone");
        campaign1.setCampaignType("PRODUCT");
        campaign1.setCampaignTypeId(7);
        campaign1.setCampaignTypeName("Apple Iphone 7 64 GB");
        campaign1.setDiscountType("RATE");
        campaign1.setDiscount(5);
        campaign1.setMaxDiscount(100);

        Campaign campaign2 = new Campaign();
        campaign2.setCampaignId(3);
        campaign2.setName("Kazaklar Daha Ucuz");
        campaign2.setCampaignType("CATEGORY");
        campaign2.setCampaignTypeId(4);
        campaign2.setCampaignTypeName("Kazak");
        campaign2.setDiscountType("RATE");
        campaign2.setDiscount(20);

        return Stream.of(campaign1, campaign2).collect(Collectors.toList());
    }
}
