package TDALista;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.Iterator;

import Interfaces.PositionList;

public class Métodos<E> {
	public static void main(String[]args) {
		//***********************************EJERCICIO 5 TP3*************************************//
		ArrayList<Integer> l1 = new ArrayList<Integer>();
		ArrayList<Integer> l2 = new ArrayList<Integer>();
		
		l1.add(1);
		l1.add(2);
		l1.add(3);
		l1.add(4);
		l1.add(9);
		l1.add(8);
		l1.add(7);
		l1.add(6);
		l1.add(5);
		l1.add(10);
		
		l2.add(9);
		l2.add(7);
		l2.add(5);
		l2.add(6);
		l2.add(8);
		
		System.out.println();
		modificarLista(l1,l2);
		System.out.println();
		
		System.out.println(l1);
		
		//***************************************************************************************//
		
		//***********************************EJERCICIO 4 TP3*************************************//
		/*
		ArrayList<Integer> l1 = new ArrayList<Integer>();
		ArrayList<Integer> l2 = new ArrayList<Integer>();
		
		l1.add(1);
		l1.add(2);
		l1.add(3);
		l1.add(4);
		l1.add(4);
		l1.add(3);
		l1.add(2);
		l1.add(1);
		
		l2.add(1);
		l2.add(2);
		l2.add(3);
		l2.add(4);
		
		System.out.println();
		System.out.println("l1 compuesta de l2: " + estaCompuesta(l1,l2));
		System.out.println();
		
		ArrayList<Integer> l3 = new ArrayList<Integer>();
		
		l3.add(1);
		l3.add(2);
		l3.add(3);
		l3.add(4);
		l3.add(4);
		l3.add(3);
		l3.add(2);
		l3.add(4);
		
		System.out.println();
		System.out.println("l3 compuesta de l2: " + estaCompuesta(l3,l2));
		System.out.println();
		
		ArrayList<Integer> l4 = new ArrayList<Integer>();
		
		l4.add(1);
		l4.add(2);
		l4.add(3);
		
		System.out.println();
		System.out.println("l4 compuesta de l2: " + estaCompuesta(l4,l2));
		System.out.println();
		*/
		//**************************************************************************************//
	}
	
	
	
	//***********************************EJERCICIO 1a TP3***************************************//
	public static<E> boolean esta(ArrayList<E> lista, E x) {
		boolean esta = false;
		for(int i = 0; i < lista.size() && !esta; i++) {
			esta = lista.get(i).equals(x);
		}
		return esta;
	}
	//****************************************************************************************//
	
	//***********************************EJERCICIO 1b TP3*************************************//
	public static<E> boolean estaNVeces(ArrayList<E> lista, E e, int n) {
		int contador = 0;
		for(int i = 0; i < lista.size() && contador < n; i++) {
			E elem = lista.get(i);
			if(e == elem) {
				contador++;
			}
		}
		return contador == n;
	}
	//****************************************************************************************//
	
	//***********************************EJERCICIO 2a TP3*************************************//
	public static<E> ArrayList<E> intercalar(ArrayList<E> l1, ArrayList<E> l2) {
		ArrayList<E> toRet = new ArrayList<E>();
		int i = 0;
		while(i < l1.size() || i < l2.size()) {
			if(l1.size() > i) {
				toRet.add(l1.get(i));
			}
			if(i < l2.size()) {
				toRet.add(l2.get(i));
			}
			i++;
		}
		return toRet;
	}
	//****************************************************************************************//
	
	//***********************************EJERCICIO 2b TP3*************************************//
	public static ArrayList<Integer> intercalarNoRep(ArrayList<Integer> l1, ArrayList<Integer> l2) {
		ArrayList<Integer> toRet = new ArrayList<Integer>();
		int i = 0; int j = 0;
		Integer e1 = null;
		Integer e2 = null;
		while(i < l1.size() && j < l2.size()) {
			e1 = l1.get(i);
			e2 = l2.get(j);
			if(e1 < e2) {
				toRet.add(e1);
				i++;
			}
			else {
				if(e1 > e2) {
					toRet.add(e2);
					j++;
				}
				else {
					toRet.add(e1);
					i++; j++;
				}
			}
		} //salimos del while solo cuando llegamos al final de alguna de la listas
		while(i < l1.size()) {
			toRet.add(l1.get(i));
			i++;
		}
		while(j < l2.size()) {
			toRet.add(l2.get(j));
			j++;
		}
		return toRet;
	}
	//***************************************************************************************//
	
	//***********************************EJERCICIO 3 TP3*************************************//
	public static<E> void invertir(ArrayList<E> lista) {
		Stack<E> pila = new Stack<E>();
		for(E e : lista) {
			pila.push(e);
		}
		for(int i = 0; !pila.isEmpty(); i++) {
			lista.add(i, pila.pop());
		}
	}
	//***************************************************************************************//
	
	//***********************************EJERCICIO 4 TP3*************************************//
	public static<E> boolean estaCompuesta(ArrayList<E> l1, ArrayList<E> l2) {
		boolean toRet = false;
		int cursor = 0; //nos permite recorrer l1
		if(l1.size() == (2 * l2.size())) { //si l1 es exactamente el doble que l2 (condición obligatoria)
			toRet = true; //hasta que se demuestre lo contrario
			for(int i = 0; i < l2.size() && toRet; i++) {
				toRet = l1.get(cursor).equals(l2.get(i));
				cursor++;
			}
			for(int j = l2.size() - 1; j >= 0 && toRet; j--) {
				toRet = l1.get(cursor).equals(l2.get(j));
				cursor++;;
			}
		}
		else {
			System.out.println("------------------------------------------");
			System.out.println("La lista no cumple la condición principal.");
			System.out.println("------------------------------------------");
		}
		return toRet;
	}
	//***************************************************************************************//
	
	//***********************************EJERCICIO 5 TP3*************************************//
	public static<E> void modificarLista(ArrayList<E> l1, ArrayList<E> l2) {
		for(E elem : l2) {
			l1.removeAll(Collections.singleton(elem));
		}
		for(int i = l2.size() - 1; i >= 0; i--) {
			l1.add(l2.get(i));
		}
	}
	//***************************************************************************************//
	
	//***********************************EJERCICIO 3 TP4*************************************//
	public static<E> boolean estaElem(PositionList<E> l, E e1) {
		boolean toRet = false;
		E elem = null;
		Iterator<E> it = l.iterator();
		while(!toRet && it.hasNext()) {
			elem = it.next();
			toRet = e1.equals(elem);
		}
		return toRet;
	}
	//***************************************************************************************//
	
	//***********************************EJERCICIO 4 TP4*************************************//
	public static<E> PositionList<E> elemRepetidos(PositionList<E> l) {
		PositionList<E> toRet = new ListaDoblementeEnlazada<E>();
		for(E e : l ) {
			toRet.addLast(e);
			toRet.addLast(e);
		}
		return toRet;
	}
	//**************************************************************************************//
}