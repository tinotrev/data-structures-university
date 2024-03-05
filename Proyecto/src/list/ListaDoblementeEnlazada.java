package list;

import java.util.Iterator;

public class ListaDoblementeEnlazada<E> implements PositionList<E> {
	protected int cant;
	protected Nodo<E> primero;
	protected Nodo<E> ultimo;
	
	public ListaDoblementeEnlazada() {
		cant=0;
		primero=new Nodo<E>(null);
		ultimo=new Nodo<E>(null);
		primero.setNext(ultimo);
		ultimo.setPrev(primero);
	}

	@Override
	public int size() {
		return cant;
	}

	@Override
	public boolean isEmpty() { 
		return (cant==0);
	}

	@Override
	public Position<E> first() throws EmptyListException {
		if(isEmpty()) {
			throw new EmptyListException("Lista vacia");
		}
		return primero.getNext();
	}

	@Override
	public Position<E> last() throws EmptyListException {
		if(isEmpty()) {
			throw new EmptyListException("Lista vacia");
		}
		
		return ultimo.getPrev();
	}

	@Override
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		Nodo<E> n = checkPosition(p);
		if(n.getNext()==ultimo) {
			throw new BoundaryViolationException("El Ãºltimo no tiene siguiete");
		}
		return n.getNext();
	}

	@Override
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		Nodo<E> n = checkPosition(p);
		if(n.getPrev()==primero) {
			throw new BoundaryViolationException("El primero no tiene anterior");
		}
		return n.getPrev();
	} 

	@Override
	public void addFirst(E element) { 
		Nodo<E> nuevo = new Nodo<E>(element);
		Nodo<E> sigNuevo = primero.getNext();
		nuevo.setPrev(primero);
		nuevo.setNext(sigNuevo);
		sigNuevo.setPrev(nuevo);
		primero.setNext(nuevo);
		cant++;
		
	}

	@Override
	public void addLast(E element) { 
		Nodo<E> nuevo = new Nodo<E>(element);
		Nodo<E> antNuevo = ultimo.getPrev();
		nuevo.setPrev(antNuevo);
		nuevo.setNext(ultimo);
		antNuevo.setNext(nuevo);
		ultimo.setPrev(nuevo);
		cant++;
		
	}

	@Override
	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		Nodo<E> antNuevo = checkPosition(p);
		Nodo<E> sigNuevo = antNuevo.getNext();
		Nodo<E> nuevo = new Nodo<E>(element);
		nuevo.setPrev(antNuevo);
		nuevo.setNext(sigNuevo);
		antNuevo.setNext(nuevo);
		sigNuevo.setPrev(nuevo);
		cant++;
		
	}

	@Override
	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		Nodo<E> sigNuevo = checkPosition(p);
		Nodo<E> antNuevo = sigNuevo.getPrev();
		Nodo<E> nuevo = new Nodo<E>(element);
		nuevo.setPrev(antNuevo);
		nuevo.setNext(sigNuevo);
		sigNuevo.setPrev(nuevo);
		antNuevo.setNext(nuevo);
		cant++;
		
	}

	@Override
	public E remove(Position<E> p) throws InvalidPositionException { 
		Nodo<E> borrar = checkPosition(p);
		E retorno = borrar.element();
		Nodo<E> antBorrar = borrar.getPrev();
		Nodo<E> sigBorrar = borrar.getNext();
		borrar.setPrev(null);
		borrar.setNext(null);
		antBorrar.setNext(sigBorrar);
		sigBorrar.setPrev(antBorrar);
		cant--;
		return retorno;
	}

	@Override
	public E set(Position<E> p, E element) throws InvalidPositionException { 
		Nodo<E> acomodar = checkPosition(p);
		E retorno = acomodar.element();
		acomodar.setElement(element);
		return retorno;
	}

	@Override
	public Iterator<E> iterator() { 
		ElementIterator<E> resultado = new ElementIterator<E>(this);
		return resultado;
	}

	@Override
	public Iterable<Position<E>> positions() { 
		PositionList<Position<E>> nueva = new ListaDoblementeEnlazada<Position<E>>();
		if(!isEmpty()) {
			try {
				Position<E> pos = first();
				while(pos!=last()) {
					nueva.addLast(pos);
					pos = next(pos);
				}
				nueva.addLast(pos);
			}
			catch(EmptyListException | InvalidPositionException | BoundaryViolationException e) {
				System.out.println("Hubo un problema...");
			}
		}
		return nueva;
	}

	private Nodo<E> checkPosition(Position<E> p) throws InvalidPositionException { 
		Nodo<E> n;
		if(p==null) {
			throw new InvalidPositionException("Posicion nula");
		}
		if(isEmpty()) {
			throw new InvalidPositionException("La lista esta vacia");
		}
		try {
		     n= (Nodo<E>) p;
		}
		catch( ClassCastException e ) { 
			throw new InvalidPositionException("Posicion invalida");
		}
		
		return n;
		}


}