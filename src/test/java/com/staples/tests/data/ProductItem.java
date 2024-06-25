package com.staples.tests.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.management.ConstructorParameters;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductItem {
    @ConstructorParameters({"productName"})
    public ProductItem(String productName) {};

    private String productName;
    private Double productPrice;

    @Override
    public boolean equals(Object productItem) {
        if (this == productItem) return true;
        if (productItem == null || getClass() != productItem.getClass()) return false;
        ProductItem obj = (ProductItem) productItem;
        return Objects.equals(productName, obj.productName);
    }

    public int getAmtForCoupon(double minForCouponAmt) {
        return (int) Math.round(minForCouponAmt / this.productPrice);
    }
}
