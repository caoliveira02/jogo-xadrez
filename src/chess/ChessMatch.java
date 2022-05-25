package chess;

import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer(){
		return currentPlayer;
	}
	
	public ChessPiece[][] getPieces(){
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++ ) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;	
	}
	
	public boolean[][] possibleMovies(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMovies();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);  //Valida se a posi��o existe
		validateTargetPosition(source, target); //Valida posi��o de destino
		Piece capturePiece = makeMove(source, target); //Recupera o movimento da pe�a
		nextTurn();
		return(ChessPiece)capturePiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.romovePiece(source); //Remove a pe�a da posi��o de origem
		Piece capturePiece = board.romovePiece(target); //Remove a pe�a que existe na posi��o de destino se tiver pe�a na posi��o
		board.placePiece(p, target); //Coloca a pe�a na posi��o de destino
		
		if (capturePiece != null) {
			piecesOnTheBoard.remove(capturePiece);
			capturedPieces.add(capturePiece);
		}
		
		return capturePiece;
	}
	
	private void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) {
			throw new ChessException("N�o existe pe�a na posi��o de origem.");
		}
		if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessException("A pe�a escolhida n�o � sua.");
		}
		if(!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("N�o existe movimento possiveis para pe�a escolhida.");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if(!board.piece(source).possibleMove(target)) {
			throw new ChessException("A pe�a escolhida n�o pode se mover para posi��o de destino.");
		}
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	public void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	
	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
		placeNewPiece('c', 2, new Rook(board, Color.WHITE));
		placeNewPiece('d', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1, new Rook(board, Color.WHITE));
		placeNewPiece('d', 1, new King(board, Color.WHITE));
		
		placeNewPiece('c', 7, new Rook(board, Color.BLACK));
		placeNewPiece('c', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
