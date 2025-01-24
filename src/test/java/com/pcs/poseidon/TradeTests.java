package com.pcs.poseidon;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.pcs.poseidon.domain.Trade;
import com.pcs.poseidon.repositories.TradeRepository;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class TradeTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TradeRepository tradeRepository;

	@BeforeEach
	void setup() {
		tradeRepository.deleteAll();
	}

	@Test
	public void testGetTradeList() throws Exception {
		mockMvc.perform(get("/trades/list")
						.with(user("user").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Trade List")));
	}

	@Test
	public void testGetAddTradeForm() throws Exception {
		mockMvc.perform(get("/trades/add")
						.with(user("user").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(view().name("trades/add"));
	}

	@Test
	public void testCreateTrade() throws Exception {
		mockMvc.perform(post("/trades/validate")
						.with(user("user").roles("USER"))
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("account", "New Account")
						.param("type", "New Type")
						.param("buyQuantity", "100.5"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/trades/list"));

		List<Trade> trades = tradeRepository.findAll();
		assertEquals(1, trades.size(), "Trade wasn't saved in database");
		assertEquals("New Account", trades.get(0).getAccount(), "Trade account wasn't saved correctly");
		assertEquals("New Type", trades.get(0).getType(), "Trade type wasn't saved correctly");
	}

	@Test
	public void testGetUpdateTradeForm() throws Exception {
		var trade = new Trade();
		trade.setAccount("Existing Account");
		trade.setType("Existing Type");
		trade.setBuyQuantity(50.0);
		var savedTrade = tradeRepository.save(trade);

		mockMvc.perform(get("/trades/update/" + savedTrade.getId())
						.with(user("user").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(view().name("trades/update"))
				.andExpect(content().string(containsString("Update Trade")));
	}

	@Test
	public void testUpdateTrade() throws Exception {
		var trade = new Trade();
		trade.setAccount("Old Account");
		trade.setType("Old Type");
		trade.setBuyQuantity(200.75);
		var savedTrade = tradeRepository.save(trade);

		mockMvc.perform(post("/trades/update/" + savedTrade.getId())
						.with(user("user").roles("USER"))
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("id", savedTrade.getId().toString())
						.param("account", "Updated Account")
						.param("type", "Updated Type")
						.param("buyQuantity", "300.25"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/trades/list"));

		var updatedTrade = tradeRepository.findById(savedTrade.getId()).orElseThrow();
		assertEquals("Updated Account", updatedTrade.getAccount(), "Trade account wasn't updated");
		assertEquals("Updated Type", updatedTrade.getType(), "Trade type wasn't updated");
		assertEquals(300.25, updatedTrade.getBuyQuantity(), "Trade buyQuantity wasn't updated");
	}

	@Test
	public void testDeleteTrade() throws Exception {
		var trade = new Trade();
		trade.setAccount("Account to Delete");
		trade.setType("Type to Delete");
		trade.setBuyQuantity(400.0);
		var savedTrade = tradeRepository.save(trade);

		mockMvc.perform(get("/trades/delete/" + savedTrade.getId())
						.with(user("user").roles("USER")))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/trades/list"));

		assertTrue(tradeRepository.findById(savedTrade.getId()).isEmpty(), "Trade wasn't deleted");
	}

}