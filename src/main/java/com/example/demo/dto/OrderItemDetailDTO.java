package com.example.demo.dto;

/**
 * Mirrors com.orders.orders.dto.OrderItemDetailDTO structure
 * including nested ProductDetailsDTO.
 */
public class OrderItemDetailDTO {

    private String productId;
    private Integer quantity;
    private ProductDetailsDTO product;

    public OrderItemDetailDTO() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductDetailsDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDetailsDTO product) {
        this.product = product;
    }
}
