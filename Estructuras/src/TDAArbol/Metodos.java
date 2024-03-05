package TDAArbol;

import java.util.Iterator;
import Interfaces.*;
import TDALista.*;
import Exceptions.*;

public class Metodos {
	
	public Metodos() {}
	
	@SuppressWarnings("unused")
	public static void main(String[]args) throws EmptyTreeException, InvalidPositionException {
			
		//*************************************EJERCICIO 4 TP6***********************************************//
		Tree<String> tr = new Árbol<String>();
		
		Position<String> a = null;
        Position<String> b = null;
        Position<String> c = null;
        Position<String> d = null;
        Position<String> e = null;
        Position<String> f = null;
        Position<String> g = null;
        Position<String> h = null;
        Position<String> k = null;
        Position<String> l = null;
        Position<String> m = null;
        Position<String> p = null;
        Position<String> z = null;


        try {
        	
            tr.createRoot("a");
            a =  tr.root();
            b = tr.addLastChild(a, "b");
            c = tr.addLastChild(a, "c");
            d = tr.addLastChild(a, "d");

            h = tr.addLastChild(b,"h");
            f = tr.addLastChild(b,"f");

            k = tr.addLastChild(h,"k");
            g = tr.addLastChild(h,"c");

            e = tr.addLastChild(c,"e");

            l = tr.addLastChild(d,"l");
            z = tr.addLastChild(d,"z");

            m = tr.addLastChild(l,"c");
            p = tr.addLastChild(m,"p");
            
           } catch(InvalidPositionException | InvalidOperationException | EmptyTreeException aaa) {}
		
        	
	        Iterable<Position<String>> lista = soloEseString(tr, "c");
			for(Position<String> r : lista) {
				System.out.println(r.element());
			}
	}
	//*******************************************************************************************************//
	
	//**************************************METODOS EXTRAS***************************************************//
	public static<E> Integer profundidad(Tree<E> T, Position<E> pos) throws InvalidPositionException, BoundaryViolationException {
		if(T.isRoot(pos) ) {
			return 0;
		}
		else
			return 1 + profundidad(T, T.parent(pos));
	}
	public static<E> Integer altura(Tree<E> T) throws InvalidPositionException, BoundaryViolationException {
		Integer toRet = 0;
		for(Position<E> pos : T.positions()) {
			if(T.isExternal(pos)) {
				toRet = Math.max(toRet, profundidad(T, pos));
			}
		}
		return toRet;
	}
	//*******************************************************************************************************//
	
	//********************************************************************EJERCICIO 4 TP6********************************************************************//
	private static Iterable<Position<String>> soloEseString(Tree<String> T, String s) throws EmptyTreeException, InvalidPositionException {
		PositionList<Position<String>> toRet = new ListaDoblementeEnlazada<Position<String>>();
		postOrden(T, s, toRet, T.root());
		return toRet;
	}
	
	private static void postOrden(Tree<String> T, String s, PositionList<Position<String>> lista, Position<String> pos) throws InvalidPositionException {
		for(Position<String> p : T.children(pos)) {
				postOrden(T, s, lista, p);
		}
		visitar(T, s, lista, pos);
		System.out.println("visite posicion: " + pos.element());
		}
	
	private static void visitar(Tree<String> T, String s, PositionList<Position<String>> lista, Position<String> pos) {
		if(pos.element().equals(s)) {
			lista.addLast(pos);
		}
	}
	//******************************************************************************************************************************************************//
	
	//***********************************EJERCICIO 5 TP6*************************************//
	public static<E> Integer aparicionesEEnA(Tree<E> T, E e) throws InvalidPositionException {
		Integer toRet = 0;
		for(Position<E> pos : T.positions()) {
			if(pos.element().equals(e)) {
				T.removeNode(pos);
				toRet++;
			}
		}
		return toRet;
	}
	//**************************************************************************************//
	
	//***********************************EJERCICIO 6 TP6*************************************//
	public static boolean nPertenece(Tree<Integer> T, Integer a) {
		boolean pertenece = false;
		Integer cursor = null;
		Iterator<Integer> it = T.iterator();
		while(!pertenece && it.hasNext()) {
			cursor = it.next();
			if(cursor == a) {
				pertenece = true;
			}
		}
		return pertenece;
	}
	//**************************************************************************************//
	
	
	
	
	
	
	//************************************************************************CODIGO VIEJO/INSERVIBLE**********************************************************************//
	/*
	private static void recPostOrden(Position<String> p, PositionList<Position<String>> l, Tree<String> T, String s) {
		try {
			for(Position<String> pos : T.children(p)) {
				recPostOrden(pos, l, T, s);
			}
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		}
		if(p.element().equals(s)){
			l.addLast(p);
		}
	}
	private static Iterable<Position<String>> soloEseString(Tree<String> T, String s) {
		PositionList<Position<String>> toRet = new ListaDoblementeEnlazada<Position<String>>();
		try {
			recPostOrden(T.root(), toRet, T, s);
		} catch (EmptyTreeException e) {
			e.printStackTrace();
		}
		return toRet;
	}
	*/
	
	/*
	private static Iterable<Position<Character>> notInf(BinaryTree<Character> T) throws EmptyTreeException, InvalidPositionException, BoundaryViolationException {
		PositionList<Position<Character>> toRet = new ListaDoblementeEnlazada<Position<Character>>();
		inOrden(T.root(), T, toRet);
		return toRet;
	}
	
	private static void inOrden(Position<Position<Character>> pos, BinaryTree<Character> T, PositionList<Character> l) throws InvalidPositionException, BoundaryViolationException {
		if(T.hasLeft(pos.element())) {
			inOrden(T.left(pos), T, l);
			System.out.println("estoy en inOrden left");
		}
		l.addLast(pos.element());
		System.out.println("imprimo");
		if(T.hasRight(pos)) {
			inOrden(T.left(pos), T, l);
			System.out.println("estoy en inOrden right");
		}
	}

	
	private static boolean esOperador(Character o) {
		return (o.equals('+')) || (o.equals('-')) || (o.equals('*')) || (o.equals('*'));
	}
	*/
	//******************************************************************************************************************************************************************************//
}
