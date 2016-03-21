package es.ucm.fdi.tp.practica4.ataxx;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.FiniteRectBoard;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class AtaxxRules implements GameRules{
	private int dim;
	private int obstaculo;
	private final Piece Obstaculo = new Piece("*");
	
	
	
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
		return "Bienvenido a Ataxx con un tablero " + this.dim + "X" + this.dim;
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
		if(this.obstaculo > 0){
			PonerObstaculos(tablero);
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
		Pair<State, Piece> resultado = new Pair<State, Piece>(State.InPlay, null);
		if(board.isFull()){
			resultado = resultado(board, pieces, turn);
		}
		else{
			if(this.nextPlayer(board, pieces, turn).equals(turn)){
				if(this.bloqueoResuelto(board, pieces)){
					resultado = resultado(board, pieces, turn);
				}
			}
		}
		return resultado;
	}
	
	/**
	 * Metodo que evita que la partida se bloque porque ningun jugador puede mover sus fichas
	 * @param board parametro que le pasa el tablero con su dimension
	 * @param pieces parametro de fichas de los jugadores del juego
	 * @return True si el numero de movimientos posibles es 0. False si hay movimientos posibles
	 */
	private boolean bloqueoResuelto(Board board, List<Piece> pieces) {
		boolean Ok = false;
		int contador = 0;
		for(int i = 0; i < pieces.size(); i++){
			if(this.validMoves(board, pieces, pieces.get(i)).size() <= 0){
				contador++;
			}
		}
		if(contador == pieces.size() - 1){
			Ok = true;
		}
		return Ok;
	}	

	/**
	 * Metodo que crea los obstaculos en el tablero
	 * @param tablero del juego con dimension * dimension
	 */
	private void PonerObstaculos(Board tablero){
		int cont = this.obstaculo;
		int f, c;
		while(cont > 0){
			f = Utils.randomInt(this.dim);
			c = Utils.randomInt(this.dim);
			if(tablero.getPosition(f, c) == null){
				tablero.setPosition(f, c, this.Obstaculo);
				cont--;
			}
		}		
	}
	
	@Override
	public Piece nextPlayer(Board board, List<Piece> pieces, Piece turn) {
		Piece ficha;
		int x = pieces.indexOf(turn);
		int valor, contador = 1;
		int numeroJugadores = pieces.size();
		
		do{
			ficha = pieces.get((x + contador)% numeroJugadores);
			valor = this.validMoves(board, pieces, ficha).size();
			contador++;
		}while(valor <= 0 && contador <= numeroJugadores);			
		return ficha;		
	}

	@Override
	public double evaluate(Board board, List<Piece> pieces, Piece turn) {
		return 0;
	}

	@Override
	public List<GameMove> validMoves(Board board, List<Piece> playersPieces, Piece turn) {		
		List<GameMove> movimientoValido = new ArrayList<GameMove>();
		for(int f = 0; f < board.getRows(); f++){
			for(int c = 0; c < board.getCols(); c++){
				Piece ficha = turn;
				if(board.getPosition(f, c) == ficha){
					movimientoValido.addAll(MovimientoFichaValido(board, turn, f, c));
				}
			}
		}
		return movimientoValido;
	}
	
	/**
	 * Metodo que comprueba que el movimiento de la ficha sea correcto
	 * @param tablero de dimension NxN
	 * @param ficha que se juega en ese turno
	 * @param row valor entero positivo de fila
	 * @param col valor entero positivo de columna
	 * @return un movimiento valido
	 */
	private List<GameMove> MovimientoFichaValido(Board tablero, Piece ficha, int row, int col) {
		int fila = tablero.getRows();
		int columna = tablero.getCols();
		List<GameMove> movimientoValido = new ArrayList<GameMove>();
		for(int f = Math.max(row - 2, 0); f <= Math.min(row + 2, fila - 1); f++){
			for(int c = Math.max(col - 2, 0); c <= Math.min(col + 2, columna - 1); c++){
				if(tablero.getPosition(f, c) == null){
					movimientoValido.add(new AtaxxMove(row, col, f, c, ficha));
				}
			}
		}
		return movimientoValido;
	}
	 /**
	  * Metodo que devuelve el estado del tablero cuando ya no hay más movimientos 
	  * @param tablero parametro de Board que le pasa el tablero
	  * @param pieces parametro de Pieces que le pasa una piece
	  * @param ficha parametro de Piece que le pasa una ficha
	  * @return el estado de un jugador al terminar la partida
	  */
	private Pair<State, Piece> resultado(Board tablero, List<Piece> pieces, Piece ficha){
		State juego = State.InPlay;
		Piece jugador = null;
		int[] jugadorEnJuego = new int[pieces.size()];
		int valorAlto = 0;
		
		for(int i = 0; i < pieces.size(); i++){
			jugadorEnJuego[i] = tablero.getPieceCount(pieces.get(i));
			if(valorAlto == jugadorEnJuego[i]){
				juego = State.Draw;
			}
			else if(valorAlto < jugadorEnJuego[i]){
				valorAlto = jugadorEnJuego[i];
				jugador = pieces.get(i);
				juego = State.Won;
				}
		}		
		Pair<State, Piece> resultado = new Pair <State, Piece>(juego, jugador);
		return resultado;
	}

}
