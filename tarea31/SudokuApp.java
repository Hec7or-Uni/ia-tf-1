package aima.gui.sudoku.csp;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.ImprovedBacktrackingStrategy;
import aima.core.search.csp.SolutionStrategy;

/*
 * @author Héctor Toral
 * 
 */
public class SudokuApp {
	public static void main(String[] args) {
		int res = 0;
		Sudoku [] lista = union (union(Sudoku.listaSudokus2("easy50.txt"),
				Sudoku.listaSudokus2("top95.txt")),
				Sudoku.listaSudokus2("hardest.txt"));
		
		for (int i = 0; i < lista.length; i++) {
			SudokuProblem problem = new SudokuProblem(lista[i].pack_celdasAsignadas());
			SolutionStrategy strategy = null;
			strategy = new ImprovedBacktrackingStrategy(true, true, true, true);
			
			System.out.println("---------");
			lista[i].imprimeSudoku();
			System.out.println("SUDOKU INCOMPLETO - Resolviendo");
			
			long startTime = System.currentTimeMillis();
			Assignment valores = strategy.solve(problem.copyDomains());
			long endTime = System.currentTimeMillis();
			System.out.format("Time to solve = %.2fsegundos\n", (double)(endTime-startTime));
			
			System.out.println(valores);
			System.out.println("SOLUCION:");
			
			Sudoku solucion = new Sudoku(valores);
			solucion.imprimeSudoku();
			
			System.out.println("Sudoku solucionado correctamente");
			res++;
		}
		System.out.println("+++++++++");
		System.out.format("Numero sudokus solucionados:%d\n", res);
	}

	private static Sudoku[] union(Sudoku[] listaSudokusV1, Sudoku[] listaSudokusV2) {
		Sudoku [] lista = new Sudoku[listaSudokusV1.length + listaSudokusV2.length];
		System.arraycopy(listaSudokusV1, 0, lista, 0, listaSudokusV1.length);
		System.arraycopy(listaSudokusV2, 0, lista, listaSudokusV1.length, listaSudokusV2.length);
		return lista;
	}
}