package com.vasidzius.restapiexample.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vasidzius.restapiexample.RestapiexampleApplication;
import com.vasidzius.restapiexample.model.PartnerMapping;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = RestapiexampleApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class PartnerMappingResourceTest {

    @Autowired
    private MockMvc mockMvc;
    private Gson gson = new Gson();

    @Test
    public void successRetrieveAllPartnerMappings() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/customers/1/partnerMappings").header("Authorization", "Bearer admin");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<PartnerMapping> partnerMappings = gson.fromJson(contentAsString, new TypeToken<List<PartnerMapping>>() {
        }.getType());
        assertEquals(3, partnerMappings.size());
    }

    @Test
    public void failedRetrieveAllPartnerMappings() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/customers/1/partnerMappings").header("Authorization", "Bearer 2");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isForbidden()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertEquals("You aren't admin, only your account information is accessible", contentAsString);
    }

    @Test
    public void successRetrievePartnerMappings() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/customers/1/partnerMappings/100").header("Authorization", "Bearer admin");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        PartnerMapping partnerMapping = gson.fromJson(contentAsString, PartnerMapping.class);
        assertEquals(1, partnerMapping.getPartnerId());
        assertEquals(1, partnerMapping.getAccountId());
    }

    @Test
    public void accessFailedRetrievePartnerMappings() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/customers/1/partnerMappings/100").header("Authorization", "Bearer 2");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isForbidden()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertEquals("You aren't admin, only your account information is accessible", contentAsString);
    }

    @Test
    public void failedRetrievePartnerMappings() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/customers/1/partnerMappings/200").header("Authorization", "Bearer admin");
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
    }

    @Test
    public void successCreate() throws Exception {
        PartnerMapping partnerMapping = new PartnerMapping();
        partnerMapping.setPartnerId(300);
        partnerMapping.setAccountId(300);
        partnerMapping.setName("Petya");
        partnerMapping.setSurname("Ivanov");
        MockHttpServletRequestBuilder requestBuilder = post("/customers/2/partnerMappings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(partnerMapping))
                .header("Authorization", "Bearer admin");
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    public void successUpdate() throws Exception {
        PartnerMapping partnerMapping = new PartnerMapping();
        partnerMapping.setPartnerId(1);
        partnerMapping.setAccountId(1);
        partnerMapping.setName("Petya");
        partnerMapping.setSurname("Petrov");
        MockHttpServletRequestBuilder requestBuilder = put("/customers/1/partnerMappings/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(partnerMapping))
                .header("Authorization", "Bearer 1");
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent()).andReturn();
    }

    @Test
    public void forbiddenUpdate() throws Exception {
        PartnerMapping partnerMapping = new PartnerMapping();
        partnerMapping.setPartnerId(1);
        partnerMapping.setAccountId(1);
        partnerMapping.setName("Petya");
        partnerMapping.setSurname("Petrov");
        MockHttpServletRequestBuilder requestBuilder = put("/customers/1/partnerMappings/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(partnerMapping))
                .header("Authorization", "Bearer 2");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isForbidden());
    }

    @Test
    public void notFoundUpdate() throws Exception {
        PartnerMapping partnerMapping = new PartnerMapping();
        partnerMapping.setPartnerId(1);
        partnerMapping.setAccountId(1);
        partnerMapping.setName("Petya");
        partnerMapping.setSurname("Petrov");
        MockHttpServletRequestBuilder requestBuilder = put("/customers/1/partnerMappings/300")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(partnerMapping))
                .header("Authorization", "Bearer 1");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }

}