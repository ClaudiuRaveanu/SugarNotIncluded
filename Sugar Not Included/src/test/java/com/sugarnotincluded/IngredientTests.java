package com.sugarnotincluded;

import com.sugarnotincluded.repository.IngredientRepository;
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
public class IngredientTests {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void checkIfGetMappingsWorkFine() throws Exception {
        mockMvc.perform(get("/ingredients"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/ingredients/3"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/ingredients/20"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Couldn't find Ingredient with ID 20")));
    }

    @Test
    @Order(2)
    public void checkIfAddMappingWorksFine() throws Exception {
        mockMvc.perform(post("/ingredients/add").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Ice cube\",\"price\":0.2,\"stock\":18}"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/ingredients/" + ingredientRepository.findAll().size()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    public void checkIfUpdateMappingWorksFine() throws Exception {
        mockMvc.perform(patch("/ingredients/update/" + ingredientRepository.findAll().size())
                .param("name", "Ice").param("price", "0.4").param("stock", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id = " + ingredientRepository.findAll().size() + ", name = 'Ice', " +
                        "price = 0.4, stock = 10")));
    }

    @Test
    @Order(4)
    public void checkIfRestockMappingWorksFine() throws Exception {
        mockMvc.perform(patch("/ingredients/restock/" + ingredientRepository.findAll().size())
                .param("stock", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("restocked with 10 pieces")));
    }

    @Test
    @Order(5)
    public void checkIfDeleteMappingWorksFine() throws Exception {
        mockMvc.perform(delete("/ingredients/delete/" + ingredientRepository.findAll().size()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ingredient deleted successfully")));
    }
}
