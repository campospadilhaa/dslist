package com.devsuperior.dslist.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dslist.dto.GameListDTO;
import com.devsuperior.dslist.dto.GameMinDTO;
import com.devsuperior.dslist.dto.ReplacementDTO;
import com.devsuperior.dslist.services.GameListService;
import com.devsuperior.dslist.services.GameService;

@RestController
@RequestMapping(value = "/lists")
public class GameListController {

	@Autowired
	private GameService gameService;

	@Autowired
	private GameListService gameListService;

	@GetMapping
	public List<GameListDTO> findAll(){

		List<GameListDTO> listaGameListDTO = gameListService.findAll();

		return listaGameListDTO;
	}

	@GetMapping(value = "/{listId}/games")
	public List<GameMinDTO> findByList(@PathVariable Long listId){

		List<GameMinDTO> listaGameMinDTO = gameService.findByList(listId);

		return listaGameMinDTO;
	}

	@PostMapping(value = "/{listId}/replacement")
	public void gameMove(@PathVariable Long listId, @RequestBody ReplacementDTO body){

		Integer sourceIndex = body.getSourceIndex();
		Integer destinationIndex = body.getDestinationIndex();

		gameListService.gameMove(listId, sourceIndex, destinationIndex);
	}
}