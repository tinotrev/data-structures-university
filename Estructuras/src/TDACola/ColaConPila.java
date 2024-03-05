package TDACola;

import Interfaces.Queue;
import java.util.Stack;

import Exceptions.EmptyQueueException;

public class ColaConPila<E> implements Queue<E> {
	
	protected Stack<E> pila;
	
	public ColaConPila() {
		pila = new Stack<E>();
	}
	
	@Override
	public int size() {
		return pila.size();
	}
	@Override
	public boolean isEmpty() {
		return pila.size() == 0;
	}
	@Override
	public E front() throws EmptyQueueException {
		E toRet = null;
		if(!isEmpty()) {
			toRet = pila.get(0); //consultamos el elemento en el fondo de la pila
		}
		else
			throw new EmptyQueueException("Cola vacía");
		return toRet;
	}
	@Override
	public void enqueue(E element) {
		pila.push(element);
	}

	@Override
	public E dequeue() throws EmptyQueueException {
		E toRet = null;
		if(!pila.isEmpty()) {	
			toRet = pila.remove(0); //eliminamos el elemento en el fondo de la pila
	 	}
		else
			throw new EmptyQueueException("Cola vacía");
		return toRet;
	}

}
