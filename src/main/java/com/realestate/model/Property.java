package com.realestate.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Double price;
    private String location;
    private String city;
    private String state;
    private Double area;
    private Integer bedrooms;
    private Integer bathrooms;

    @Enumerated(EnumType.STRING)
    private PropertyType type;

    @Enumerated(EnumType.STRING)
    private ListingType listingType;

    private String imageUrl;
    private boolean featured;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public enum PropertyType {
        APARTMENT, VILLA, PLOT, COMMERCIAL
    }

    public enum ListingType {
        BUY, SELL, RENT
    }
}
