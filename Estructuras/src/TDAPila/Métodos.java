package TDAPila;

import java.util.Stack;

public class Métodos {
	public static void main(String[]args) {
		//***********************************EJERCICIO 1 TP2*************************************//
		/*
		Object[] arreglo = new Object[11];
		
		arreglo[0] = 1;
		arreglo[1] = 2;
		arreglo[2] = 3;
		arreglo[3] = 4;
		arreglo[4] = 5;
		arreglo[5] = 6;
		arreglo[6] = 7;
		arreglo[7] = 8;
		arreglo[8] = 9;
		arreglo[9] = 10;
		arreglo[10] = 11;
		
		imprimirArreglo(arreglo);
		
		System.out.println();
		invertir(arreglo);
		System.out.println();
		
		imprimirArreglo(arreglo);
		*/
		//**************************************************************************************//
		
		//***********************************EJERCICIO 3 TP2*************************************//
		Stack<Integer> p1 = new Stack<Integer>();
		Stack<Integer> p2 = new Stack<Integer>();
		
		p1.add(1);
		p1.add(2);
		p1.add(3);
		
		p2.add(4);
		p2.add(5);
		p2.add(6);
		p2.add(1);
		
		imprimirPila(p1);
		System.out.println();
		imprimirPila(p2);
		System.out.println();
		
		Stack<Integer> resultante = intercalarPilas(p1,p2);
		
		imprimirPila(resultante);
		//**************************************************************************************//
	}
	//**************************************METODOS EXTRAS***************************************************//
	public static void imprimirArreglo(Object[] arreglo) {
		for(int i = 0; i < arreglo.length; i++) {
			System.out.println((i + 1) + " elemento: " + arreglo[i]);
		}
	}
	
	public static<E> void imprimirPila(Stack<E> pila) {
		Object[] arreglo = pila.toArray();
		System.out.println("*************Tope de la pila (primeros en salir)*************");
		for(int i = arreglo.length - 1; i >= 0; i--) {
			System.out.println((arreglo.length - i) + " elemento: " + arreglo[i]);
		}
		System.out.println("*************Final de la pila (últimos en salir)*************");
	}
	//*******************************************************************************************************//
	
	//***********************************EJERCICIO 1 TP2*************************************//
	public static void invertir(Object[] a) {
		Stack<Object> pila = new Stack<Object>();
		for(int i = 0; i < a.length; i++) {
			pila.add(a[i]);
		}
		for(int i = 0; i < a.length; i++) {
			a[i] = pila.pop();
		}
	}
	//**************************************************************************************//
	//***********************************EJERCICIO 3 TP2*************************************//
	public static<E> Stack<E> intercalarPilas(Stack<E> p1, Stack<E> p2) {
		Stack<E> toRet = new Stack<E>();
		while(!p1.isEmpty() || !p2.isEmpty()) {
			if(!p1.isEmpty()) {
				toRet.add(p1.pop());
			}
			if(!p2.isEmpty()) {
				toRet.add(p2.pop());
			}
		}
		return toRet;
	}
	//**************************************************************************************//
}
