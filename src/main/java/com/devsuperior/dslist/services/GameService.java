package com.devsuperior.dslist.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dslist.dto.GameDTO;
import com.devsuperior.dslist.dto.GameMinDTO;
import com.devsuperior.dslist.entities.Game;
import com.devsuperior.dslist.projections.GameMinProjection;
import com.devsuperior.dslist.repositories.GameRepository;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;

	@Transactional(readOnly = true)
	public List<GameMinDTO> findAll(){

		// retorna todos os Game's
		List<Game> listaGame = gameRepository.findAll();

		// convertendo a listaGame para listaGameMinDTO para exibição resumida
		List<GameMinDTO> listaGameMinDTO = listaGame.stream().map(game -> new GameMinDTO(game)).toList();

		return listaGameMinDTO;
	}

	@Transactional(readOnly = true)
	public GameDTO loadById(Long id){

		// retorna o Game através do id informado
		Game game = gameRepository.findById(id).get();

		// convertendo a Game para GameDTO para exibição
		GameDTO gameDTO = new GameDTO(game);

		return gameDTO;
	}

	@Transactional(readOnly = true)
	public List<GameMinDTO> findByList(Long listId){

		// retorna os Game's do tipo de lista informada
		List<GameMinProjection> listaGameMinProjection = gameRepository.searchByList(listId);

		// convertendo a listaGame para listaGameMinDTO para exibição resumida
		List<GameMinDTO> listaGameMinDTO = listaGameMinProjection.stream().map(gameMinProjection -> new GameMinDTO(gameMinProjection)).toList();

		return listaGameMinDTO;
	}
}