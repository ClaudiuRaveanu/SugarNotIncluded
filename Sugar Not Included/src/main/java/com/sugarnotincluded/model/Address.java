package com.sugarnotincluded.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street_name;

    private int street_number;

    private String apartment_building;

    private int floor = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public int getStreet_number() {
        return street_number;
    }

    public void setStreet_number(int street_number) {
        this.street_number = street_number;
    }

    public String getApartment_building() {
        return apartment_building;
    }

    public void setApartment_building(String apartment_building) {
        this.apartment_building = apartment_building;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public Address() {}

    /**
     * Address Constructor for Customers who are living in an apartment.
     *
     * @param street_name
     * @param street_number
     * @param apartment_building
     * @param floor
     */
    public Address(String street_name, int street_number, String apartment_building, int floor) {
        this.street_name = street_name;
        this.street_number = street_number;
        this.apartment_building = apartment_building;
        this.floor = floor;
    }

    /**
     * Address Constructor for Customers who live in a house.
     *
     * @param street_name
     * @param street_number
     */
    public Address(String street_name, int street_number) {
        this.street_number = street_number;
        this.street_name = street_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;

        return street_number == address.street_number && apartment_building.equals(address.apartment_building) &&
                floor == address.floor && id.equals(address.id) && street_name.equals(address.street_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street_name, street_number, apartment_building, floor);
    }

    public String stringRep() {
        String str = "The " + (this.getApartment_building() == null ? "house " : "apartment ") + "address: \n\n{\n";

        str += ("  Street: " + this.getStreet_name() + "\n  Nr.: " + this.getStreet_number());
        str += (this.getApartment_building() == null ? "" : "\n  Apartment: " + this.getApartment_building() + "\n  Floor: " + this.getFloor());
        str += "\n}\n\nwas saved successfully";

        return str;
    }
}
