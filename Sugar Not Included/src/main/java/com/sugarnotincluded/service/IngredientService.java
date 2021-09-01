package com.sugarnotincluded.service;

import com.sugarnotincluded.model.Ingredient;
import com.sugarnotincluded.model.Recipe;
import com.sugarnotincluded.repository.IngredientRepository;
import com.sugarnotincluded.repository.RecipeRepository;
import org.springframework.stereotype.Service;

@Service("iService")
public class IngredientService {

    public String checkStock(IngredientRepository iRepository, RecipeRepository rRepository) {
        String str = "Checking ingredients for the next three coffees... \n\n";

        for (Ingredient i : iRepository.findAll()) {
            int needed_amount = 0;

            for (Recipe r : rRepository.findAll())
                needed_amount += (r.getIngredients().get(i.getName()) == null) ? 0 : r.getIngredients().get(i.getName());

            str += "* " + i.getName() + " - Stock available: " + i.getStock() + "\n";

            if (i.getStock() < needed_amount * 3)
                str += "  !!! Insufficient stock of '" + i.getName() + "' for the next three coffees !!! (Stock needed: " + needed_amount * 3 + ")\n";
        }
        return str + "\nStock inventory finished successfully";
    }
}
