package com.pcs.poseidon;

import com.pcs.poseidon.domain.Bid;
import com.pcs.poseidon.repositories.BidRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BidTests {

	@Autowired
	private BidRepository bidRepository;

	@Test
	public void bidListTest() {
		var bid = new Bid();
		bid.setType("Type Test");
		bid.setAccount("Account Test");
		bid.setBidQuantity(10d);

		// Save
		bid = bidRepository.save(bid);
		assertNotNull(bid.getId());
		assertEquals(10d, bid.getBidQuantity(), 10d);

		// Update
		bid.setBidQuantity(20d);
		bid = bidRepository.save(bid);
		assertEquals(20d, bid.getBidQuantity(), 20d);

		// Find
		List<Bid> listResult = bidRepository.findAll();
		assertFalse(listResult.isEmpty());

		// Delete
		var id = bid.getId();
		bidRepository.delete(bid);
		Optional<Bid> bidList = bidRepository.findById(id);
		assertFalse(bidList.isPresent());
	}
}
