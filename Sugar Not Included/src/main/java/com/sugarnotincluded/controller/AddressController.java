package com.sugarnotincluded.controller;

import com.sugarnotincluded.assembler.AddressModelAssembler;
import com.sugarnotincluded.handler.address.AddressNotFoundException;
import com.sugarnotincluded.model.Address;
import com.sugarnotincluded.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressRepository addressRepository;
    private final AddressModelAssembler assembler;

    @Autowired
    AddressController(AddressRepository addressRepository, AddressModelAssembler assembler) {
        this.addressRepository = addressRepository;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Address>> getAll() {
        return assembler.toCollectionModel(addressRepository.findAll());
    }

    @GetMapping("/{id}")
    public EntityModel<Address> getOne(@PathVariable Long id) {
        return assembler.toModel(addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(id)));
    }

    @PostMapping("/add")
    public String add(@RequestBody Address address) {
        addressRepository.save(address);

        return address.stringRep();
    }

    @PatchMapping("/update/{id}")
    public String update(@RequestBody Address address, @PathVariable("id") Long id) {
        Address a = addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(id));

        a.setStreet_name(address.getStreet_name().trim());
        a.setStreet_number(address.getStreet_number());

        if (address.getApartment_building() != null) {
            a.setApartment_building(address.getApartment_building().trim());
            a.setFloor(address.getFloor());
        }

        addressRepository.save(a);

        return a.stringRep();
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        try {
            addressRepository.deleteById(id);
        } catch (AddressNotFoundException exception) {
            throw new AddressNotFoundException(id);
        }

        return "The address was deleted successfully";
    }
}
