package com.pcs.poseidon;

import com.pcs.poseidon.domain.Bid;
import com.pcs.poseidon.repositories.BidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class BidTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BidRepository bidRepository;

	@BeforeEach
	void setup() {
		bidRepository.deleteAll();
	}

	@Test
	public void testGetBidList() throws Exception {
		mockMvc.perform(get("/bids/list")
						.with(user("user").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Bid List")));
	}

	@Test
	public void testGetAddBidForm() throws Exception {
		mockMvc.perform(get("/bids/add")
						.with(user("user").roles("USER")))
				.andExpect(status().isOk()) //
				.andExpect(view().name("bids/add"));
	}

	@Test
	public void testCreateBid() throws Exception {
		mockMvc.perform(post("/bids/validate")
						.with(user("user").roles("USER"))
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("type", "New Type")
						.param("account", "New Account")
						.param("bidQuantity", "20"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/bids/list"));

		List<Bid> bids = bidRepository.findAll();
		assertEquals(1, bids.size(), "Bid wasn't saved in database");
		assertEquals("New Type", bids.get(0).getType(), "Wrong bid type");
	}

	@Test
	public void testGetUpdateBidForm() throws Exception {
		var bid = new Bid();
		bid.setType("Type to Update");
		bid.setAccount("Account to Update");
		bid.setBidQuantity(30d);
		var savedBid = bidRepository.save(bid);

		// Requête GET pour accéder au formulaire de mise à jour
		mockMvc.perform(get("/bids/update/" + savedBid.getId())
						.with(user("user").roles("USER")))
				.andExpect(status().isOk()) // Statut HTTP 200
				.andExpect(view().name("bids/update"))
				.andExpect(content().string(containsString("Update Bid")));
	}

	@Test
	public void testUpdateBid() throws Exception {
		var bid = new Bid();
		bid.setType("Old Type");
		bid.setAccount("Old Account");
		bid.setBidQuantity(50d);
		var savedBid = bidRepository.save(bid);

		mockMvc.perform(post("/bids/update/" + savedBid.getId())
							.with(user("user").roles("USER"))
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("id", savedBid.getId().toString())
						.param("type", "Updated Type")
						.param("account", "Updated Account")
						.param("bidQuantity", "100"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/bids/list"));

		var updatedBid = bidRepository.findById(savedBid.getId()).orElseThrow();
		assertEquals("Updated Type", updatedBid.getType(), "Type wasn't updated");
		assertEquals("Updated Account", updatedBid.getAccount(), "Account wasn't updated");
		assertEquals(100, (double) updatedBid.getBidQuantity(), "Bid Quantity wasn't updated");
	}

	@Test
	public void testDeleteBid() throws Exception {
		var bid = new Bid();
		bid.setType("Type to Delete");
		bid.setAccount("Account to Delete");
		bid.setBidQuantity(70d);
		var savedBid = bidRepository.save(bid);

		mockMvc.perform(get("/bids/delete/" + savedBid.getId())
					.with(user("user").roles("USER")))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/bids/list"));

		// Vérifie que le Bid a bien été supprimé
		assertTrue(bidRepository.findById(savedBid.getId()).isEmpty(), "Bid wasn't deleted");
	}

}