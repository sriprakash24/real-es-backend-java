package com.realestate.dto;

import lombok.Data;

@Data
public class PropertyDTO {
    private String title;
    private String description;
    private Double price;
    private String location;
    private String city;
    private String state;
    private Double area;
    private Integer bedrooms;
    private Integer bathrooms;
    private String type;
    private String listingType;
    private String imageUrl;
    private boolean featured;
}
