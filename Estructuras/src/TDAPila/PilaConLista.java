package TDAPila;

import Exceptions.*;
import Interfaces.*;
import java.util.ArrayList;

public class PilaConLista<E> implements Stack<E> {

	protected ArrayList<E> lista;
	
	public PilaConLista() {
		lista = new ArrayList<E>();
	}
	
	@Override
	public int size() {
		return lista.size();
	}

	@Override
	public boolean isEmpty() {
		return lista.isEmpty();
	}

	@Override
	public E top() throws EmptyStackException {
		if(isEmpty()) {
			throw new EmptyStackException("Pila vacía");
		}
		E toRet = lista.get(lista.size() - 1);
		return toRet;
	}

	@Override
	public void push(E element) {
		lista.add(element);
		
	}

	@Override
	public E pop() throws EmptyStackException {
		if(isEmpty()) {
			throw new EmptyStackException("Pila vacía");
		}
		int tamañoLista = lista.size();
		E toRet = lista.remove(tamañoLista - 1);
		return toRet;
	}
}
