package com.sugarnotincluded.controller;

import com.sugarnotincluded.assembler.BeverageModelAssembler;
import com.sugarnotincluded.handler.beverage.BeverageNotFoundByIdException;
import com.sugarnotincluded.handler.recipe.RecipeNotFoundException;
import com.sugarnotincluded.model.Beverage;
import com.sugarnotincluded.model.Recipe;
import com.sugarnotincluded.repository.BeverageRepository;
import com.sugarnotincluded.repository.IngredientRepository;
import com.sugarnotincluded.repository.RecipeRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/beverages")
public class BeverageController {

    private final BeverageRepository beverageRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final BeverageModelAssembler assembler;

    BeverageController(BeverageRepository beverageRepository, IngredientRepository ingredientRepository,
                       RecipeRepository recipeRepository, BeverageModelAssembler assembler) {
        this.beverageRepository = beverageRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Beverage>> getAll() {
        return assembler.toCollectionModel(beverageRepository.findAll());
    }

    @GetMapping("/{id}")
    public EntityModel<Beverage> getOne(@PathVariable Long id) {
        return assembler.toModel(beverageRepository.findById(id).orElseThrow(() -> new BeverageNotFoundByIdException(id)));
    }

    @GetMapping("/string-representation")
    public String get() {
        String str = "";

        for (Beverage b : beverageRepository.findAll()) {
            str += (b.getName() + " (ID: " + b.getId().toString() + ")\n");
            Recipe r = recipeRepository.getById(b.getRecipe_id());

            str += ("--== Recipe (ID: " + r.getId().toString() + ") ==--" + r.noOrdinalStringRep(ingredientRepository) + '\n');

            str += (b.totalPriceString(ingredientRepository, recipeRepository));
        }

        return str;
    }

    @PostMapping("/add-with-ingredients")
    public String add(@ApiParam(value = "Recipe", example = "{\n  \"ingredient-name_1\": quantity, \n  \"ingredient-name_2\": quantity\n}")
                          @RequestBody java.util.Map<String, Integer> ingredients, @RequestParam String beverageName) {
        String str = "";
        Recipe recipe = new Recipe(ingredients);
        recipeRepository.save(recipe);
        Beverage beverage = new Beverage(recipe.getId(), beverageName.trim());

        beverageRepository.save(beverage);

        str += "Saved the beverage with the following information: \n\n";
        str += (beverage.getName() + "(ID: " + beverage.getId() + ")\n");

        str += ("--== Recipe (ID: " + recipe.getId().toString() + ") ==--" + recipe.noOrdinalStringRep(ingredientRepository) + '\n');

        str += (beverage.totalPriceString(ingredientRepository, recipeRepository) + "\n\n");

        return str;
    }

    @PostMapping("/add-with-recipe-id")
    public String customBeverage(@RequestParam Long recipeId, @RequestParam String beverageName) {
        String str = "";

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RecipeNotFoundException(recipeId));
        Beverage beverage = new Beverage(recipe.getId(), beverageName.trim());
        beverageRepository.save(beverage);

        str += "Saved the beverage with the following information: \n\n";
        str += (beverage.getName() + "(ID: " + beverage.getId() + ")\n");

        str += ("--== Recipe (ID: " + recipe.getId().toString() + ") ==--" + recipe.noOrdinalStringRep(ingredientRepository) + '\n');

        str += (beverage.totalPriceString(ingredientRepository, recipeRepository) + "\n\n");

        return str;
    }

    @PatchMapping("/update-recipe-id/{id}")
    public String updateWithID(@PathVariable("id") Long id, @RequestParam Long recipeId, @RequestParam String beverageName) {
        String str = "";

        Beverage beverage = beverageRepository.findById(id).orElseThrow(() -> new BeverageNotFoundByIdException(id));
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RecipeNotFoundException(recipeId));
        beverage.setRecipe_id(recipeId);
        beverage.setName(beverageName.trim());
        beverageRepository.save(beverage);


        str += "Updated the beverage with the following information: \n\n";
        str += (beverage.getName() + "(ID: " + beverage.getId() + ")\n");

        str += ("--== Recipe (ID: " + recipe.getId().toString() + ") ==--" + recipe.noOrdinalStringRep(ingredientRepository) + '\n');

        str += (beverage.totalPriceString(ingredientRepository, recipeRepository) + "\n\n");

        return str;
    }

    @PatchMapping("/update-ingredients/{id}")
    public String updateWithIngredients(@ApiParam(value = "Recipe", example = "{\n  \"ingredient-name_1\": quantity, \n  \"ingredient-name_2\": quantity\n}")
    @RequestBody java.util.Map<String, Integer> ingredients, @RequestParam String beverageName, @PathVariable("id") Long id) {
        String str = "";

        Beverage beverage = beverageRepository.findById(id).orElseThrow(() -> new BeverageNotFoundByIdException(id));
        Recipe recipe = new Recipe(ingredients);
        recipeRepository.save(recipe);
        beverage.setRecipe_id(recipe.getId());
        beverage.setName(beverageName.trim());
        beverageRepository.save(beverage);

        str += "Updated the beverage with the following information: \n\n";
        str += (beverage.getName() + "(ID: " + beverage.getId() + ")\n");

        str += ("--== Recipe (ID: " + recipe.getId().toString() + ") ==--" + recipe.noOrdinalStringRep(ingredientRepository) + '\n');

        str += (beverage.totalPriceString(ingredientRepository, recipeRepository) + "\n\n");

        return str;
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        try {
            beverageRepository.deleteById(id);
        } catch (BeverageNotFoundByIdException exception) {
            throw new BeverageNotFoundByIdException(id);
        }
        return "Beverage deleted successfully";
    }
}
