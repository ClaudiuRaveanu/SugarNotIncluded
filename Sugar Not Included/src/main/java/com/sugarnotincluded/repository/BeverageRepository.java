package com.sugarnotincluded.repository;

import com.sugarnotincluded.model.Beverage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeverageRepository extends JpaRepository<Beverage, Long> {

    Beverage findBeverageByNameIgnoreCase(String name);
}
