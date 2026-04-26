package com.realestate.controller;

import com.realestate.dto.InquiryDTO;
import com.realestate.dto.InquiryResponseDTO;
import com.realestate.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inquiries")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    // Buyer: Submit interest in a property
    @PostMapping
    public ResponseEntity<String> submitInquiry(
            @RequestBody InquiryDTO dto,
            Authentication auth) {
        return ResponseEntity.ok(inquiryService.submitInquiry(dto, auth.getName()));
    }

    // Seller: See all inquiries on their properties
    @GetMapping("/seller")
    public ResponseEntity<List<InquiryResponseDTO>> getSellerInquiries(
            Authentication auth) {
        return ResponseEntity.ok(inquiryService.getInquiriesForSeller(auth.getName()));
    }

    // Buyer: See their own inquiries
    @GetMapping("/buyer")
    public ResponseEntity<List<InquiryResponseDTO>> getBuyerInquiries(
            Authentication auth) {
        return ResponseEntity.ok(inquiryService.getInquiriesForBuyer(auth.getName()));
    }

    // Seller: Update inquiry status
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long id,
            @RequestParam String status,
            Authentication auth) {
        return ResponseEntity.ok(inquiryService.updateStatus(id, status, auth.getName()));
    }
}
