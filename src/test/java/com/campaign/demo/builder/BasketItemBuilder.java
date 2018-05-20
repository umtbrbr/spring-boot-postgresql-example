package com.campaign.demo.builder;

import com.campaign.demo.entity.BasketItem;

import java.math.BigDecimal;

public class BasketItemBuilder {

    private BasketItem basketItem;

    public BasketItemBuilder() {
        basketItem = new BasketItem();
    }

    public BasketItemBuilder withProductId(Integer productId) {
        basketItem.setProductId(productId);
        return this;
    }

    public BasketItemBuilder withCategoryId(Integer categoryId) {
        basketItem.setCategoryId(categoryId);
        return this;
    }

    public BasketItemBuilder withPrice(BigDecimal price) {
        basketItem.setPrice(price);
        return this;
    }

    public BasketItemBuilder withDiscountedPrice(BigDecimal discountedPrice) {
        basketItem.setDiscountedPrice(discountedPrice);
        return this;
    }

    public BasketItem build() {
        return basketItem;
    }
}
