package TDACola;

import java.util.Queue;

import java.util.LinkedList;

public class Métodos {
	public static void main(String[]args) {
		
		//***********************************EJERCICIO 2 TP2*************************************//
		Queue<Integer> cola = new LinkedList<Integer>();
		
		cola.add(1);
		cola.add(2);
		cola.add(3);
		cola.add(4);
		cola.add(5);
		cola.add(6);
		cola.add(7);
		cola.add(8);
		cola.add(9);
		cola.add(10);
		cola.add(11);
		
		imprimirCola(cola);
		
		System.out.println();
		
		imprimirCola(soloImpares(cola));
		//**************************************************************************************//
		
	}
	//**************************************METODOS EXTRAS***************************************************//
		public static<E> void imprimirCola(Queue<E> cola) {
			Object[] arreglo = cola.toArray();
			System.out.println("*************Inicio de la cola (primeros en salir)*************");
			for(int i = 0; i < arreglo.length; i++) {
				System.out.println((i + 1) + " elemento: " + arreglo[i]);
			}
			System.out.println("*************Fin de la cola (últimos en salir)*************");
		}
	//*******************************************************************************************************//
	
	//***********************************EJERCICIO 2 TP2*************************************//
	public static Queue<Integer> soloImpares(Queue<Integer> cola) {
		Queue<Integer> toRet = new LinkedList<Integer>();
		while(!cola.isEmpty()) {
			Integer entero = cola.remove();
			if(!(entero % 2 == 0)) {
				toRet.add(entero);
			}
		}
		return toRet;
	}
	//**************************************************************************************//
}
