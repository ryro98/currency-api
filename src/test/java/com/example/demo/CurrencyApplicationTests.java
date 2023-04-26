package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyApplicationTests {
	@Autowired
	private MockMvc mvc;
	@Test
	public void Test_GetAverageExchangeRate_OK() throws Exception {
		mvc.perform(get("/api/v1/avg/ISK/2023-04-20"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.2023-04-20", is(0.030842)));
	}

	@Test
	public void Test_GetAverageExchangeRate_NotFound() throws Exception {
		mvc.perform(get("/api/v1/avg/test/2023-04-20"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void Test_GetMinMaxAverageValue_OK() throws Exception {
		mvc.perform(get("/api/v1/minmax/ISK/10"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", aMapWithSize(2)))
				.andExpect(jsonPath("$.min", is(0.030592)))
				.andExpect(jsonPath("$.max", is(0.031253)));
	}

	@Test
	public void Test_GetMinMaxAverageValue_NotFound() throws Exception {
		mvc.perform(get("/api/v1/minmax/test/10"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void Test_GetMinMaxAverageValue_BadRequest_Above255() throws Exception {
		mvc.perform(get("/api/v1/minmax/ISK/256"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void Test_GetMinMaxAverageValue_BadRequest_Below1() throws Exception {
		mvc.perform(get("/api/v1/minmax/ISK/0"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void Test_GetMajorDiffBuyAskRate_OK() throws Exception {
		mvc.perform(get("/api/v1/bidask/CZK/100"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", aMapWithSize(2)))
				.andExpect(jsonPath("$.min", is(0.0038)))
				.andExpect(jsonPath("$.max", is(0.004)));
	}

	@Test
	public void Test_GetMajorDiffBuyAskRate_NotFound() throws Exception {
		mvc.perform(get("/api/v1/bidask/test/100"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void Test_GetMajorDiffBuyAskRate_BadRequest_Above255() throws Exception {
		mvc.perform(get("/api/v1/bidask/CZK/256"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void Test_GetMajorDiffBuyAskRate_BadRequest_Below1() throws Exception {
		mvc.perform(get("/api/v1/bidask/CZK/0"))
				.andExpect(status().isBadRequest());
	}
}
