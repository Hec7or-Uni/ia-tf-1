package aima.core.search.adversarial;

import aima.core.search.framework.Metrics;

public class MinimaxArbolJuego {

	private ArboldeJuego<Double> tree;
	static private int expandedNodes = 0;

	/** Creates a new search object for a given game. */
	public MinimaxArbolJuego(ArboldeJuego<Double> _tree) {
		this.tree = _tree;
	}

	public double makeDecision() {
		if (this.tree.esTerminal()) return this.tree.getValor();
		return this.tree.esMax() ? this.maxValue() : minValue();
	}

	public double maxValue() {
		double maxVal = Double.NEGATIVE_INFINITY;
		for (ArboldeJuego<Double> hijo : this.tree.getHijos()) {
			expandedNodes++;
			MinimaxArbolJuego treeHijo = new MinimaxArbolJuego(hijo);
			double eval = treeHijo.makeDecision();
			hijo.setVisitado();
			maxVal = Math.max(eval, maxVal);
			this.tree.setValor(maxVal);
		}
		return maxVal;
	}

	public double minValue() {
		double minVal = Double.POSITIVE_INFINITY;
		for (ArboldeJuego<Double> hijo : this.tree.getHijos()) {
			expandedNodes++;
			MinimaxArbolJuego treeHijo = new MinimaxArbolJuego(hijo);
			double eval = treeHijo.makeDecision();
			hijo.setVisitado();
			minVal = Math.min(eval, minVal);
			this.tree.setValor(minVal);
		}
		return minVal;
	}
	
	public void printTree(double x) {
		System.out.format("Valor con MINIMAX: %.2f\n", x);
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
