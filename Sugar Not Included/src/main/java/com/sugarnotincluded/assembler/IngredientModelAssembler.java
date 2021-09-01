package com.sugarnotincluded.assembler;

import com.sugarnotincluded.controller.IngredientController;
import com.sugarnotincluded.model.Ingredient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class IngredientModelAssembler implements RepresentationModelAssembler<Ingredient, EntityModel<Ingredient>> {

    @Override
    public EntityModel<Ingredient> toModel(Ingredient ingredient) {
        return EntityModel.of(ingredient,
                linkTo(methodOn(IngredientController.class).getOne(ingredient.getId())).withSelfRel(),
                linkTo(methodOn(IngredientController.class).getAll()).withRel("ingredients"));
    }

    @Override
    public CollectionModel<EntityModel<Ingredient>> toCollectionModel(Iterable<? extends Ingredient> ingredients) {
        return RepresentationModelAssembler.super.toCollectionModel(ingredients);
    }
}
