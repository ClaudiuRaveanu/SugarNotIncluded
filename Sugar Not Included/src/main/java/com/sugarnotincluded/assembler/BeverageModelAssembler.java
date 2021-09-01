package com.sugarnotincluded.assembler;

import com.sugarnotincluded.controller.BeverageController;
import com.sugarnotincluded.model.Beverage;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class BeverageModelAssembler implements RepresentationModelAssembler<Beverage, EntityModel<Beverage>> {

    @Override
    public EntityModel<Beverage> toModel(Beverage beverage) {
        return EntityModel.of(beverage,
                linkTo(methodOn(BeverageController.class).getOne(beverage.getId())).withSelfRel(),
                linkTo(methodOn(BeverageController.class).getAll()).withRel("beverages"));
    }

    @Override
    public CollectionModel<EntityModel<Beverage>> toCollectionModel(Iterable<? extends Beverage> beverages) {
        return RepresentationModelAssembler.super.toCollectionModel(beverages);
    }
}
