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
		int distancia = Math.max(Math.abs(this.row - this.filaDestino), Math.abs(this.col - this.columnaDestino));
		if (board.getPosition(this.row, this.col) == null) {
			throw new GameError("position (" + this.row + "," + this.col + ") is void!");
		} 
		else if (board.getPosition(this.filaDestino, this.columnaDestino ) != null) {
			throw new GameError("position (" + this.filaDestino + "," + this.columnaDestino + ") is already occupied!");
		}
		else if(board.getPosition(this.row, this.col) != piece){
			throw new GameError("La pieza en (" + this.row + "," + this.col + ") es de otro jugador");
		}
		else if(distancia > 2){
			throw new GameError("La posicion (" + this.filaDestino + "," + this.columnaDestino + ")es mayor que 2");
		}
		else if(board.getPieceCount(piece) <= 0){
			throw new GameError("La ficha del tipo " + piece + "no es valida");
		}
		if(distancia == 1){
			board.setPosition(this.filaDestino, this.columnaDestino, piece);
			board.setPieceCount(piece, board.getPieceCount(piece)+ 1);
		}
		else if(distancia == 2){
			board.setPosition(this.filaDestino, this.columnaDestino, piece);
			board.setPosition(this.row, this.col, null);
		}
		this.convertirFichas(board, pieces, piece);
	}
		
	/**
	 * Metodo que convierte a las fichas de alrededor
	 * @param tablero se le pasa el tablero del juego
	 * @param pieces la lista de fichas
	 * @param ficha la ficha del jugador
	 */
	private void convertirFichas(Board tablero, List<Piece> pieces, Piece ficha){
		int row = tablero.getRows();
		int col = tablero.getCols();
		for(int f = Math.max(this.filaDestino - 1, 0); f <= Math.min(this.filaDestino + 1, row -1); f++){
			for(int c = Math.max(this.columnaDestino - 1, 0); c <= Math.min(this.columnaDestino + 1, col -1); c++){
				if(tablero.getPosition(f, c) != null && pieces.contains(tablero.getPosition(f, c))){
					if(ficha.getId() != tablero.getPosition(f, c).getId()){
						tablero.setPieceCount(tablero.getPosition(f, c), tablero.getPieceCount(tablero.getPosition(f, c))- 1);
						tablero.setPieceCount(ficha, tablero.getPieceCount(ficha) + 1);
						tablero.setPosition(f, c, ficha);
					}
				}
			}
		}
	}

	@Override
	public GameMove fromString(Piece p, String str) {
		String[] words = str.split(" ");
		if (words.length != 4) {
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
