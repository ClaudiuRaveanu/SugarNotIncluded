package com.sugarnotincluded;

import com.sugarnotincluded.model.Address;
import com.sugarnotincluded.repository.AddressRepository;
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
public class AddressTests {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MockMvc mockMvc;

    private Address a1 = new Address("Street 1", 4);
    private Address a2 = new Address("Street 2", 2, "6B", 1);
    private java.util.List<Address> testAddresses = new ArrayList<>() {{ add(a1); add((a2)); }};

    @Test
    @Order(1)
    public void checkIfAddressesModelsAreGeneratedCorrectly() throws Exception {
        addressRepository.saveAll(testAddresses);

        mockMvc.perform(get("/addresses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"_embedded\":" +
                        "{\"addressList\":[" +
                        "{\"id\":1,\"street_name\":\"Street 1\",\"street_number\":4,\"apartment_building\":null,\"floor\":0," +
                        "\"_links\":{\"self\":{\"href\":\"http://localhost/addresses/1\"},\"addresses\":{\"href\":\"http://localhost/addresses\"}}}," +
                        "{\"id\":2,\"street_name\":\"Street 2\",\"street_number\":2,\"apartment_building\":\"6B\",\"floor\":1," +
                        "\"_links\":{\"self\":{\"href\":\"http://localhost/addresses/2\"},\"addresses\":{\"href\":\"http://localhost/addresses\"}}}]}}")));
    }

    @Test
    @Order(2)
    public void checkIfAddressStringRepIsOkAfterSavingIt() throws Exception {

        //house-add endpoint test
        mockMvc.perform(post("/addresses/add").contentType(MediaType.APPLICATION_JSON).content("{\"id\":3,\"street_name\":\"Street 3\",\"street_number\":7}"))
                .andExpect(status().isOk()).andExpect(content().string(containsString("The house address: \n" +
                "\n" +
                "{\n" +
                "  Street: Street 3\n" +
                "  Nr.: 7\n" +
                "}\n" +
                "\n" +
                "was saved successfully")));

        //apartment-add endpoint test
        mockMvc.perform(post("/addresses/add").contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":4,\"street_name\":\"Street 4\",\"street_number\":9,\"apartment_building\":\"7A\",\"floor\":2}"))
                .andExpect(status().isOk()).andExpect(content().string(containsString("The apartment address: \n" +
                "\n" +
                "{\n" +
                "  Street: Street 4\n" +
                "  Nr.: 9\n" +
                "  Apartment: 7A\n" +
                "  Floor: 2\n" +
                "}\n" +
                "\n" +
                "was saved successfully")));
    }

    @Test
    @Order(3)
    public void checkIfGetOneEndpointIsReturningTheAddressModelCorrectly() throws Exception {
        mockMvc.perform(get("/addresses/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":2,\"street_name\":\"Street 2\",\"street_number\":2,\"apartment_building\":\"6B\",\"floor\":1," +
                        "\"_links\":{\"self\":{\"href\":\"http://localhost/addresses/2\"},\"addresses\":{\"href\":\"http://localhost/addresses\"}}}")));
    }

    @Test
    @Order(4)
    public void checkIfDeleteEndpointWorksFine() throws Exception {
        mockMvc.perform(delete("/addresses/delete/2")).andExpect(status().isOk())
                .andExpect(content().string(containsString("The address was deleted successfully")));
    }

    @Test
    @Order(5)
    public void checkIfPatchMappingWorksFine() throws Exception {
        mockMvc.perform(patch("/addresses/update/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content("{\"street_name\":\"Street 1\",\"street_number\":2}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("The house address: \n" +
                        "\n" +
                        "{\n" +
                        "  Street: Street 1\n" +
                        "  Nr.: 2\n" +
                        "}\n" +
                        "\n" +
                        "was saved successfully")));
    }
}
