package com.sugarnotincluded;

import com.sugarnotincluded.repository.BeverageRepository;
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

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BeverageTests {

    @Autowired
    private BeverageRepository beverageRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void checkIfGetMappingsWorkFine() throws Exception {
        mockMvc.perform(get("/beverages"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/beverages/2"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Macchiatto")));
    }

    @Test
    @Order(2)
    public void checkIfAddMappingsWorkFine() throws Exception {
        mockMvc.perform(post("/beverages/add-with-ingredients").contentType(MediaType.APPLICATION_JSON)
                .content("{\"espresso shot\":2, \"milk\":2}").param("beverageName", "Milk Macchiatto"))
                .andExpect(status().isOk()).andDo(print());

        mockMvc.perform(post("/beverages/add-with-recipe-id")
                .param("recipeId", "2").param("beverageName", "Macchiatto Copy"))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @Order(3)
    public void checkIfPatchMappingsWorkFine() throws Exception {
        mockMvc.perform(patch("/beverages/update-recipe-id/2").param("recipeId", "3").param("beverageName", "Drunk Coffee Miel"))
                .andExpect(status().isOk());

        mockMvc.perform(patch("/beverages/update-ingredients/3").contentType(MediaType.APPLICATION_JSON)
                .content("{\"espresso shot\":4}").param("beverageName", "Hard Espresso"))
                .andExpect(content().string(containsString("4 x Espresso Shot;"))).andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void checkIfDeleteMappingWorksFine() throws Exception {
        mockMvc.perform(delete("/beverages/delete/7")).andExpect(status().isOk());
    }
}
