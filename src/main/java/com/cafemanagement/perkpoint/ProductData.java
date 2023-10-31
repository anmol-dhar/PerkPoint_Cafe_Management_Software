package com.cafemanagement.perkpoint;

import java.util.*;

public class ProductData {

    private Integer id, stock, price;
    private String productId, productName, type, status, image;
    private Date date;

    public ProductData(Integer id, String productId, String productName,
                       String type, Integer stock, Integer price,
                       String status, String image, Date date){

        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.stock = stock;
        this.price = price;
        this.status = status;
        this.image = image;
        this.date = date;

    }

    public Integer getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getType() {
        return type;
    }

    public Integer getStock() {
        return stock;
    }

    public Integer getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }



}
