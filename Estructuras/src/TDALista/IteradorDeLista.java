package TDALista;

import java.util.Iterator;
import Interfaces.*;
import Exceptions.*;

public class IteradorDeLista<E> implements Iterator<E> {

	protected Position<E> actual;
	protected PositionList<E> estructura;
	
	public IteradorDeLista( ListaDoblementeEnlazada<E> ed ) {
		this.estructura = ed;
		if( !this.estructura.isEmpty() ) {
			try {
				this.actual = ed.first();
			} catch (EmptyListException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean hasNext() {
		return this.actual != null ;
	}

	@Override
	public E next() {
		E resultado = null;
		if(this.actual != null ) {
			resultado = this.actual.element();
			try {
				if(this.actual == this.estructura.last() ) {
					this.actual = null;
				} else {
					this.actual = this.estructura.next(this.actual);
				}
			} catch (InvalidPositionException | EmptyListException | BoundaryViolationException e) {
				e.printStackTrace();
			}
		} 
		return resultado;
	}
}
