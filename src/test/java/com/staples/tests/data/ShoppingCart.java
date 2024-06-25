package com.staples.tests.data;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<ProductItem, Integer> mapShoppingCart;

    public ShoppingCart() {
        this.mapShoppingCart = new HashMap<>();
    }

    public void addProduct(ProductItem productItem, Integer qty) {
        if (this.mapShoppingCart.containsKey(productItem)) {
            this.mapShoppingCart.compute(productItem, (key, val) -> val += qty);
        } else {
            this.mapShoppingCart.put(productItem, qty);
        }
    }

    public void removeProduct(ProductItem productItem) {
        if (this.mapShoppingCart.containsKey(productItem))
            this.mapShoppingCart.remove(productItem);
    }

    public double calcCartTotal() {
        double cartTotal = 0.0;

        for (Map.Entry<ProductItem, Integer> productItemEntry : this.mapShoppingCart.entrySet()) {
            ProductItem productItem = productItemEntry.getKey();
            cartTotal += productItem.getProductPrice() * productItemEntry.getValue();
        }

        return cartTotal;
    }
}
