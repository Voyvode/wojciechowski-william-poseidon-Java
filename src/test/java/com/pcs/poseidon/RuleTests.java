package com.pcs.poseidon;

import com.pcs.poseidon.domain.Rule;
import com.pcs.poseidon.repositories.RuleRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
		assertNotNull(rule.getId());
		assertEquals("Rule Name", rule.getName());

		// Update
		rule.setName("Rule Name Update");
		rule = ruleRepository.save(rule);
		assertEquals("Rule Name Update", rule.getName());

		// Find
		List<Rule> listResult = ruleRepository.findAll();
		assertFalse(listResult.isEmpty());

		// Delete
		var id = rule.getId();
		ruleRepository.delete(rule);
		Optional<Rule> ruleList = ruleRepository.findById(id);
		assertFalse(ruleList.isPresent());
	}
}
