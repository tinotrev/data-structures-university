package TDALista;

import java.util.Iterator;
import Exceptions.*;
import Interfaces.*;

public class ListaDoblementeEnlazada<E> implements PositionList<E> {	
	
	protected Nodo<E> head;
	protected Nodo<E> tail;
	protected int size;
	
	public ListaDoblementeEnlazada() {
		head = new Nodo<>(null,null,null);
		tail = new Nodo<>(null,head,null);
		head.setNext(tail);
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public Position<E> first() throws EmptyListException {
		if(isEmpty())
			throw new EmptyListException("La lista está vacía.");
		return head.getNext();
	}

	public Position<E> last() throws EmptyListException {
		if(isEmpty())
			throw new EmptyListException("La lista está vacía.");
		return tail.getPrev();
	}

	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		Nodo<E> nodo = checkPosition(p); 
		if(nodo.getNext() == tail)
			throw new BoundaryViolationException("La posición corresponde al último elemento de la lista. ");
		return nodo.getNext();
	}

	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		Nodo<E> nodo = checkPosition(p);
		if(nodo.getPrev() == head)
			throw new BoundaryViolationException("La posición corresponde al último elemento de la lista. ");
		return nodo.getPrev();
	}

	public void addFirst(E element) {
		Nodo<E> nuevo = new Nodo<E>(element,head,null);
		nuevo.setNext(head.getNext());
		head.getNext().setPrev(nuevo);
		head.setNext(nuevo);
		size++;
	}

	public void addLast(E element) {
		Nodo<E> nuevo;
		if(isEmpty())
			addFirst(element);
		else {
			nuevo = new Nodo<E>(element,null,tail);
			nuevo.setPrev(tail.getPrev());
			tail.getPrev().setNext(nuevo);
			tail.setPrev(nuevo);
			size++;
		}
	}

	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		Nodo<E> pos = checkPosition(p);
		Nodo<E> nuevo = new Nodo<E>(element,pos,null);
		nuevo.setNext(pos.getNext());
		nuevo.getNext().setPrev(nuevo);
		pos.setNext(nuevo);
		size++;
	}

	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		Nodo<E> pos = checkPosition(p);
		Nodo<E> nuevo = new Nodo<E>(element,null,pos);
		nuevo.setPrev(pos.getPrev());
		pos.getPrev().setNext(nuevo);
		pos.setPrev(nuevo);
		size++;
	}

	public E remove(Position<E> p) throws InvalidPositionException {
		Nodo<E> pos = checkPosition(p);
        E toReturn;
        pos.getPrev().setNext(pos.getNext());
        pos.getNext().setPrev(pos.getPrev());
        toReturn = pos.element();
        pos.setElement(null);
        pos.setPrev(null);
        pos.setNext(null);
        size--;
        return toReturn;
	}

	public E set(Position<E> p, E element) throws InvalidPositionException {
		Nodo<E> pos = checkPosition(p);
		E toReturn = pos.element();
		pos.setElement(element);
		return toReturn;
	}

	public Iterator<E> iterator() {
		return new ElementIterator<E>(this);
	}

	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> p = new ListaDoblementeEnlazada<Position<E>>();
		if(!isEmpty()) {
			Position<E> pos = null;
			try {
				pos = first();
			} catch (EmptyListException e) { e.printStackTrace(); }
			try {
				while(pos != last()) {
					p.addLast(pos);
					pos = next(pos);
				}
			} catch (EmptyListException | InvalidPositionException | BoundaryViolationException e) {				
				e.printStackTrace();
			}	
			p.addLast(pos);
			} 
		return p;
	}
	
	//chequea que la Position que pasa como parámetro sea un nodo
	private Nodo<E> checkPosition(Position<E> p) throws InvalidPositionException {
		try {
			if( p == null )
                throw new InvalidPositionException("La posición es nula.");
            if( p == head )
                throw new InvalidPositionException("La posición es inválida.");
            if( p == tail )
                throw new InvalidPositionException("La posición es inválida.");
            if( p.element() == null )
                throw new InvalidPositionException("La posición fue eliminada previamente.");
            Nodo<E> nodo = (Nodo<E>) p;
            if ((nodo.getPrev() == null))
                throw new InvalidPositionException("La posición no tiene anterior.");
            else if ((nodo.getNext() == null)) 
            	throw new InvalidPositionException("La posición no tiene siguiente.");
            return nodo;
		} catch(ClassCastException e) {
			throw new InvalidPositionException("La posición no es de tipo Nodo E.");
		}
	}
	
	
	//***********************************EJERCICIO 2 TP4*************************************//
	public void agregarElem(E e1, E e2) {
		if(isEmpty()) {
			/*a pedido del test (no entiendo el porqué) en el caso de tener la lista vacía agrego al principio el elemento 
			que debería ser el ante último y agrego al final el elemento que debería ser el segundo*/
			this.addFirst(e2);
			this.addLast(e1);
		}
		else {
			try {
				if(size == 1) {
					Position<E> unicaPos = this.first();
					this.addBefore(unicaPos, e1);
					this.addAfter(unicaPos, e2);
				}
				else {
					Position<E> primerPos = this.first();
					this.addAfter(primerPos, e1);
					Position<E> ultimaPos = this.last();
					this.addBefore(ultimaPos, e2);
				}
			} catch(InvalidPositionException | EmptyListException ex) {
				ex.printStackTrace();
			}
		}
	}
	//**************************************************************************************//
}