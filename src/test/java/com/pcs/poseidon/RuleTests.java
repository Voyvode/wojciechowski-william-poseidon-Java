package com.pcs.poseidon;

import com.pcs.poseidon.domain.Rule;
import com.pcs.poseidon.repositories.RuleRepository;
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
public class RuleTests {

	@Autowired
	private RuleRepository ruleRepository;

	@Test
	public void ruleTest() {
		var rule = new Rule();
		rule.setName( "Rule Name");
		rule.setDescription("Description");
		rule.setJson("JSON");
		rule.setTemplate("Template");
		rule.setSqlStr("SQL");
		rule.setSqlPart("SQL Part");

		// Save
		rule = ruleRepository.save(rule);
		Assert.assertNotNull(rule.getId());
		Assert.assertTrue(rule.getName().equals("Rule Name"));

		// Update
		rule.setName("Rule Name Update");
		rule = ruleRepository.save(rule);
		Assert.assertTrue(rule.getName().equals("Rule Name Update"));

		// Find
		List<Rule> listResult = ruleRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		var id = rule.getId();
		ruleRepository.delete(rule);
		Optional<Rule> ruleList = ruleRepository.findById(id);
		Assert.assertFalse(ruleList.isPresent());
	}
}
