package com.campaign.demo.validator;

import com.campaign.demo.entity.Campaign;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class MaxDiscountValidator {

    public void validate(Campaign campaign, Errors errors) {
        if ("RATE".equalsIgnoreCase(campaign.getDiscountType()) && campaign.getMaxDiscount() == null) {
            errors.reject("Max discount is mandatory");
        }
    }
}
