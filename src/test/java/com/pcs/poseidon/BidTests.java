package com.pcs.poseidon;

import com.pcs.poseidon.domain.Bid;
import com.pcs.poseidon.repositories.BidRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
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
		Assert.assertNotNull(bid.getId());
		Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);

		// Update
		bid.setBidQuantity(20d);
		bid = bidRepository.save(bid);
		Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);

		// Find
		List<Bid> listResult = bidRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		var id = bid.getId();
		bidRepository.delete(bid);
		Optional<Bid> bidList = bidRepository.findById(id);
		Assert.assertFalse(bidList.isPresent());
	}
}
