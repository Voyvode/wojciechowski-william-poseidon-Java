package com.pcs.poseidon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pcs.poseidon.domain.CurvePoint;
import org.springframework.stereotype.Repository;

@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Long> {

}
