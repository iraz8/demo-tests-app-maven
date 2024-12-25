package com.razvan.demotestsapp.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CalculatorController.class)
public class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private Model model;

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void testCalculate() throws Exception {
        mockMvc.perform(post("/calculate")
                        .param("number1", "5")
                        .param("number2", "3"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("result", 8));
    }

    @Test
    public void testCalculateWithNegativeNumbers() throws Exception {
        mockMvc.perform(post("/calculate")
                        .param("number1", "-5")
                        .param("number2", "-3"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("result", -8));
    }

    @Test
    public void testCalculateWithZero() throws Exception {
        mockMvc.perform(post("/calculate")
                        .param("number1", "0")
                        .param("number2", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("result", 0));
    }

    @Test
    public void testCalculateWithLargeNumbers() throws Exception {
        mockMvc.perform(post("/calculate")
                        .param("number1", "1000000")
                        .param("number2", "2000000"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("result", 3000000));
    }
}