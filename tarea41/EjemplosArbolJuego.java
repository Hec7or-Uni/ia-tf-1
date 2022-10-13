package aima.core.search.adversarial;

import java.util.ArrayList;

public class EjemplosArbolJuego<V> {

	public static void main(String[] args) {
		ArboldeJuego<Double> tree1 = plant();
		ArboldeJuego<Double> tree2 = plant();
		
		MinimaxArbolJuego minMax = new MinimaxArbolJuego(tree1);
		AlfaBetaArbolJuego alphaBeta = new AlfaBetaArbolJuego(tree2);
		
		double x = minMax.makeDecision();
		double y = alphaBeta.makeDecision(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		
		minMax.printTree(x);
		System.out.println("-------------------------------");
		alphaBeta.printTree(y);
	}
	
	private static ArboldeJuego<Double> plant() {
		ArrayList<ArboldeJuego<Double>> sublist1 = new ArrayList<ArboldeJuego<Double>>();
			sublist1.add(new ArboldeJuego<Double>(3.0, true));	//MAX, terminal 3
			sublist1.add(new ArboldeJuego<Double>(12.0, true));	//MAX, terminal 12
			sublist1.add(new ArboldeJuego<Double>(8.0, true));	//MAX, terminal 8
		
		// árbol MIN del que cuelga 3,12,8
		ArboldeJuego<Double> subtree1 = new ArboldeJuego<Double>(Double.MIN_VALUE, false, sublist1);
		ArrayList<ArboldeJuego<Double>> sublist2 = new ArrayList<ArboldeJuego<Double>>();
			sublist2.add(new ArboldeJuego<Double>(2.0, true));
			sublist2.add(new ArboldeJuego<Double>(4.0, true));
			sublist2.add(new ArboldeJuego<Double>(6.0, true));
		
		// árbol MIN del que cuelga 2,4,6
		ArboldeJuego<Double> subtree2 = new ArboldeJuego<Double>(Double.MIN_VALUE, false, sublist2);
		ArrayList<ArboldeJuego<Double>> sublist3 = new ArrayList<ArboldeJuego<Double>>();
			sublist3.add(new ArboldeJuego<Double>(14.0, true));
			sublist3.add(new ArboldeJuego<Double>(5.0, true));
			sublist3.add(new ArboldeJuego<Double>(2.0, true));
		
		// árbol MIN del que cuelga 14,5,2
		ArboldeJuego<Double> subtree3 = new ArboldeJuego<Double>(Double.MIN_VALUE, false, sublist3);
		
		//Top arrayList con subárboles
		ArrayList<ArboldeJuego<Double>> topTree = new ArrayList<ArboldeJuego<Double>>();
			topTree.add(subtree1);
			topTree.add(subtree2);
			topTree.add(subtree3);
		
		return new ArboldeJuego<Double>(Double.MIN_VALUE, true, topTree);
	}
}