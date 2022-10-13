package aima.core.search.adversarial;

import aima.core.search.framework.Metrics;

public class AlfaBetaArbolJuego {

	private ArboldeJuego<Double> tree;
	static private int expandedNodes = 0;

	/** Creates a new search object for a given game. */
	public AlfaBetaArbolJuego(ArboldeJuego<Double> _tree) {
		this.tree = _tree;
	}

	public double makeDecision(double alpha, double beta) {
		if (this.tree.esTerminal()) return this.tree.getValor();
		return this.tree.esMax() ? this.maxValue(alpha, beta) : minValue(alpha, beta);
	}

	public double maxValue(double alpha, double beta) {
		double maxVal = Double.NEGATIVE_INFINITY;
		for (ArboldeJuego<Double> hijo : this.tree.getHijos()) {
			expandedNodes++;
			AlfaBetaArbolJuego treeHijo = new AlfaBetaArbolJuego(hijo);
			double eval = treeHijo.makeDecision(alpha, beta);
			hijo.setVisitado();
			maxVal = Math.max(eval, maxVal);
			this.tree.setValor(maxVal);
			alpha = Math.max(alpha, eval);
			if (beta <= alpha) break;
		}
		return maxVal;
	}

	public double minValue(double alpha, double beta) {
		double minVal = Double.POSITIVE_INFINITY;
		for (ArboldeJuego<Double> hijo : this.tree.getHijos()) {
			expandedNodes++;
			AlfaBetaArbolJuego treeHijo = new AlfaBetaArbolJuego(hijo);
			double eval = treeHijo.makeDecision(alpha, beta);
			hijo.setVisitado();
			minVal = Math.min(eval, minVal);
			this.tree.setValor(minVal);
			beta = Math.min(beta, eval);
			if (beta <= alpha) break;
		}
		return minVal;
	}
	
	public void printTree(double y) {
		System.out.format("Valor con MINIMAX: %.2f\n", y);
		this.tree.printArbolExplorado();
		System.out.format("Nodos visitados: %d\n", this.getExpandedNodes());
	}

	public int getExpandedNodes() {
		return expandedNodes;
	}
	
	public Metrics getMetrics() {
		Metrics result = new Metrics();
		result.set("expandedNodes", expandedNodes);
		return result;
	}
}
