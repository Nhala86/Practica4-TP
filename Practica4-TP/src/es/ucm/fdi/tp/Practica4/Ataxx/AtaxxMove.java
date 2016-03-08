package es.ucm.fdi.tp.Practica4.Ataxx;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectN.ConnectNMove;

public class AtaxxMove extends GameMove{
	
	/**
	 * The row where to place the piece return by {@link GameMove#getPiece()}.
	 * <p>
	 * Fila en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	protected int row;

	/**
	 * The column where to place the piece return by {@link GameMove#getPiece()}
	 * .
	 * <p>
	 * Columna en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	protected int col;

	/**
	 * Fila en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */	
	protected int filaDestino;
	
	/**
	 * Columna en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	protected int columnaDestino;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructora instanciada a vacio
	 */
	public AtaxxMove(){
		
	}
	 /**
	  * 
	  * @param row
	  * @param col
	  * @param filaDestino
	  * @param columnaDestino
	  * @param p
	  */
	public AtaxxMove(int row, int col, int filaDestino, int columnaDestino, Piece p){
			super(p);
			this.row = row;
			this.col = col;
			this.filaDestino = filaDestino;
			this.columnaDestino = columnaDestino;
	}
	
	@Override
	public void execute(Board board, List<Piece> pieces) {
		Piece piece = getPiece();
		if (board.getPosition(row, col) == null) {
			throw new GameError("position (" + row + "," + col + ") is void!");
		} 
		else if (board.getPosition(this.filaDestino, this.columnaDestino ) != null) {
			throw new GameError("position (" + this.filaDestino + "," + this.columnaDestino + ") is already occupied!");
		}
		else if(board.getPosition(this.row, this.col) != piece){
			throw new GameError("La pieza en (" + this.row + "," + this.col + ") es de otro jugador");
		}		
	}

	@Override
	public GameMove fromString(Piece p, String str) {
		String[] words = str.split(" ");
		if (words.length != 2) {
			return null;
		}
		try {
			int row, col, filaDestino, columnaDestino;
			row = Integer.parseInt(words[0]);
			col = Integer.parseInt(words[1]);
			filaDestino = Integer.parseInt(words[2]);
			columnaDestino = Integer.parseInt(words[3]);
			return createMove(row, col, filaDestino, columnaDestino, p);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param row2
	 * @param col2
	 * @param filaDestino
	 * @param columnaDestino
	 * @param p
	 * @return
	 */
	private GameMove createMove(int row2, int col2, int filaDestino, int columnaDestino, Piece p) {
		return new AtaxxMove(row, col, filaDestino, columnaDestino, p);
	}

	@Override
	public String help() {
		return "'row column', to place a piece at the corresponding position.";
	}

}
