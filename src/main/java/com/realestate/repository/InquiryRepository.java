package com.realestate.repository;

import com.realestate.model.Inquiry;
import com.realestate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    @Query("SELECT i FROM Inquiry i WHERE i.property.owner = :seller")
    List<Inquiry> findByPropertyOwner(@Param("seller") User seller);

    List<Inquiry> findByBuyer(User buyer);

    List<Inquiry> findByPropertyId(Long propertyId);
}
