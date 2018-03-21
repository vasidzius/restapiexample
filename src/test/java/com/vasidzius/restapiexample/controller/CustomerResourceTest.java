package com.vasidzius.restapiexample.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vasidzius.restapiexample.RestapiexampleApplication;
import com.vasidzius.restapiexample.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = RestapiexampleApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class CustomerResourceTest {

    @Autowired
    private MockMvc mockMvc;

    private Gson gson = new Gson();

    @Test
    public void allSuccess() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/customers").header("Authorization", "Bearer admin");
        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<Customer> customers = gson.fromJson(contentAsString, new TypeToken<List<Customer>>() {
        }.getType());
        assertEquals(4, customers.size());
    }

    @Test
    public void allFailed() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/customers").header("Authorization", "Bearer 1");
        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andExpect(status().isForbidden()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertEquals("You aren't admin", contentAsString);
    }

    @Test
    public void oneSuccess() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/customers/1").header("Authorization", "Bearer admin");
        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Customer customer = gson.fromJson(contentAsString, Customer.class);
        assertEquals(3, customer.getPartnerMappings().size());
    }

    @Test
    public void oneFailed() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/customers/1").header("Authorization", "Bearer 2");
        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andExpect(status().isForbidden()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertEquals("You aren't admin, only your account information is accessible", contentAsString);
    }

    @Test
    public void returnMe() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/customers/me").header("Authorization", "Bearer 1");
        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Customer customer = gson.fromJson(contentAsString, Customer.class);
        assertEquals(Long.valueOf(1), customer.getId());
    }


    @Test
    public void retrieveUnexistId() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/customers/100").header("Authorization", "Bearer admin");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isNotFound()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertEquals("Can't find customer with id 100", contentAsString);
    }
}