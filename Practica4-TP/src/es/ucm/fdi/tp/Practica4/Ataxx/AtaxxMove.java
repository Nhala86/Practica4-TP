package es.ucm.fdi.tp.Practica4.Ataxx;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class AtaxxMove extends GameMove{
	
	/**
	 * Fila en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	protected int row;

	/**
	 * Columna en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	protected int col;

	/**
	 * Fila de destino en la que se coloca la ficha una vez movida	
	 */	
	protected int filaDestino;
	
	/**
	 * Columna de destino en la que se coloca la ficha una vez movida	 
	 */
	protected int columnaDestino;
	
	/**
	 * Numero de serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructora instanciada a vacio por defecto
	 */
	public AtaxxMove(){
		
	}
	 /**
	  * Constructora a la que se le pasa los parametros de las filas, columnas y ficha
	  * @param row valor entero de la fila en la que se encuentra la ficha antes de moverse
	  * @param col valor entero de la columna en la que se encuentra la ficha antes de moverse
	  * @param filaDestino valor entero de la fila en la que se encuentra la ficha despues de ser movida
	  * @param columnaDestino valor entero de la columna en la que se encuentra la ficha despues de ser movida
	  * @param p ficha que se le pasa por parametros
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
	 * Metodo creador de movimiento que llama a la constructora de AtaxxMove
	 * @param row valor entero de la fila en la que se encuentra la ficha antes de moverse
	 * @param col valor entero de la columna en la que se encuentra la ficha antes de moverse
	 * @param filaDestino valor entero de la fila en la que se encuentra la ficha despues de ser movida
	 * @param columnaDestino valor entero de la columna en la que se encuentra la ficha despues de ser movida
	 * @param p ficha que se le pasa por parametros
	 * @return movimiento con los parametros de filas, columnas y ficha
	 */
	private GameMove createMove(int row, int col, int filaDestino, int columnaDestino, Piece p) {
		return new AtaxxMove(row, col, filaDestino, columnaDestino, p);
	}

	@Override
	public String help() {
		return "'row column', to place a piece at the corresponding position.";
	}

}
