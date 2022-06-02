package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();
		
		while(!chessMatch.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printMatch(chessMatch, captured);
				System.out.println();
				System.out.println("Source: ");
				ChessPosition source = UI.readChassPosition(sc);
				
				boolean[][] possibleMovies = chessMatch.possibleMovies(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMovies);
				
				System.out.println();
				System.out.println("Target: ");
				ChessPosition target = UI.readChassPosition(sc);
				
				//Movendo da origem para o destino
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				
				if(capturedPiece != null) {
					captured.add(capturedPiece);
				}
				
				if (chessMatch.getPromoted() != null) {
					System.out.print("Informe a pe�a promovida (B/N/R/Q): ");
					String type = sc.nextLine().toUpperCase();
					
					while (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q") ) {
						System.out.print("Varlor invalido: Informe a pe�a promovida (B/N/R/Q): ");
						type = sc.nextLine().toUpperCase();
					}
					chessMatch.replacePromotedPiece(type);
				}
			}
			catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
		//UI.printBoard(chessMatch.getPieces());
	}

}
