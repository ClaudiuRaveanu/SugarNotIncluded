package com.sugarnotincluded;

import com.sugarnotincluded.repository.OrderRepository;
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

import java.text.SimpleDateFormat;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderTests {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void checkIfGetMappingsWorkFine() throws Exception {
        mockMvc.perform((get("/orders"))).andExpect(status().isOk());
        mockMvc.perform(get("/orders/1")).andExpect(status().isNotFound());

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();

        com.sugarnotincluded.model.Order order = new com.sugarnotincluded.model.Order("John Smith", "Coffee Miel",
                format.format(date), false, 10.0f);

        repository.save(order);

        mockMvc.perform(get("/orders/1")).andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void checkIfAddMappingWorksFine() throws Exception {
        mockMvc.perform(post("/orders/add").param("customerName", "Judy Monroe")
                .contentType(MediaType.APPLICATION_JSON).content("{}")
                .param("beverages", "Macchiatto"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/orders/add").param("customerName", "William Smith")
                .contentType(MediaType.APPLICATION_JSON).content("{\"street_name\":\"Saint Arnold\",\"street_number\":5}")
                .param("beverages", "Macchiatto, Espresso"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    public void checkIfPatchMappingWorks() throws Exception {
        mockMvc.perform(patch("/orders/update/1").param("customerName", "Judy Monroe")
                .contentType(MediaType.APPLICATION_JSON).content("{}")
        .param("beverages", "Macchiatto, Coffee Latte"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Macchiatto, Coffee Latte")));
    }

    @Test
    @Order(4)
    public void checkIfDeleteMappingWorks() throws Exception {
        mockMvc.perform(delete("/orders/delete/1")).andExpect(status().isOk());
    }
}
