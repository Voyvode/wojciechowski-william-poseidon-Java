package com.pcs.poseidon;

import com.pcs.poseidon.domain.CurvePoint;
import com.pcs.poseidon.repositories.CurvePointRepository;
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
public class CurvePointTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CurvePointRepository curvePointRepository;

	@BeforeEach
	void setup() {
		curvePointRepository.deleteAll();
	}

	@Test
	public void testGetCurvePointList() throws Exception {
		mockMvc.perform(get("/curve-points/list")
						.with(user("user").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Curve Point List")));
	}

	@Test
	public void testGetAddCurvePointForm() throws Exception {
		mockMvc.perform(get("/curve-points/add")
						.with(user("user").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(view().name("curve-points/add"));
	}

	@Test
	public void testCreateCurvePoint() throws Exception {
		mockMvc.perform(post("/curve-points/validate")
						.with(user("user").roles("USER"))
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("curveId", "1")
						.param("term", "10.0")
						.param("value", "20.0"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/curve-points/list"));

		List<CurvePoint> curvePoints = curvePointRepository.findAll();
		assertEquals(1, curvePoints.size(), "CurvePoint wasn't saved in database");
		assertEquals(1, curvePoints.get(0).getCurveId(), "Wrong curveId");
	}

	@Test
	public void testGetUpdateCurvePointForm() throws Exception {
		var curvePoint = new CurvePoint();
		curvePoint.setCurveId(1L);
		curvePoint.setTerm(15.0);
		curvePoint.setValue(30.0);
		var savedCurvePoint = curvePointRepository.save(curvePoint);

		// Requête GET pour accéder au formulaire de mise à jour
		mockMvc.perform(get("/curve-points/update/" + savedCurvePoint.getId())
						.with(user("user").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(view().name("curve-points/update"))
				.andExpect(content().string(containsString("Update CurvePoint")));
	}

	@Test
	public void testUpdateCurvePoint() throws Exception {
		var curvePoint = new CurvePoint();
		curvePoint.setCurveId(1L);
		curvePoint.setTerm(20.0);
		curvePoint.setValue(40.0);
		var savedCurvePoint = curvePointRepository.save(curvePoint);

		mockMvc.perform(post("/curve-points/update/" + savedCurvePoint.getId())
						.with(user("user").roles("USER"))
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("id", savedCurvePoint.getId().toString())
						.param("curveId", "2")
						.param("term", "25.0")
						.param("value", "50.0"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/curve-points/list"));

		var updatedCurvePoint = curvePointRepository.findById(savedCurvePoint.getId()).orElseThrow();
		assertEquals(2, updatedCurvePoint.getCurveId(), "CurveId wasn't updated");
		assertEquals(25.0, updatedCurvePoint.getTerm(), "Term wasn't updated");
		assertEquals(50.0, updatedCurvePoint.getValue(), "Value wasn't updated");
	}

	@Test
	public void testDeleteCurvePoint() throws Exception {
		var curvePoint = new CurvePoint();
		curvePoint.setCurveId(1L);
		curvePoint.setTerm(30.0);
		curvePoint.setValue(70.0);
		var savedCurvePoint = curvePointRepository.save(curvePoint);

		mockMvc.perform(get("/curve-points/delete/" + savedCurvePoint.getId())
						.with(user("user").roles("USER")))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/curve-points/list"));

		// Vérifie que le CurvePoint a bien été supprimé
		assertTrue(curvePointRepository.findById(savedCurvePoint.getId()).isEmpty(), "CurvePoint wasn't deleted");
	}

}