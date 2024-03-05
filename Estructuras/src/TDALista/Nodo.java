package TDALista;

import Interfaces.*;

public class Nodo<E> implements Position<E> {
	
	//atributos de instancia
	private E element;
	private Nodo<E> prev;
	private Nodo<E> next;
	
	//constructores
	public Nodo(E e, Nodo<E> p, Nodo<E> n) {
		element = e;
		prev = p;
		next = n;
	}
	
	public Nodo(E e) {
		this(e,null,null);
	}
	
	//getters
	public E element() { 
		return element; 
	}
	
	public Nodo<E> getPrev(){ 
		return prev;
	}
	
	public Nodo<E> getNext(){ 
		return next;
	}
	
	//setters
	public void setElement(E e) {
		element = e;
	}
	
	public void setPrev(Nodo<E> p) {
		prev = p;
	}
	
	public void setNext(Nodo<E> n) {
		next = n; }
	}