package com.projeto.tracker.controller;

import org.springframework.web.bind.annotation.*;

import com.projeto.tracker.model.Projeto;
import com.projeto.tracker.repository.ProjetoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projeto")
@CrossOrigin("http://localhost:4200")
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

	@GetMapping("/listarPorId:{id}")
	public Projeto buscaPorId(@PathVariable("id") Long id) {
		return this.projetoRepository.getById(id);
	}
}
