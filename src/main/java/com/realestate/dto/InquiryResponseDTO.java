package com.realestate.dto;

import com.realestate.model.Inquiry.InquiryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class InquiryResponseDTO {
    private Long inquiryId;
    private String buyerName;
    private String buyerEmail;
    private String propertyTitle;
    private String message;
    private InquiryStatus status;
    private LocalDateTime createdAt;
}
