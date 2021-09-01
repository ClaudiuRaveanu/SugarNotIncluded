package com.sugarnotincluded.repository;

import com.sugarnotincluded.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Ingredient findIngredientByNameIgnoreCase(String name);
}
