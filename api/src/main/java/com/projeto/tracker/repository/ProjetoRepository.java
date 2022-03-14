package com.projeto.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.tracker.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

	
	
}
