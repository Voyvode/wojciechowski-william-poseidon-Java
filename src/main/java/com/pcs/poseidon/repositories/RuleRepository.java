package com.pcs.poseidon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pcs.poseidon.domain.Rule;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {

}
