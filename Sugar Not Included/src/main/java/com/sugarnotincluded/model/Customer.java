package com.sugarnotincluded.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private Long address_id;

    private Long order_id;

    public Customer(String name) {
        this.name = name;
    }

    public Customer(String name, Long address_id, Long order_id) {
        this.name = name;
        this.address_id = address_id;
        this.order_id = order_id;
    }

    public Customer(String name, Long order_id) {
        this.name = name;
        this.order_id = order_id;
    }

    public Customer() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Long address_id) {
        this.address_id = address_id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;

        return id.equals(customer.id) && name.equals(customer.name) && address_id.equals(customer.address_id) && order_id.equals(customer.order_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address_id, order_id);
    }
}
