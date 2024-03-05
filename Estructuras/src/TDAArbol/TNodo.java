package TDAArbol;

import Interfaces.*;
import TDALista.*;

public class TNodo<E> implements Position<E>{
	protected E elem;
	protected TNodo<E> padre;
	protected PositionList<TNodo<E>> hijos;
	
	public TNodo(E e, TNodo<E> p) {
		elem = e;
		padre = p;
		hijos = new ListaDoblementeEnlazada<TNodo<E>>();
	}

	public TNodo(E elem) {
        this(elem, null);
    }
	
	@Override
	public E element() {
		return elem;
	}
	
	public void setElement(E e) {
		elem = e;
	}
	public void setPadre(TNodo<E> p) {
		padre = p;
	}
	public E getElement() {
		return elem;
	}
	public TNodo<E> getPadre() {
		return padre;
	}
	public PositionList<TNodo<E>> getHijos() {
		return hijos;
	}
}