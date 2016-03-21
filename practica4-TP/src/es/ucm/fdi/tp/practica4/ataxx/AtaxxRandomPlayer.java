package es.ucm.fdi.tp.practica4.ataxx;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class AtaxxRandomPlayer extends Player {
	/**
	 * Numero de serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		int opciones, opcion;
		List<GameMove> movimientoValido = new ArrayList<GameMove>();		
		if(board.isFull()){
			throw new GameError("El tablero esta lleno, no se puede mover mas fichas");
		}
		else{
			movimientoValido = rules.validMoves(board, pieces, p);
			opciones = movimientoValido.size();
			if(opciones <= 0){
				throw new GameError("Jugador " + p.getId() + " no tiene más fichas para mover");				
			}			
		}
		opcion = Utils.randomInt(opciones);
		return movimientoValido.get(opcion);
	}
}
