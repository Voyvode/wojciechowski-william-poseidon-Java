package com.pcs.poseidon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pcs.poseidon.domain.CurvePoint;

@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Long> {

}
