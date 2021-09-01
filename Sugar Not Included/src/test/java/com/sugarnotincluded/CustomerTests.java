package com.sugarnotincluded;

import com.sugarnotincluded.model.Customer;
import com.sugarnotincluded.repository.CustomerRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerTests {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer c1 = new Customer("Danny Paps");
    private Customer c2 = new Customer("Mary Jones");
    private java.util.List<Customer> customers = new ArrayList<>() {{ add(c1); add(c2); }};

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void checkIfGetMappingsWorkFine() throws Exception {
        customerRepository.saveAll(customers);
        mockMvc.perform(get("/customers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":4,\"name\":\"Danny Paps\",\"address_id\":null,\"order_id\":null," +
                        "\"_links\":{\"self\":{\"href\":\"http://localhost/customers/4\"},\"customers\":{\"href\":\"http://localhost/customers\"}}}," +
                        "{\"id\":5,\"name\":\"Mary Jones\",\"address_id\":null,\"order_id\":null," +
                        "\"_links\":{\"self\":{\"href\":\"http://localhost/customers/5\"},\"customers\":{\"href\":\"http://localhost/customers\"}}}]}}")));

        mockMvc.perform(get("/customers/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":5,\"name\":\"Mary Jones\",\"address_id\":null,\"order_id\":null," +
                        "\"_links\":{\"self\":{\"href\":\"http://localhost/customers/5\"},\"customers\":{\"href\":\"http://localhost/customers\"}}}")));
    }

    @Test
    @Order(2)
    public void checkIfAddMappingWorksFine() throws Exception {
        mockMvc.perform(post("/customers/add").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Andrew Thomas\",\"address_id\":null,\"order_id\":null}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":6,\"name\":\"Andrew Thomas\",\"address_id\":null,\"order_id\":null," +
                        "\"_links\":{\"self\":{\"href\":\"http://localhost/customers/6\"},\"customers\":{\"href\":\"http://localhost/customers\"}}}")));
    }

    @Test
    @Order(3)
    public void checkIfPatchMappingWorksFine() throws Exception {
        mockMvc.perform(patch("/customers/update/4").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Daniel Papps\"}")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":4,\"name\":\"Daniel Papps\",\"address_id\":null,\"order_id\":null," +
                        "\"_links\":{\"self\":{\"href\":\"http://localhost/customers/4\"},\"customers\":{\"href\":\"http://localhost/customers\"}}}")));
    }

    @Test
    @Order(4)
    public void checkIfDeleteMappingWorksFine() throws Exception {
        mockMvc.perform(delete("/customers/delete/6"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Customer deleted successfully")));
    }
}
