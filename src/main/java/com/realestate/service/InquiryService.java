package com.realestate.service;

import com.realestate.dto.InquiryDTO;
import com.realestate.dto.InquiryResponseDTO;
import com.realestate.model.*;
import com.realestate.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    public String submitInquiry(InquiryDTO dto, String buyerEmail) {
        User buyer = userRepository.findByEmail(buyerEmail)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        Inquiry inquiry = Inquiry.builder()
                .buyer(buyer)
                .property(property)
                .message(dto.getMessage())
                .build();

        inquiryRepository.save(inquiry);
        return "Inquiry submitted successfully!";
    }

    public List<InquiryResponseDTO> getInquiriesForSeller(String sellerEmail) {
        User seller = userRepository.findByEmail(sellerEmail)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        return inquiryRepository.findByPropertyOwner(seller)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<InquiryResponseDTO> getInquiriesForBuyer(String buyerEmail) {
        User buyer = userRepository.findByEmail(buyerEmail)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        return inquiryRepository.findByBuyer(buyer)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public String updateStatus(Long inquiryId, String status, String sellerEmail) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));

        if (!inquiry.getProperty().getOwner().getEmail().equals(sellerEmail)) {
            throw new RuntimeException("Unauthorized action");
        }

        inquiry.setStatus(Inquiry.InquiryStatus.valueOf(status.toUpperCase()));
        inquiryRepository.save(inquiry);
        return "Status updated to " + status;
    }

    private InquiryResponseDTO toResponseDTO(Inquiry inquiry) {
        return new InquiryResponseDTO(
                inquiry.getId(),
                inquiry.getBuyer().getName(),
                inquiry.getBuyer().getEmail(),
                inquiry.getProperty().getTitle(),
                inquiry.getMessage(),
                inquiry.getStatus(),
                inquiry.getCreatedAt()
        );
    }
}
