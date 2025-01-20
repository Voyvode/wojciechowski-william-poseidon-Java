package com.pcs.poseidon;

import com.pcs.poseidon.domain.User;
import com.pcs.poseidon.repositories.UserRepository;
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
public class UserTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void setup() {
		userRepository.deleteAll();
	}

	@Test
	public void testGetUserList() throws Exception {
		mockMvc.perform(get("/admin/users/list")
						.with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("User List")));
	}

	@Test
	public void testGetAddUserForm() throws Exception {
		mockMvc.perform(get("/admin/users/add")
						.with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk())
				.andExpect(view().name("admin/users/add"));
	}

	@Test
	public void testCreateUser() throws Exception {
		mockMvc.perform(post("/admin/users/validate")
						.with(user("admin").roles("ADMIN"))
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("username", "newuser")
						.param("password", "Pa$$word123")
						.param("fullname", "New User")
						.param("role", "USER"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/admin/users/list"));

		List<User> users = userRepository.findAll();
		assertEquals(1, users.size(), "User wasn't saved in database");
		assertEquals("newuser", users.get(0).getUsername(), "Username wasn't saved correctly");
		assertEquals("New User", users.get(0).getFullname(), "Fullname wasn't saved correctly");
		assertEquals("USER", users.get(0).getRole(), "Role wasn't saved correctly");
	}

	@Test
	public void testGetUpdateUserForm() throws Exception {
		var user = new User();
		user.setUsername("existinguser");
		user.setPassword("Pa$$word123");
		user.setFullname("Existing User");
		user.setRole("ADMIN");
		var savedUser = userRepository.save(user);

		mockMvc.perform(get("/admin/users/update/" + savedUser.getId())
						.with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk())
				.andExpect(view().name("admin/users/update"))
				.andExpect(content().string(containsString("Update User")));
	}

	@Test
	public void testUpdateUser() throws Exception {
		var user = new User();
		user.setUsername("olduser");
		user.setPassword("0ldPa$$word");
		user.setFullname("Old User");
		user.setRole("USER");
		var savedUser = userRepository.save(user);

		mockMvc.perform(post("/admin/users/update/" + savedUser.getId())
						.with(user("admin").roles("ADMIN"))
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("id", savedUser.getId().toString())
						.param("username", "updateduser")
						.param("password", "N3wPa$$word")
						.param("fullname", "Updated User")
						.param("role", "ADMIN"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/admin/users/list"));

		var updatedUser = userRepository.findById(savedUser.getId()).orElseThrow();
		assertEquals("updateduser", updatedUser.getUsername(), "Username wasn't updated");
		assertEquals("Updated User", updatedUser.getFullname(), "Fullname wasn't updated");
		assertEquals("ADMIN", updatedUser.getRole(), "Role wasn't updated");
	}

	@Test
	public void testDeleteUser() throws Exception {
		var user = new User();
		user.setUsername("usertodelete");
		user.setPassword("Pa$$word123");
		user.setFullname("User To Delete");
		user.setRole("USER");
		var savedUser = userRepository.save(user);

		mockMvc.perform(get("/admin/users/delete/" + savedUser.getId())
						.with(user("admin").roles("ADMIN")))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/admin/users/list"));

		assertTrue(userRepository.findById(savedUser.getId()).isEmpty(), "User wasn't deleted");
	}

}