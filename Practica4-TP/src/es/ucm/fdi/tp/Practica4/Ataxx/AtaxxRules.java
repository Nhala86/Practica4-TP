package es.ucm.fdi.tp.Practica4.Ataxx;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.FiniteRectBoard;
import es.ucm.fdi.tp.basecode.bgame.model.Game;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class AtaxxRules implements GameRules{
	private int dim;
	private int obstaculo;
	
	/**
	 * Constructora a la que se le pasa el parametro de entrada de dimension del tablero
	 * @param dim valor entero positivo de la dimension del tablero
	 */
	public AtaxxRules(int dim){
		if(dim < 5){
			throw new GameError("La dimension debe ser mayor o igual que 5" + dim);
		}
		else{
			this.dim = dim;
			this.obstaculo = 0;
		}
	}
	
	/**
	 * Constructora a la que se le pasa el parametro de entrada de dimension y obstaculos del tablero
	 * @param dim valor entero positivo de la dimension del tablero
	 * @param obstaculo valor entero de la cantidad de obstaculos que hay en el tablero
	 */
	public AtaxxRules(int dim, int obstaculo){
		if(dim < 5){
			throw new GameError("La dimension debe ser mayor o igual que 5" + dim);
		}
		else{
			if(obstaculo > (dim * dim)- 8){
				throw new GameError("Los obstaculos deben ser menor que " + dim * dim);
			}
			else{
				this.dim = dim;
				this.obstaculo = obstaculo;
			}
		}
	}

	
	@Override
	public String gameDesc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Board createBoard(List<Piece> pieces) {
		Board tablero = new FiniteRectBoard(dim, dim);
		
		Piece p1 = pieces.get(0);
		tablero.setPosition(0, 0, p1);
		tablero.setPosition(this.dim-1, this.dim-1, p1);
		tablero.setPieceCount(p1, 2);
		
		Piece p2 = pieces.get(1);
		tablero.setPosition(0, this.dim-1, p2);
		tablero.setPosition(this.dim-1, 0, p2);
		tablero.setPieceCount(p2, 2);
		
		if(pieces.size()> 2){
			Piece p3 = pieces.get(2);
			tablero.setPosition(0, this.dim/2, p3);
			tablero.setPosition(this.dim-1, this.dim/2, p3);
			tablero.setPieceCount(p3, 2);
			if(pieces.size()> 3){
				Piece p4 = pieces.get(3);
				tablero.setPosition(this.dim/2, 0, p4);
				tablero.setPosition(this.dim/2, this.dim-1, p4);
				tablero.setPieceCount(p4, 2);
			}
		}
		return tablero;
	}

	@Override
	public Piece initialPlayer(Board board, List<Piece> pieces) {	
		return pieces.get(0);
	}

	@Override
	public int minPlayers() {
		return 2;
	}

	@Override
	public int maxPlayers() {
		return 4;
	}

	@Override
	public Pair<State, Piece> updateState(Board board, List<Piece> pieces, Piece turn) {
		Piece jugador = null;
		State juego = State.InPlay;
		
		if(board.isFull()){
			
		}
		
		
		return new Pair<State, Piece>(juego, jugador);
	}
	
	@Override
	public Piece nextPlayer(Board board, List<Piece> pieces, Piece turn) {
		List<Piece> piecesAux = pieces;
		int i = piecesAux.indexOf(turn);
		return piecesAux.get((i+1)% piecesAux.size());
	}

	@Override
	public double evaluate(Board board, List<Piece> pieces, Piece turn) {
		return 0;
	}

	@Override
	public List<GameMove> validMoves(Board board, List<Piece> playersPieces, Piece turn) {
		return null;
	}

}
