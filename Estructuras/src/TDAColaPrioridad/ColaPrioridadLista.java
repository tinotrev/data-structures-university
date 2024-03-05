package TDAColaPrioridad;

import Interfaces.*;
import java.util.Comparator;
import java.util.Iterator;

import Exceptions.*;
import TDALista.ListaDoblementeEnlazada;

public class ColaPrioridadLista<K,V> implements PriorityQueue<K,V> {

	protected int size;
	protected Comparator<K> comp;
	protected PositionList<Entry<K,V>> entradas;
	
	public ColaPrioridadLista(Comparator<K> c) {
		size = 0;
		comp = c;
		entradas = new ListaDoblementeEnlazada<>();
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
		try {
			Entry<K,V> entAct = entradas.first().element();
			for(Entry<K,V> e : entradas) {
				int c = comp.compare(e.getKey(), entAct.getKey());
				if(c < 0) { //la llave leída es menor (más prioridad) a la actual
					entAct = e;
				}
			}
			return entAct;
		} catch (EmptyListException e) {
			throw new EmptyPriorityQueueException("Cola vacía");
			}
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if(key == null) {
			throw new InvalidKeyException("Llave invalida");
		}
		Entry<K,V> toRet = new Entrada<>(key, value);
		entradas.addLast(toRet);
		size++;
		return toRet;
	}

	@Override
	public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
		//para eliminar de la lista tengo que trabajar con Position
		if(size == 0) {
			throw new EmptyPriorityQueueException("Cola vacia");
		}
		try {
			Iterable<Position<Entry<K,V>>> positions = entradas.positions();
			Iterator<Position<Entry<K,V>>> it = positions.iterator();
			Position<Entry<K,V>> posAct = it.next();
			for(Position<Entry<K,V>> p : positions) { //O(n)
				int c = comp.compare(p.element().getKey(), posAct.element().getKey());
				if(c < 0) { //la llave leída es menor (más prioridad) a la actual
					posAct = p;
				}
			}
			Entry<K,V> toRet = posAct.element();
			entradas.remove(posAct);
			size--;
			return toRet;
		} catch (InvalidPositionException e) {
			// TODO Auto-generated catch block
			throw new EmptyPriorityQueueException("Algo salió mal");
		}
	}
}