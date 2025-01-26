package com.devsuperior.dslist.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dslist.dto.GameListDTO;
import com.devsuperior.dslist.entities.GameList;
import com.devsuperior.dslist.projections.GameMinProjection;
import com.devsuperior.dslist.repositories.GameListRepository;
import com.devsuperior.dslist.repositories.GameRepository;

@Service
public class GameListService {

	@Autowired
	private GameListRepository gameListRepository;

	@Autowired
	private GameRepository gameRepository;

	@Transactional(readOnly = true)
	public List<GameListDTO> findAll(){

		// retorna todos os Game's
		List<GameList> listaGameList = gameListRepository.findAll();

		// convertendo a listaGame para listaGameMinDTO para exibição resumida
		List<GameListDTO> listaGameListnDTO = listaGameList.stream().map(gameList -> new GameListDTO(gameList)).toList();

		return listaGameListnDTO;
	}

	@Transactional
	public void gameMove(Long listId, int sourceIndex, int destinationIndex) {

		List<GameMinProjection> listaGameMinProjection = gameRepository.searchByList(listId);

		GameMinProjection gameMinProjection = listaGameMinProjection.remove(sourceIndex);

		listaGameMinProjection.add(destinationIndex, gameMinProjection);

		int min = (sourceIndex < destinationIndex ? sourceIndex : destinationIndex);
		int max = (destinationIndex > sourceIndex ? destinationIndex : sourceIndex);

		for (int i = min; i <= max; i++) {

			Long id = listaGameMinProjection.get(i).getId();

			gameListRepository.updateBelongingPosition(listId, id, i);
		}
	}
}