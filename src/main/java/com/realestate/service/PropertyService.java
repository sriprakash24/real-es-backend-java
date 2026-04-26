package com.realestate.service;

import com.realestate.dto.PropertyDTO;
import com.realestate.model.*;
import com.realestate.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public Property addProperty(PropertyDTO dto, String email) {
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Property property = Property.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .location(dto.getLocation())
                .city(dto.getCity())
                .state(dto.getState())
                .area(dto.getArea())
                .bedrooms(dto.getBedrooms())
                .bathrooms(dto.getBathrooms())
                .type(Property.PropertyType.valueOf(dto.getType().toUpperCase()))
                .listingType(Property.ListingType.valueOf(dto.getListingType().toUpperCase()))
                .imageUrl(dto.getImageUrl())
                .featured(dto.isFeatured())
                .owner(owner)
                .build();

        return propertyRepository.save(property);
    }

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));
    }

    public List<Property> searchProperties(String city, String type,
                                            Double minPrice, Double maxPrice) {
        return propertyRepository.searchProperties(city, type, minPrice, maxPrice);
    }

    public List<Property> getFeaturedProperties() {
        return propertyRepository.findByFeaturedTrue();
    }

    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }
}
