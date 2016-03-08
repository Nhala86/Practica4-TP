package es.ucm.fdi.tp.Practica4.Ataxx;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.AIAlgorithm;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class AtaxxFactory implements GameFactory {
	private int dim;
	private int obstaculo;
	
	public AtaxxFactory(){
		this.dim = 5;
		this.obstaculo = 0;
		
	}

	@Override
	public GameRules gameRules() {
		return new AtaxxRules(this.dim);
	}

	@Override
	public Player createConsolePlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player createRandomPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player createAIPlayer(AIAlgorithm alg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Piece> createDefaultPieces() {
		List<Piece> pieces = new ArrayList<Piece>();
		pieces.add(new Piece("X"));
		pieces.add(new Piece("O"));
		return pieces;
	}

	@Override
	public void createConsoleView(Observable<GameObserver> game, Controller ctrl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createSwingView(Observable<GameObserver> game, Controller ctrl, Piece viewPiece, Player randPlayer,
			Player aiPlayer) {
		// TODO Auto-generated method stub
		
	}

}
