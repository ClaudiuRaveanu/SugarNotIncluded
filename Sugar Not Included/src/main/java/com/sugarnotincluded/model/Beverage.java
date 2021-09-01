package com.sugarnotincluded.model;

import com.sugarnotincluded.handler.ingredient.IngredientNotFoundByNameException;
import com.sugarnotincluded.repository.IngredientRepository;
import com.sugarnotincluded.repository.RecipeRepository;

import javax.persistence.*;
import java.util.Objects;
import java.util.Map;

@Entity
@Table(name = "Beverages")
public class Beverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long recipe_id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(Long recipe_id) {
        this.recipe_id = recipe_id;
    }

    public Beverage() {}

    public Beverage(Long recipe_id, String name) {
        this.recipe_id = recipe_id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beverage beverage = (Beverage) o;
        return id.equals(beverage.id) && recipe_id.equals(beverage.recipe_id) && name.equals(beverage.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipe_id, name);
    }

    public float getTotalPrice(IngredientRepository ingredientRepository, RecipeRepository recipeRepository) {
        float total_price = 0.00f;
        Recipe r = recipeRepository.getById(this.getRecipe_id());

        for (Map.Entry<String, Integer> e : r.getIngredients().entrySet()) {
            try {
                Ingredient ingredient = ingredientRepository.findIngredientByNameIgnoreCase(e.getKey());

                total_price += (ingredient.getPrice() * e.getValue() + 0.5f);
            } catch (IngredientNotFoundByNameException exception) {

                throw new IngredientNotFoundByNameException(e.getKey());
            }
        }

        return total_price;
    }

    public String totalPriceString(IngredientRepository ingredientRepository, RecipeRepository recipeRepository) {
        String str = "Total price: $";
        Recipe r = recipeRepository.getById(this.getRecipe_id());

        return str + this.getTotalPrice(ingredientRepository, recipeRepository) + "\n\n";
    }
}
