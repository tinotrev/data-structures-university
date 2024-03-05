package TDAÁrbolBinario;

import Interfaces.*;
import Exceptions.*;
import TDALista.*;

public class Métodos {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws EmptyTreeException, InvalidPositionException, BoundaryViolationException {
		
		//***********************************EJERCICIO 3 TP7*************************************//
		BinaryTree<Character> bt1 = new ÁrbolBinario<Character>();
		
		Position<Character> div = null;
        Position<Character> por = null;
        Position<Character> mas1 = null;
        Position<Character> mas2 = null;
        Position<Character> menos = null;
        Position<Character> n3_1 = null;
        Position<Character> n3_2 = null;
        Position<Character> n1 = null;
        Position<Character> n9 = null;
        Position<Character> n5 = null;
        Position<Character> n2 = null;
		
        try {
        	
			bt1.createRoot('/');
	        div =  bt1.root();
	        
	        por = bt1.addLeft(div, '*');
	        mas1 = bt1.addRight(div, '+');
	        
	        mas2 = bt1.addLeft(por,'+');
	        n3_1 = bt1.addRight(por, '3');
	        
	        n3_2 = bt1.addLeft(mas1, '3');
	        n1 = bt1.addRight(mas1, '1');
	        
	        menos = bt1.addLeft(mas2, '-');
	        n2 = bt1.addRight(mas2, '2');
	        
	        n9 = bt1.addLeft(menos, '9');
	        n5 = bt1.addRight(menos, '5');
        
        } catch(EmptyTreeException | InvalidOperationException | InvalidPositionException e) {
        	e.printStackTrace();
        }
        
		Iterable<Character> lista = arbAExp(bt1);
		
		for(Character c : lista) {
			System.out.print(c + " ");
		}
	//****************************************************************************************//
		
	}
	//***********************************EJERCICIO 3 TP7*************************************//
	private static Iterable<Character> arbAExp(BinaryTree<Character> BT) throws EmptyTreeException, InvalidPositionException, BoundaryViolationException {
		PositionList<Character> toRet = new ListaDoblementeEnlazada<Character>();
		inOrden(BT, BT.root(), toRet);
		return toRet;
	}
	
	private static void inOrden(BinaryTree<Character> BT, Position<Character> pos, PositionList<Character> l) throws InvalidPositionException, BoundaryViolationException {
		if(BT.hasLeft(pos)) {
			inOrden(BT, BT.left(pos), l);
		}
		visitar(BT, pos, l);
		if(BT.hasRight(pos)) {
			inOrden(BT, BT.right(pos), l);
		}
	}
	
	private static void visitar(BinaryTree<Character> BT, Position<Character> pos, PositionList<Character> l) {
		l.addLast(pos.element());
	}
	//****************************************************************************************//
}
