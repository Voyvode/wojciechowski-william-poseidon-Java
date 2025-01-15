package com.pcs.poseidon;

import com.pcs.poseidon.domain.CurvePoint;
import com.pcs.poseidon.repositories.CurvePointRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CurvePointTests {

	@Autowired
	private CurvePointRepository curvePointRepository;

	@Test
	public void curvePointTest() {
		var curvePoint = new CurvePoint();
		curvePoint.setCurveId(10L);
		curvePoint.setTerm(10d);
		curvePoint.setTerm(30d);

		// Save
		curvePoint = curvePointRepository.save(curvePoint);
		assertNotNull(curvePoint.getId());
		assertEquals(10L, (long) curvePoint.getCurveId());

		// Update
		curvePoint.setCurveId(20L);
		curvePoint = curvePointRepository.save(curvePoint);
		assertEquals(20L, (long) curvePoint.getCurveId());

		// Find
		List<CurvePoint> listResult = curvePointRepository.findAll();
		assertFalse(listResult.isEmpty());

		// Delete
		var id = curvePoint.getId();
		curvePointRepository.delete(curvePoint);
		Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
		assertFalse(curvePointList.isPresent());
	}

}
