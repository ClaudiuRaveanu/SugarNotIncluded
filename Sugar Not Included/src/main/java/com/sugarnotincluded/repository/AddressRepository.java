package com.sugarnotincluded.repository;

import com.sugarnotincluded.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
