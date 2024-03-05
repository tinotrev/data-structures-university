package TDAColaPrioridad;

import java.util.Comparator;
import Exceptions.*;
import Interfaces.*;

public class ColaPrioridadHeap<K,V> implements PriorityQueue<K,V> {

	int size;
	Comparator<K> comp;
	
	Entrada<K,V>[] entradas;
	/* REPRESENTACION DE UN ARBOL CON ARREGLOS:
	 * hijo_izquierdo(i) = 2i
	 * hijo_derecho(i) = 2i + 1
	 * padre(i) = i div 2
	 * 
	 * La componente del arreglo no se usa
	 * La raíz se guarda en la componente 1
	 */
	
	@SuppressWarnings("unchecked")
	public ColaPrioridadHeap(int maxEnt, Comparator<K> c) {
		entradas = (Entrada<K,V> []) new Entrada[maxEnt];
		size = 0;
		comp = c; 
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Entry<K, V> min() throws EmptyPriorityQueueException {
		if(size == 0) {
			throw new EmptyPriorityQueueException("Cola vacía");
		}
		return entradas[1];
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if(key == null)
			throw new InvalidKeyException("Llave inválida");
		if((size + 1) >= entradas.length) { //hemos superado el límite del arreglo
			reSize();
		}
		Entrada<K,V> aInsertar = new Entrada<>(key, value);
		entradas[++size] = aInsertar;
		//comienzo a burbujear
		boolean seguir = true;
		int i = size;
		while(seguir && i > 1) { //mientras seguir sea verdadero y no haya llegado a la raíz
			Entrada<K,V> entAct = entradas[i];
			Entrada<K,V> entPadre = entradas[i / 2];
			if(comp.compare(entAct.getKey(), entPadre.getKey()) < 0) { //el actual es más chico que el padre, procedo a intercambiar
				Entrada<K,V> aux = entradas[i];
				entradas[i] = entradas[i/2];
				entradas[i / 2] = aux;
				i/= 2; //llevo al índice al padre para el siguiente bucle
			}
			else
				seguir = false;
		}
		return aInsertar;
	}
	
	//método auxiliar para insert()
	@SuppressWarnings("unchecked")
	private void reSize() {
		int sizeAct = entradas.length;
		Entrada<K,V>[] newEnt = (Entrada<K,V>[]) new Entrada[sizeAct + sizeAct/2];
		int i = 0;
		for(Entrada<K,V> e : entradas) {
			newEnt[i++] = e;
		}
		entradas = newEnt;
	}

	//se remueve el elemento de la raiz y es reemplazo por el elemento de la última hoja y burbujeo para abajo buscando su ubicación
	@Override
	public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
		if(size == 0)
			throw new EmptyPriorityQueueException("Cola vacía");
		Entry<K,V> toReturn = min();
		if(size == 1) {
			entradas[1] = null;
			size = 0;
		}
		else {
			entradas[1] = entradas[size];
			entradas[size] = null;
			size--;
			//comienzo el burbujeo para abajo
			int i = 1;
			boolean seguir = true;
			int m = 1; //en esta variable guardaremos la posición del minimo de los hijos de i (izquierdo o derecho)
			while(seguir) {
				boolean tieneHijoIzquierdo = (2*i) <= size;
				boolean tieneHijoDerecho = (2*i + 1) <= size;
				if(!tieneHijoIzquierdo) {
					seguir = false;
					System.out.println(1);
				}
				else {
					if(tieneHijoDerecho) {
						if(comp.compare(entradas[2*i].getKey(), entradas[2*i + 1].getKey()) < 0) {
							m = 2*i; //el minimo es el hijo izquierdo
							System.out.println(2);
						}
						else {
							m = 2*i + 1; //el minimo es el hijo derecho
							System.out.println(3);
						}
					}
					else {
						m = 2*i; //si tiene hijo izquierdo y no tiene hijo derecho el minimo es si o si el izquierdo
						System.out.println(4);
					}
				}
				System.out.println("valor m:" + m);
				//una vez localizado el minimo hijo vemos si hay que ordenar o no
				if(comp.compare(entradas[i].getKey(), entradas[m].getKey()) > 0) {
					Entrada<K,V> aux = entradas[i];
					entradas[i] = entradas[m];
					entradas[m] = aux;
					i = m;
				}
				else {
					seguir = false;
				}
			}
		}
		return toReturn;
	}
}
