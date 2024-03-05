package TDAÁrbolBinario;

import Interfaces.*;

public class BTNodo<E> implements Position<E> {

	E element;
	BTNodo<E> padre;
	BTNodo<E> left;
	BTNodo<E> right;
	
	public BTNodo(E e, BTNodo<E> p, BTNodo<E> l, BTNodo<E> r) {
		element = e;
		padre = p;
		left = l;
		right =l;
	}
	public BTNodo(E e, BTNodo<E> p) {
		element = e;
		padre = p;
		left = null;
		right = null;
	}
	
	public BTNodo<E> getPadre() {
		return padre;
	}
	
	public BTNodo<E> getRight() {
		return right;
	}
	
	public BTNodo<E> getLeft() {
		return left;
	}
	
	public void setPadre(BTNodo<E> p) {
		padre = p;
	}
	
	public void setRight(BTNodo<E> r) {
		right = r;
	}
	
	public void setLeft(BTNodo<E> l) {
		left = l;
	}
	
	public void setElement(E e) {
		element = e;
	}
	
	public E element() {
		// TODO Auto-generated method stub
		return element;
	}
	
}
