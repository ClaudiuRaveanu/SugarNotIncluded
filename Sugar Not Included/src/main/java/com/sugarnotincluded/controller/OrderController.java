package com.sugarnotincluded.controller;

import com.sugarnotincluded.assembler.OrderModelAssembler;
import com.sugarnotincluded.handler.order.OrderNotFoundException;
import com.sugarnotincluded.model.Address;
import com.sugarnotincluded.model.Order;
import com.sugarnotincluded.repository.*;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository repository;
    private final OrderModelAssembler assembler;
    private final CustomerRepository customerRepository;
    private final BeverageRepository beverageRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderModelAssembler assembler, CustomerRepository customerRepository,
                           IngredientRepository ingredientRepository, BeverageRepository beverageRepository, RecipeRepository recipeRepository) {
        this.repository = orderRepository;
        this.assembler = assembler;
        this.customerRepository = customerRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.beverageRepository = beverageRepository;
    }

    @GetMapping
    public CollectionModel<EntityModel<Order>> getAll() {
        return assembler.toCollectionModel(repository.findAll());
    }

    @GetMapping("/{id}")
    public EntityModel<Order> getOne(@PathVariable Long id) {
        return assembler.toModel(repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id)));
    }

    @PostMapping("/add")
    public EntityModel<Order> add(@RequestParam String customerName, @RequestBody(required = false) Address address, @RequestParam String beverages) {
        Order order = new Order();

        if (order.checkCustomerName(customerRepository, customerName)) {

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            java.util.Date date = new java.util.Date();

            order.setCustomerName(customerName);
            order.setBeverages(beverages);
            order.setOrder_date(format.format(date));
            order.setPick_up((address.getStreet_name() == null) ? false : true);
            order.setPrice(order.getTotalPrice(beverageRepository, recipeRepository, ingredientRepository));
        }

        repository.save(order);

        return assembler.toModel(order);
    }
    
    @PatchMapping("/update/{id}")
    public EntityModel<Order> update(@PathVariable Long id, @RequestParam String customerName, @RequestBody(required = false) Address address, @RequestParam String beverages) {
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        if (order.checkCustomerName(customerRepository, customerName)) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            java.util.Date date = new java.util.Date();

            order.setCustomerName(customerName);
            order.setBeverages(beverages);
            order.setOrder_date(format.format(date));
            order.setPick_up((address.getStreet_name() == null) ? false : true);
            order.setPrice(order.getTotalPrice(beverageRepository, recipeRepository, ingredientRepository));
        }

        repository.save(order);

        return assembler.toModel(order);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        try {
            repository.deleteById(id);
        } catch (OrderNotFoundException exception) {
            throw new OrderNotFoundException(id);
        }
        return "Order deleted successfully";
    }
}
