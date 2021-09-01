package com.sugarnotincluded;

import com.sugarnotincluded.repository.RecipeRepository;
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
public class RecipeTests {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void checkIfGetMappingsWorkFine() throws Exception {
        mockMvc.perform(get("/recipes"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/recipes/3"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(2)
    public void checkIfStringRepIsGeneratingCorrectly() throws Exception {
        mockMvc.perform(get("/recipes/string-representation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("First recipe (ID: 1)\n# Ingredients:\n\n"
                + " - 2 x Espresso Shot;\n")));
    }

    @Test
    @Order(3)
    public void checkIfAddMappingWorksFine() throws Exception {
        mockMvc.perform(post("/recipes/add").contentType(MediaType.APPLICATION_JSON)
                .content("{\"ingredients\":{\"espresso shot\":3,\"milk\":2}}"))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @Order(4)
    public void checkIfPatchMappingWorksFine() throws Exception {
        mockMvc.perform(patch("/recipes/update/6").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content("{\"ingredients\":{\"espresso shot\":4,\"milk\":1}}"))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @Order(5)
    public void checkIfDeleteMappingWorksFine() throws Exception {
        mockMvc.perform(delete("/recipes/delete/6"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Recipe deleted successfully")));
    }
}
