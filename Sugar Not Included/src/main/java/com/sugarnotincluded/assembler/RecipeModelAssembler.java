package com.sugarnotincluded.assembler;

import com.sugarnotincluded.controller.RecipeController;
import com.sugarnotincluded.model.Recipe;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RecipeModelAssembler implements RepresentationModelAssembler<Recipe, EntityModel<Recipe>> {

    @Override
    public EntityModel<Recipe> toModel(Recipe recipe) {
        return EntityModel.of(recipe,
                linkTo(methodOn(RecipeController.class).getOne(recipe.getId())).withSelfRel(),
                linkTo(methodOn(RecipeController.class).getAll()).withRel("recipes"));
    }

    @Override
    public CollectionModel<EntityModel<Recipe>> toCollectionModel(Iterable<? extends Recipe> recipes) {
        return RepresentationModelAssembler.super.toCollectionModel(recipes);
    }
}
