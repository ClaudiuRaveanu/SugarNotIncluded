package com.sugarnotincluded.controller;

import com.sugarnotincluded.assembler.RecipeModelAssembler;
import com.sugarnotincluded.handler.recipe.RecipeNotFoundException;
import com.sugarnotincluded.model.Recipe;
import com.sugarnotincluded.repository.IngredientRepository;
import com.sugarnotincluded.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeRepository repository;
    private final IngredientRepository iRepository;
    private final RecipeModelAssembler assembler;

    @Autowired
    RecipeController(RecipeRepository repository, IngredientRepository iRepository, RecipeModelAssembler assembler) {
        this.repository = repository;
        this.iRepository = iRepository;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Recipe>> getAll() {
        return assembler.toCollectionModel(repository.findAll());
    }

    @GetMapping("/{id}")
    public EntityModel<Recipe> getOne(@PathVariable Long id) {
        return assembler.toModel(repository.findById(id).orElseThrow(() -> new RecipeNotFoundException(id)));
    }

    @GetMapping("/string-representation")
    public String get() {
        String str = "";
        for (Recipe r : repository.findAll())
            str += r.stringRep(iRepository);

        return str;
    }

    @PostMapping("/add")
    public String add(@RequestBody Recipe recipe) {
        repository.save(recipe);

        return recipe.toString();
    }

    @PatchMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @RequestBody Recipe recipe) {
        Recipe find = repository.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
        find.setIngredients(recipe.getIngredients());
        repository.save(find);

        return find.stringRep(iRepository);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        try {
            repository.deleteById(id);
        } catch (RecipeNotFoundException exception) {
            throw new RecipeNotFoundException(id);
        }
        return "Recipe deleted successfully";
    }
}
