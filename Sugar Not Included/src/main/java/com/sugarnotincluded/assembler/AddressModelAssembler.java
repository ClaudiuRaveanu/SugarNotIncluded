package com.sugarnotincluded.assembler;

import com.sugarnotincluded.controller.AddressController;
import com.sugarnotincluded.model.Address;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AddressModelAssembler implements RepresentationModelAssembler<Address, EntityModel<Address>> {

    @Override
    public EntityModel<Address> toModel(Address address) {
        return EntityModel.of(address,
                linkTo(methodOn(AddressController.class).getOne(address.getId())).withSelfRel(),
                linkTo(methodOn(AddressController.class).getAll()).withRel("addresses"));
    }

    @Override
    public CollectionModel<EntityModel<Address>> toCollectionModel(Iterable<? extends Address> addresses) {
        return RepresentationModelAssembler.super.toCollectionModel(addresses);
    }
}
