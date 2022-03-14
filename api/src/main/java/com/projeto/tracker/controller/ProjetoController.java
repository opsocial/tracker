package com.projeto.tracker.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.tracker.model.Projeto;
import com.projeto.tracker.repository.ProjetoRepository;

@RestController
@RequestMapping("/projeto")
public class ProjetoController {

	private final ProjetoRepository projetoRepository;
	
	public ProjetoController(ProjetoRepository projetoRepository) {
		this.projetoRepository = projetoRepository;
	}
	
	@PostMapping("/salvar")
	public Projeto save(@RequestBody Projeto projeto) {
		return this.projetoRepository.save(projeto);
	}
	
}
