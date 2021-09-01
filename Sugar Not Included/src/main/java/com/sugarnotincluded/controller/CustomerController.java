package com.sugarnotincluded.controller;

import com.sugarnotincluded.assembler.CustomerModelAssembler;
import com.sugarnotincluded.handler.customer.CustomerNotFoundByIdException;
import com.sugarnotincluded.model.Customer;
import com.sugarnotincluded.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final CustomerModelAssembler assembler;

    @Autowired
    CustomerController(CustomerRepository customerRepository, CustomerModelAssembler assembler) {
        this.customerRepository = customerRepository;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Customer>> getAll() {
        return assembler.toCollectionModel(customerRepository.findAll());
    }

    @GetMapping("/{id}")
    public EntityModel<Customer> getOne(@PathVariable Long id) {
        return assembler.toModel(customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundByIdException(id)));
    }

    @PostMapping("/add")
    public EntityModel<Customer> add(@RequestBody Customer customer) {
        customerRepository.save(customer);
        return assembler.toModel(customer);
    }

    @PatchMapping("/update/{id}")
    public EntityModel<Customer> update(@RequestBody Customer customer, @PathVariable Long id) {
        Customer c = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundByIdException(id));
        c.setName(customer.getName());
        c.setAddress_id(customer.getAddress_id());
        c.setOrder_id(customer.getOrder_id());

        customerRepository.save(c);
        return assembler.toModel(c);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        try {
            customerRepository.deleteById(id);
        } catch (CustomerNotFoundByIdException exception) {
            throw new CustomerNotFoundByIdException(id);
        }

        return "Customer deleted successfully";
    }
}
