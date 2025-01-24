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

import com.pcs.poseidon.domain.Rule;
import com.pcs.poseidon.repositories.RuleRepository;

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
public class RuleTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RuleRepository ruleRepository;

	@BeforeEach
	void setup() {
		ruleRepository.deleteAll();
	}

	@Test
	public void testGetRuleList() throws Exception {
		mockMvc.perform(get("/rules/list")
						.with(user("user").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Rule List")));
	}

	@Test
	public void testGetAddRuleForm() throws Exception {
		mockMvc.perform(get("/rules/add")
						.with(user("user").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(view().name("rules/add"));
	}

	@Test
	public void testCreateRule() throws Exception {
		mockMvc.perform(post("/rules/validate")
						.with(user("user").roles("USER"))
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("name", "New Rule")
						.param("description", "Description of the rule")
						.param("json", "Some JSON")
						.param("template", "Sample Template")
						.param("sqlStr", "SQL Query")
						.param("sqlPart", "SQL Part"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/rules/list"));

		List<Rule> rules = ruleRepository.findAll();
		assertEquals(1, rules.size(), "Rule wasn't saved in database");
		assertEquals("New Rule", rules.get(0).getName(), "Rule name wasn't saved correctly");
	}

	@Test
	public void testGetUpdateRuleForm() throws Exception {
		var rule = new Rule();
		rule.setName("Existing Rule");
		rule.setDescription("Existing Description");
		rule.setJson("Existing JSON");
		rule.setTemplate("Old Template");
		rule.setSqlStr("Old SQL Query");
		rule.setSqlPart("Old SQL Part");
		var savedRule = ruleRepository.save(rule);

		mockMvc.perform(get("/rules/update/" + savedRule.getId())
						.with(user("user").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(view().name("rules/update"))
				.andExpect(content().string(containsString("Update Rule")));
	}

	@Test
	public void testUpdateRule() throws Exception {
		var rule = new Rule();
		rule.setName("Old Rule");
		rule.setDescription("Old Description");
		rule.setJson("Old JSON");
		rule.setTemplate("Old Template");
		rule.setSqlStr("Old SQL Query");
		rule.setSqlPart("Old SQL Part");
		var savedRule = ruleRepository.save(rule);

		mockMvc.perform(post("/rules/update/" + savedRule.getId())
						.with(user("user").roles("USER"))
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("id", savedRule.getId().toString())
						.param("name", "Updated Rule")
						.param("description", "Updated Description")
						.param("json", "Updated JSON")
						.param("template", "Updated Template")
						.param("sqlStr", "Updated SQL Query")
						.param("sqlPart", "Updated SQL Part"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/rules/list"));

		var updatedRule = ruleRepository.findById(savedRule.getId()).orElseThrow();
		assertEquals("Updated Rule", updatedRule.getName(), "Rule name wasn't updated");
		assertEquals("Updated Description", updatedRule.getDescription(), "Description wasn't updated");
		assertEquals("Updated JSON", updatedRule.getJson(), "JSON wasn't updated");
		assertEquals("Updated Template", updatedRule.getTemplate(), "Template wasn't updated");
		assertEquals("Updated SQL Query", updatedRule.getSqlStr(), "SQL Query wasn't updated");
		assertEquals("Updated SQL Part", updatedRule.getSqlPart(), "SQL Part wasn't updated");
	}

	@Test
	public void testDeleteRule() throws Exception {
		var rule = new Rule();
		rule.setName("Rule to Delete");
		rule.setDescription("Description to Delete");
		rule.setJson("Delete JSON");
		rule.setTemplate("Delete Template");
		rule.setSqlStr("Delete SQL");
		rule.setSqlPart("Delete SQL Part");
		var savedRule = ruleRepository.save(rule);

		mockMvc.perform(get("/rules/delete/" + savedRule.getId())
						.with(user("user").roles("USER")))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/rules/list"));

		assertTrue(ruleRepository.findById(savedRule.getId()).isEmpty(), "Rule wasn't deleted");
	}

}