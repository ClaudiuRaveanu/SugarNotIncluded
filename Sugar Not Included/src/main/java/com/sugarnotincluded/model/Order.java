package com.sugarnotincluded.model;

import com.sugarnotincluded.handler.beverage.BeverageNotFoundByNameException;
import com.sugarnotincluded.handler.customer.CustomerNotFoundByNameException;
import com.sugarnotincluded.repository.BeverageRepository;
import com.sugarnotincluded.repository.CustomerRepository;
import com.sugarnotincluded.repository.IngredientRepository;
import com.sugarnotincluded.repository.RecipeRepository;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.StringTokenizer;

@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String order_date;

    @NotNull
    private String beverages;

    @NotNull
    private String customerName;

    private float price;

//    @Column(columnDefinition = "boolean not null default false")
    private boolean pick_up = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getBeverages() {
        return beverages;
    }

    public void setBeverages(String beverages) {
        this.beverages = beverages;
    }

    public boolean isPick_up() {
        return pick_up;
    }

    public void setPick_up(boolean pick_up) {
        this.pick_up = pick_up;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Order() {}

    public Order(String customerName, String beverages, String order_date, boolean pick_up, float price) {
        this.customerName = customerName;
        this.order_date = order_date;
        this.beverages = beverages;
        this.pick_up = pick_up;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (this == o) return true;
        Order order = (Order) o;
        return pick_up == order.pick_up && customerName.equals(order.customerName) && Float.compare(price, order.price) == 0 &&
                order_date.equals(order.order_date) && beverages.equals(order.beverages);
    }

    public boolean checkCustomerName(CustomerRepository repository, String customerName) {
        try {
            Customer customer = repository.findCustomerByNameIgnoreCase(customerName);
        } catch (CustomerNotFoundByNameException exception) {
            throw new CustomerNotFoundByNameException(this.getCustomerName());
        }

        return true;
    }

    public float getTotalPrice(BeverageRepository bRepository, RecipeRepository rRepository, IngredientRepository iRepository) {
        float total = 0.00f;

        StringTokenizer tokenizer = new StringTokenizer(this.getBeverages(), ",");

        while(tokenizer.hasMoreTokens()) {
            try {
                Beverage beverage = bRepository.findBeverageByNameIgnoreCase(tokenizer.nextToken().trim());

                total += beverage.getTotalPrice(iRepository, rRepository);
            } catch (BeverageNotFoundByNameException exception) {
                throw new BeverageNotFoundByNameException(tokenizer.nextToken().trim());
            }
        }

        return total;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order_date, beverages, pick_up, customerName, price);
    }
}
