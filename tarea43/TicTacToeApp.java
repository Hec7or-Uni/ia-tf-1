package aima.core.environment.tictactoe;

import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.adversarial.AlphaBetaSearch;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import aima.core.search.adversarial.MinimaxSearch;
import aima.core.util.datastructure.XYLocation;

import java.util.List;
import java.util.Scanner;

public class TicTacToeApp {

    public static void main(String[] args) {
    	Boolean modo = false;			// true: maquina vs jugador, false: maquina vs maquina
    	Boolean metrics = true;		// true: metricas activadas, false: metricas desactivadas
    	String Algoritmo = "ALFA-BETA";	// "MINI MAX" | "ALFA-BETA" -> Solo afecta al maquina vs maquina
    	TicTacToeGame game = new TicTacToeGame();
    	
    	// Elección del algoritmo de busqueda
    	AdversarialSearch<TicTacToeState, XYLocation> search = null;
    	if (Algoritmo == "MINI MAX") search = MinimaxSearch.createFor(game);
    	else search = AlphaBetaSearch.createFor(game);
    	
    	// Inicio del juego
    	if (modo) JuegaContraUsuario(search, game);
    	else JuegaSoloMaquina(search, game, metrics, Algoritmo);
    }
    
    public static void JuegaSoloMaquina(
    		 AdversarialSearch<TicTacToeState, XYLocation> search,
    		 TicTacToeGame game,
    		 boolean metrics,
    		 String Algoritmo) {
    	System.out.format("%s con TIC TAC TOE Jugando solo maquina\n\n", Algoritmo);
    	TicTacToeState estado = game.getInitialState();
    	while (!game.isTerminal(estado)) {
    		XYLocation accion = search.makeDecision(estado);
    		
    		System.out.println("Juega " + game.getPlayer(estado));
    		if (metrics) System.out.println("Metrics : " + search.getMetrics());

            estado = game.getResult(estado, accion);
    	}
    	System.out.println("GAME OVER: ");
    	System.out.println(estado);
    	
		if (estado.getUtility() == 1) System.out.println("¡Gana el humano!");
        else if (estado.getUtility() == 0) System.out.println("¡Gana el computador!");
        else System.out.println("¡Hubo un empate!");
    }

    public static void JuegaContraUsuario(
    		 AdversarialSearch<TicTacToeState, XYLocation> search,
    		 TicTacToeGame game) {
    	Scanner teclado = new Scanner(System.in); 
    	System.out.format("MINI MAX DEMO con TIC TAC TOE Jugando solo humano\n\n");
    	TicTacToeState estado = game.getInitialState();
    	while(!game.isTerminal(estado)) {
    		System.out.println("======================");
            System.out.println(estado);
            XYLocation accion = null;
            if (game.getPlayer(estado) == "X") {
            	List<XYLocation> acciones = game.getActions(estado);
                while (!acciones.contains(accion)) {
                    System.out.println("Jugador Humano: ¿Cuál es tu acción?");
                    System.out.println("Fila(0-2):");
                    int x = teclado.nextInt();
                    System.out.println("Columna(0-2):");
                    int y = teclado.nextInt();
                    accion = new XYLocation(y, x);
                }
                
            } else {
            	accion = search.makeDecision(estado);
            	System.out.println("La máquina Juega, y elige:");
            	System.out.println("Acción elegida:" + accion);
            }
            
            estado = game.getResult(estado, accion);
    	}
    	teclado.close();
    	System.out.println("GAME OVER: ");
    	System.out.println(estado);
    	
		if (estado.getUtility() == 1) System.out.println("¡Gana el humano!");
        else if (estado.getUtility() == 0) System.out.println("¡Gana el computador!");
        else System.out.println("¡Hubo un empate!");
    }
}