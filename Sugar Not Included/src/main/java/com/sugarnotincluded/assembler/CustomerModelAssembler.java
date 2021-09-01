package com.sugarnotincluded.assembler;

import com.sugarnotincluded.controller.CustomerController;
import com.sugarnotincluded.model.Customer;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CustomerModelAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {

    @Override
    public EntityModel<Customer> toModel(Customer customer) {
        return EntityModel.of(customer,
                linkTo(methodOn(CustomerController.class).getOne(customer.getId())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).getAll()).withRel("customers"));
    }

    @Override
    public CollectionModel<EntityModel<Customer>> toCollectionModel(Iterable<? extends Customer> customers) {
        return RepresentationModelAssembler.super.toCollectionModel(customers);
    }
}
