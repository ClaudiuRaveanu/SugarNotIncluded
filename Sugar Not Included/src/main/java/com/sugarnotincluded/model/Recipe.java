package com.sugarnotincluded.model;

import com.ibm.icu.text.RuleBasedNumberFormat;
import com.sugarnotincluded.handler.ingredient.IngredientNotFoundByNameException;
import com.sugarnotincluded.repository.IngredientRepository;

import javax.persistence.*;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "Recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    Usage example:

    * Let's say you have Espresso with ID 1 and Milk with ID 2
    * Use case is: 1 1, 2 2
    * Explanation: 1 espresso shot with 1/2 cups whole milk

    @Column(nullable = false)
     */
    @ElementCollection
    private Map<String, Integer> ingredients;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe() {}

    public Recipe(Map<String, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public Recipe(int id, Map<String, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<String, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id.equals(recipe.id) && ingredients.equals(recipe.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ingredients);
    }

    public String stringRep(IngredientRepository repository) {
        String str = "";

        RuleBasedNumberFormat numberFormat = new RuleBasedNumberFormat(Locale.UK, RuleBasedNumberFormat.SPELLOUT);

        str += numberFormat.format(this.getId(), "%spellout-ordinal").substring(0, 1).toUpperCase() +
                numberFormat.format(this.getId(), "%spellout-ordinal").substring(1) + " recipe (ID: " + this.getId().toString() + ')' + "\n# Ingredients:\n\n";

        for (Map.Entry<String, Integer> e : this.getIngredients().entrySet()) {
            try {
                Ingredient ingredient = repository.findIngredientByNameIgnoreCase(e.getKey());
                str += (" - " + e.getValue() + " x " + ingredient.getName() + ";\n");
            } catch (IngredientNotFoundByNameException exception) {
                throw new IngredientNotFoundByNameException(e.getKey());
            }
        }
        str += '\n';

        return str;
    }

    public String noOrdinalStringRep(IngredientRepository repository) {
        String str = "\n\n";

        for (Map.Entry<String, Integer> e : this.getIngredients().entrySet()) {
            try {
                Ingredient ingredient = repository.findIngredientByNameIgnoreCase(e.getKey());
                str += (" - " + e.getValue() + " x " + ingredient.getName() + ";\n");
            } catch (IngredientNotFoundByNameException exception) {
                throw new IngredientNotFoundByNameException(e.getKey());
            }
        }

        return str;
    }
}
