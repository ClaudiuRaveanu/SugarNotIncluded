package com.sugarnotincluded.assembler;

import com.sugarnotincluded.controller.OrderController;
import com.sugarnotincluded.model.Order;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

    @Override
    public EntityModel<Order> toModel(Order order) {
        return EntityModel.of(order,
                linkTo(methodOn(OrderController.class).getOne(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).getAll()).withSelfRel());
    }

    @Override
    public CollectionModel<EntityModel<Order>> toCollectionModel(Iterable<? extends Order> orders) {
        return RepresentationModelAssembler.super.toCollectionModel(orders);
    }
}
