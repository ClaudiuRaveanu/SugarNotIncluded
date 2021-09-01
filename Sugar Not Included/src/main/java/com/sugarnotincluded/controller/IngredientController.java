package com.sugarnotincluded.controller;

import com.sugarnotincluded.assembler.IngredientModelAssembler;
import com.sugarnotincluded.handler.ingredient.IngredientNotFoundByIdException;
import com.sugarnotincluded.model.Ingredient;
import com.sugarnotincluded.repository.IngredientRepository;
import com.sugarnotincluded.repository.RecipeRepository;
import com.sugarnotincluded.service.IngredientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredients")
@CrossOrigin(origins="*", maxAge=3600)
public class IngredientController {

    private final IngredientRepository repository;
    private final RecipeRepository recipeRepository;
    private final IngredientService service;
    private final IngredientModelAssembler assembler;

    @Autowired
    IngredientController(IngredientRepository repository, IngredientModelAssembler assembler,
                         RecipeRepository recipeRepository, IngredientService service) {
        this.repository = repository;
        this.assembler = assembler;
        this.recipeRepository = recipeRepository;
        this.service = service;
    }

    @GetMapping
    public CollectionModel<EntityModel<Ingredient>> getAll() {
        return assembler.toCollectionModel(repository.findAll());
    }

    @GetMapping("/{id}")
    public EntityModel<Ingredient> getOne(@PathVariable Long id) {
        return assembler.toModel(repository.findById(id).orElseThrow(() -> new IngredientNotFoundByIdException(id)));
    }

    @PostMapping("/add")
    public String add(@RequestBody Ingredient ingredient) {
        repository.save(ingredient);

        return "The ingredient has been saved with the following information:\n" + ingredient;
    }

    @PatchMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @RequestParam String name, @RequestParam float price, @RequestParam int stock) {
        Ingredient ingredient = repository.findById(id).orElseThrow(() -> new IngredientNotFoundByIdException(id));
        ingredient.setName(name.trim());
        ingredient.setPrice(price);
        ingredient.setStock(stock);
        repository.save(ingredient);
        return "The ingredient has been updated with the following information:\n" + ingredient;
    }

    @ApiOperation(value = "Restock ingredient", notes = "Adds the value of the parameter 'stock' to the current stock of the ingredient with the given ID")
    @PatchMapping("/restock/{id}")
    public String restock(@PathVariable Long id, @RequestParam int stock) {
        Ingredient ingredient = repository.findById(id).orElseThrow(() -> new IngredientNotFoundByIdException(id));
        ingredient.setStock(ingredient.getStock() + stock);

        return ingredient.getName() + " has been restocked with " + stock + " pieces successfully";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        try {
            repository.deleteById(id);
        } catch (IngredientNotFoundByIdException exception) {
            throw new IngredientNotFoundByIdException(id);
        }
        return "Ingredient deleted successfully";
    }

    @GetMapping("/check-stock")
    public String check() {
        return service.checkStock(repository, recipeRepository);
    }
}
