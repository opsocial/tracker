package com.projeto.tracker.controller;

import org.springframework.web.bind.annotation.*;

import com.projeto.tracker.model.Projeto;
import com.projeto.tracker.repository.ProjetoRepository;

import java.util.List;

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

	@GetMapping("/listar")
	public List<Projeto> listAll() {return this.projetoRepository.findAll();}
}
