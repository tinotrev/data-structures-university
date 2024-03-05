package TDAArbol;

import java.util.Iterator;
import TDALista.*;
import TDAMapeo.*;
import Exceptions.*;
import Interfaces.*;

public class Árbol<E> implements Tree<E> {
	protected TNodo<E> root;
	protected int size;
	
	public Árbol() {
		size = 0;
		root = null;
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
	public Iterator<E> iterator() {
		PositionList<E> lista = new ListaDoblementeEnlazada<>();
		if(size > 0)
			preOrden(root, lista);
		return lista.iterator();
	}
	
	//metodo auxiliar para iterator()
	private void preOrden(TNodo<E> n, PositionList<E> pl) {
		pl.addLast(n.element()); //visito
		for(TNodo<E> hn : n.getHijos()) {
			preOrden(hn, pl);
		}
	}
	
	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> toRet = new ListaDoblementeEnlazada<>();
		if(size > 0)
			preOrdenPos(root, toRet);
		return toRet;
	}
	
	//metodo auxiliar para positions()
	private void preOrdenPos(TNodo<E> n, PositionList<Position<E>> pl) {
		pl.addLast(n); //visito
		for(TNodo<E> hn : n.getHijos()) {
			preOrdenPos(hn, pl);
		}
	}
	
	@Override
	public E replace(Position<E> v, E e) throws InvalidPositionException {
		TNodo<E> n = checkPosition(v);
		E toRet = n.element();
		n.setElement(e);
		return toRet;
	}

	@Override
	public Position<E> root() throws EmptyTreeException {
		if(size == 0)
			throw new EmptyTreeException("árbol vacio");
		return root;
	}

	@Override
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		TNodo<E> n = checkPosition(v);
		if(n == root)
			throw new BoundaryViolationException("El nodo es la raíz, no tiene padre");
		return n.getPadre();
	}

	@Override
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		TNodo<E> n = checkPosition(v);
		PositionList<Position<E>> toRet = new ListaDoblementeEnlazada<>();
		for(TNodo<E> nn : n.getHijos()) {
			toRet.addLast(nn);
		}
		return toRet;
	}

	@Override
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		TNodo<E> n = checkPosition(v);
		return !n.getHijos().isEmpty();
	}

	@Override
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		TNodo<E> n = checkPosition(v);
		return n.getHijos().isEmpty();
	}

	@Override
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		TNodo<E> n = checkPosition(v);
		return n == root;
	}

	@Override
	public void createRoot(E e) throws InvalidOperationException {
		if(root != null)
			throw new InvalidOperationException("Ya hay raíz");
		root = new TNodo<>(e);
		size++;
	}

	@Override
	public Position<E> addFirstChild(Position<E> p, E e) throws InvalidPositionException {
		if(isEmpty())
			throw new InvalidPositionException("El árbol está vacío.");
		TNodo<E> n = checkPosition(p);
		TNodo<E> aInsertar = new TNodo<>(e);
		n.getHijos().addFirst(aInsertar);
		aInsertar.setPadre(n);
		size++; //por último incrementamos size
		return aInsertar;
	}

	@Override
	public Position<E> addLastChild(Position<E> p, E e) throws InvalidPositionException {
		if(isEmpty())
			throw new InvalidPositionException("El árbol está vacío.");
		TNodo<E> n = checkPosition(p);
		TNodo<E> aInsertar = new TNodo<>(e);
		n.getHijos().addLast(aInsertar);
		aInsertar.setPadre(n);
		size++; //por último incrementamos size
		return aInsertar;
	}

	@Override
	public Position<E> addBefore(Position<E> p, Position<E> rb, E e) throws InvalidPositionException {
		if(isEmpty())
			throw new InvalidPositionException("El árbol está vacío.");
		TNodo<E> n = checkPosition(p);
		TNodo<E> hn = checkPosition(rb);
		TNodo<E> aInsertar = new TNodo<>(e);
		Position<TNodo<E>> pos = buscarPos(n, hn); //Para insertarlo en la lista de hijos del nodo "n" justo antes del nodo "hn" necesitamos buscar la posición de "hn" en la lista de hijos de "n" que deberia ser su padre
		n.getHijos().addBefore(pos, aInsertar); //una vez encontrado el nodo lo insertamos
		aInsertar.setPadre(n); //seteamos el padre al nodo insertado
		size++; //por último incrementamos size
		return aInsertar;
	}
	
	//método auxiliar para addBefore(), addAfter() y removeExternalNode()
	private Position<TNodo<E>> buscarPos(TNodo<E> n, TNodo<E> hn) {
		for(Position<TNodo<E>> act : n.getHijos().positions()) {
			if(act.element() == hn)
				return act;
		}
		return null;
	}
	
	@Override
	public Position<E> addAfter(Position<E> p, Position<E> lb, E e) throws InvalidPositionException {
		if(isEmpty())
			throw new InvalidPositionException("El árbol está vacío.");
		TNodo<E> n = checkPosition(p);
		TNodo<E> hn = checkPosition(lb);
		TNodo<E> aInsertar = new TNodo<>(e);
		Position<TNodo<E>> pos = buscarPos(n, hn);
		n.getHijos().addAfter(pos, aInsertar);
		aInsertar.setPadre(n);
		size++; //por último incrementamos size
		return aInsertar;
	}

	@Override
	public void removeExternalNode(Position<E> p) throws InvalidPositionException {
		TNodo<E> n = checkPosition(p);
		if(size == 0)
			throw new InvalidPositionException("El árbol está vacio");
		if(isInternal(n))
			throw new InvalidPositionException("El nodo es interno");
		if(size == 1) { //si size es 1 la única opción es que el nodo sea la raíz entonces la eliminación es distinta
				root = null;
				size--;
		}
		else { //si no es la raíz el procedimiento para eliminar es este
			//como es externo solo tengo que eliminarlo de la lista de hijos del padre
			TNodo<E> pn = n.getPadre();
			Position<TNodo<E>> pos = buscarPos(pn, n);
			pn.getHijos().remove(pos);
			size--;
		}
	}

	@Override
	public void removeInternalNode(Position<E> p) throws InvalidPositionException {
		TNodo<E> n = checkPosition(p);
		if(size == 0)
			throw new InvalidPositionException("El árbol está vacio");
		if(isExternal(n))
			throw new InvalidPositionException("El nodo es externo");
		if(n == root) {
			if(root.getHijos().size() < 2) { //si es verdadero quiere decir que la raíz tiene exclusivamente un solo hijo
				try {
					TNodo<E> nuevaRaiz = root.getHijos().first().element();
					nuevaRaiz.setPadre(null);
					root = nuevaRaiz;
					size--;
				} catch (EmptyListException e) {
					System.out.println("Algo salió mal... No deberíamos de poder llegar hasta aquí");
				}
			}
			else { //si el nodo es la raíz pero tiene más de un hijo no se puede seguir
					throw new InvalidPositionException("El nodo a eliminar es la raíz pero tiene más de un hijo, no se puede eliminar");
			}
		}
		else { //el nodo a eliminar no es una raíz
			//tenemos que añadir a la lista de hijos del padre del nodo a eliminar todos sus hijos y setearle a esos hijos el nuevo padre
			TNodo<E> pn = n.getPadre();
			Position<TNodo<E>> pos = buscarPos(pn, n); //No sin antes buscar la posición del nodo a eliminar en la lista de hijos de su padre
			for(TNodo<E> nn : n.getHijos()) {
				pn.getHijos().addBefore(pos, nn);
				nn.setPadre(pn);
			}
			pn.getHijos().remove(pos); //por último elimanos el nodo de la lista de hijos del padre
			size--;
		}
	}

	@Override
	public void removeNode(Position<E> p) throws InvalidPositionException {
		if(isExternal(p))
			removeExternalNode(p);
		else
			removeInternalNode(p);
	}
	
	//sirve para chequear que el objeto de tipo Position sea un TNodo
	private TNodo<E> checkPosition(Position<E> v) throws InvalidPositionException {
		if(v == null)
			throw new InvalidPositionException("Posición inválida");
		try {
			TNodo<E> nodo = (TNodo<E>) v;
			return nodo;
		}
		catch(ClassCastException e) {
			throw new InvalidPositionException("La posición no es un TNodo");
		}
	}
	
	//***********************************EJERCICIO 2 TP6*************************************//
	public void eliminarUltimoHijo(Position<E> p) throws InvalidOperationException, InvalidPositionException, EmptyListException {
		TNodo<E> nodo = null;
		TNodo<E> nodoP = null;
		PositionList<TNodo<E>> hijosNodoP = null;
		if(this.isRoot(p)) {
			throw new InvalidOperationException("La raíz no es hijo de nadie");
		}
		else {
			nodo = checkPosition(p);
			nodoP = nodo.getPadre();
			hijosNodoP = nodoP.getHijos();
			if(hijosNodoP.last().equals(p)) {
				if(this.isInternal(p)) {
					this.removeInternalNode(p);
				}
				else {
					this.removeExternalNode(p);
				}
			}
		}
	}
	//**************************************************************************************//
	
	//***********************************EJERCICIO 3 TP6*************************************//
	public static Map<Character, Integer> cantidadRepiticiones(Tree<Character> T) throws InvalidKeyException {
		Map<Character, Integer> toRet = new MapHashAbierto<Character, Integer>();
		Iterable<Position<Character>> arbRec = T.positions();
		Integer valorViejo;
		for(Position<Character> pos : arbRec) {
			valorViejo = toRet.get(pos.element());
			if (valorViejo != null) {
				toRet.put(pos.element(), ++valorViejo);
			}
			else {
				toRet.put(pos.element(), 1);
			}
		}
		return toRet;
	}
	//**************************************************************************************//
}	
	
	//***************************************************************************************IMPLEMENTACION VIEJA***************************************************************************************//
	/*
	public Árbol() {
		root = null;
		size = 0;
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
	//Construyo un iterador de rótulos de árboles a partir de posiciones guardandolos en una lista
	public Iterator<E> iterator() {
		PositionList<E> lista = new ListaDoblementeEnlazada<E>();
		for(Position<E> p : positions())
			lista.addLast(p.element());
		return lista.iterator();
	}
	
	/*
	@Override
	public Iterable<Position<E>> positions() {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	/*
	@Override
	public E replace(Position<E> v, E e) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(v);
		E toReturn = nodo.element();
		nodo.setElement(e);
		return toReturn;
	}
	
	@Override
	public Position<E> root() throws EmptyTreeException {
		if(isEmpty()) {
			throw new EmptyTreeException("El árbol está vacio");
		}
		return root;
		
	}
	
	@Override
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		TNodo<E> nodo = checkPosition(v);
		if(nodo == root)
			throw new InvalidPositionException("El nodo es una raíz, no tiene padre");
		return nodo;
	}
	
	@Override
	//Como no podemos devolver la lista de hijos directamente este método consiste en crear una lista auxiliar, copiar los hijos del nodo en esa lista y devolverla
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(v);
		PositionList<Position<E>> lista = new ListaDoblementeEnlazada<Position<E>>();
		for(TNodo<E> n : nodo.getHijos())
			lista.addLast(n);
		return lista;
	}
	
	@Override
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(v);
		return !nodo.getHijos().isEmpty();
	}
	
	@Override
	//Si es hoja es externo, es hoja si no tiene hijos
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(v);
		return nodo.getHijos().isEmpty();
	}
	
	@Override
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(v);
		return nodo == root;
	}
	
	@Override
	public void createRoot(E e) throws InvalidOperationException {
		if(root != null)
			throw new InvalidOperationException("El árbol ya posee raíz");
		root = new TNodo<E>(e);
		size = 1;
	}
	
	@Override
	public Position<E> addFirstChild(Position<E> p, E e) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(p);
		TNodo<E> nuevo = new TNodo<E>(e, nodo);
		nodo.getHijos().addFirst(nuevo);
		size++;
		return nuevo;
	}
	
	@Override
	public Position<E> addLastChild(Position<E> p, E e) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(p);
		TNodo<E> nuevo = new TNodo<E>(e, nodo);
		nodo.getHijos().addLast(nuevo);
		size++;
		return nuevo;
	}
	
	@Override
	//Agregar nodo nuevo con elemento e a la izquierda del nodoh que tiene padre p
	public Position<E> addBefore(Position<E> p, Position<E> rb, E e) throws InvalidPositionException {
		//primero chequeo que el árbol no está vacio
		if(isEmpty())
			throw new InvalidPositionException("Él árbol está vacío");
		//los siguientes 3 checkposition podrían lanzar una excepción la cual no controlo y delego a quien vaya a usar el método
		TNodo<E> nodoh = checkPosition(rb);
		TNodo<E> nodop = checkPosition(p);
		TNodo<E> nuevo = new TNodo<E>(e, nodop);
		//Obtener la lista de todos los hijos de nodop
		PositionList<TNodo<E>> hijos = nodop.getHijos();
		//Me preparo esta variable para más tarde usarla para recorrer la lista de hijos de nodop
		Interfaces.Position<TNodo<E>> rl = null; //Acá no entiendo bien como funciona el llamado a Interfaces
		boolean encontre = false;
		//bucle para buscar a nodoh en la lista de hijos de nodop
		try {
			rl = hijos.first(); // Intento obtener el primer hijo de nodop, podría no tener hijos y la lista estaría vacía por lo que lanzo una excepción
			//Procedo a recorrer la lista usando los métodos propios de ListaDoblementeEnlazada
			while(rl != null && !encontre) {
				if(nodoh == rl.element()) {
					encontre = true;
				}
				else {
					if(rl != hijos.last())
						rl = hijos.next(rl);
					else
						rl = null;
				}	
			}
		} catch (EmptyListException | BoundaryViolationException ex) {
			System.out.println(ex.getMessage());
		}
		if(!encontre)
			throw new InvalidPositionException("p no es padre de rb");
		hijos.addBefore(rl, nuevo);
		size++;
		return nuevo;
	}
	
	@Override
	//agregar nodo nuevo con elemento a la derecha del nodoh con padre nodop
	public Position<E> addAfter(Position<E> p, Position<E> lb, E e) throws InvalidPositionException {
		//primero chequeo que el árbol no está vacio
		if(isEmpty())
			throw new InvalidPositionException("Él árbol está vacío");
		//los siguientes 3 checkposition podrían lanzar una excepción la cual no controlo y delego a quien vaya a usar el método
		TNodo<E> nodoh = checkPosition(lb);
		TNodo<E> nodop = checkPosition(p);
		TNodo<E> nuevo = new TNodo<E>(e, nodop);
		//obtener la lista de todos los hijos de nodop
		PositionList<TNodo<E>> hijos = nodop.getHijos();
		//me preparo esta variable para más tarde usarla para recorrer la lista de hijos de nodop
		Interfaces.Position<TNodo<E>> rl = null; //Acá no entiendo bien como funciona el llamado a Interfaces
		boolean encontre = false;
		//bucle para buscar a nodoh en la lista de hijos de nodop
		try {
			rl = hijos.first(); //Intento obtener el primer hijo de nodop, podría no tener hijos y ser una lista vacía por lo cual me preparo para lanzar una excepción
			//Procedo a recorrer la lista usando los métodos propios de ListDoblementeEnlazada
			while(rl != null && !encontre) {
				if(rl == nodoh)
					encontre = true;
				else
					if(rl != hijos.last())
						rl = hijos.next(rl);
					else
						rl = null;
			}
		} catch(EmptyListException | BoundaryViolationException ex) {
			System.out.println(ex.getMessage());
		}
		if(!encontre)
			throw new InvalidPositionException("p no es padre de lb");
		hijos.addAfter(rl, nuevo);
		size++;
		return nuevo;
	}
	
	@Override
	public void removeExternalNode(Position<E> p) throws InvalidPositionException { //contemplo también si 
		if(isEmpty())
			throw new InvalidPositionException("El árbol esta vacío");
		TNodo<E> nodo = checkPosition(p);
		if(!nodo.getHijos().isEmpty())
			throw new InvalidPositionException("El nodo no es externo (hoja)");
		//guardo en una variable al padre del nodo
		TNodo<E> nodop = nodo.getPadre();
		//guardo en una variable a la lista de hijos del padre del nodo
		PositionList<TNodo<E>> hijosNodop = nodop.getHijos();
		//tenemos que buscar al nodo en la lista de hijos de su padre, por lo que, creo una variable que más tarde me va a ayudar a recorrer la lista
		Interfaces.Position<TNodo<E>> rl = null;
		//guardo en una variable la positions de la lista de hijos del padre del nodo
		Iterable<Interfaces.Position<TNodo<E>>> posiciones = hijosNodop.positions();
		//invoco un iterador para recorrer esa lista
		Iterator<Interfaces.Position<TNodo<E>>> it = posiciones.iterator();
		boolean encontre = false;
		//procedo a recorrer la lista usando los métodos iterables
		while(it.hasNext() && !encontre) {
			rl = it.next();
			if (rl.element() == nodo) {
				encontre = true;
			}
		}
		if(!encontre) //algo falló
			throw new InvalidPositionException("nodo no está en la lista de hijos de su padre. NO PUDE ELIMINAR");
		hijosNodop.remove(rl);
		nodo.setElement(null); //anulo el elemento del nodo que elimine para facilitar el trabajo en un futuro a checkPosition
		size--;
		if(isEmpty()) //si el árbol quedo vacio anulo la raiz 
			root = null;
	}
	
	@Override
	public void removeInternalNode(Position<E> p) throws InvalidPositionException {
		if(isEmpty())
			throw new InvalidPositionException("El árbol esta vacío");
		TNodo<E> nodo = checkPosition(p);
		try {
			if(nodo.getHijos().isEmpty()) {
				removeExternalNode(nodo);
				System.out.println("El nodo es una hoja (o una raíz sin hijos), controlo la situación e igualmente lo borro");
			}
			else {
				if(nodo == root) {
					if(nodo.getHijos().size() == 1) {
						Position<TNodo<E>> nuevaRaíz = nodo.getHijos().first();
						root = nuevaRaíz.element();
						root.setPadre(null);
						size--;
					}
					else {
						throw new InvalidPositionException("El nodo es la raíz del árbol y tiene más de un hijo. ERROR");
					}
				}
				else {
					TNodo<E> nodop = nodo.getPadre();
					PositionList<TNodo<E>> hijosNodo = nodo.getHijos();
					PositionList<TNodo<E>> hijosPadre = nodop.getHijos(); //hermanos del nodo a eliminar
					Position<TNodo<E>> recHijoPadre = hijosPadre.first();
					//busco el nodo a eliminar en la lista de hijos del padre
					while(recHijoPadre.element() != nodo)
						recHijoPadre = hijosPadre.next(recHijoPadre);
					//por cada hijo que tenga el nodo a eliminar lo agrego uno por uno a la lista de hijos del padre (a la izquierda)
					while(!hijosNodo.isEmpty()) {
						//obtengo el hijo a ingresar
						Position<TNodo<E>> hijoAInsertar = hijosNodo.first();
						//lo agrego a la izquierda de la lista de hijos del padre del nodo a eliminar
						hijosPadre.addBefore(hijoAInsertar, hijoAInsertar.element());
						//configuro al nuevo padre
						hijoAInsertar.element().setPadre(nodop);
						//elimino el hijo del nodo a eliminar
						hijosNodo.remove(hijoAInsertar);
					}
					//borro el nodo a eliminar de la lista de hijos del padre
					hijosPadre.remove(recHijoPadre);
					size--;
				}
			}
		} catch(EmptyListException | BoundaryViolationException  | InvalidPositionException ex) {
			throw new InvalidPositionException("La posición no es válida (algo no anduvo bien)");
		}
	}
	
	@Override
	//Este método no lo hice yo, lo copie de Thiago, no lo uso porque yo implemento removeExternalNode y removeInternalNode por separado teniendo diferentes consideraciones
	public void removeNode(Position<E> p) throws InvalidPositionException {
		if(size == 0) 
	       throw new InvalidPositionException("El árbol está vacío.");
		TNodo<E> toRemove = checkPosition(p);
		if(isInternal(toRemove)) {
			removeInternalNode(toRemove);
		}
		else {
			removeExternalNode(toRemove);
		}
	}
	*/
	//************************************************************************************FIN IMPLEMENTACION VIEJA**************************************************************************************//
	


	//******************************************************************************************CODIGO THIAGO******************************************************************************************//
	/*
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> list = new ListaDoblementeEnlazada<Position<E>>();
		if(size > 0)
			recPreOrdenPos(root,list);
		return list;
	}
	private void recPreOrdenPos(TNodo<E> r, PositionList<Position<E>> l) {
		l.addLast(r);
		for(TNodo<E> p : r.getHijos())
			recPreOrdenPos(p,l);
	}
	*/
	
	//Este método no lo hice yo, lo copie de Thiago, no lo uso porque yo implemento removeExternalNode y removeInternalNode por separado teniendo diferentes consideraciones
	/*
	public void removeNode(Position<E> p) throws InvalidPositionException {
		if(size == 0) 
            throw new InvalidPositionException("El árbol está vacío.");
     TNodo<E> toRemove = checkPosition(p);
     try {
    	 if(toRemove == root) {
    		 if(toRemove.getHijos().size() == 1) {
    			 Position<TNodo<E>> nuevaRaiz = toRemove.getHijos().first();
    			 root = nuevaRaiz.element();
    			 root.setPadre(null);
    			 size--;
    		 }
    		 else 
    			  if(size == 1) {
    				  root = null;
    				  size--;
    			  }
    			  else
    				  throw new InvalidPositionException("No se puede eliminar el nodo porque no se puede decidir que nodoHijo será la raíz."); 
    	 }
    	 else {
    		 TNodo<E> padre = toRemove.getPadre();
    		 PositionList<TNodo<E>> hijosDeR = toRemove.getHijos();
    		 PositionList<TNodo<E>> hijosDelPadre = padre.getHijos();
    		 Position<TNodo<E>> primero = hijosDelPadre.first();
    		 while(primero.element() != toRemove) {
    			 primero = hijosDelPadre.next(primero);
    	 	 }
    		 while(!hijosDeR.isEmpty()) {
    			 Position<TNodo<E>> hijoAInsertar = hijosDeR.first();
    			 hijosDelPadre.addBefore(primero, hijoAInsertar.element());
    			 hijoAInsertar.element().setPadre(padre);
    			 hijosDeR.remove(hijoAInsertar);
    		 }
    		 hijosDelPadre.remove(primero);
    		 size--;
    	 }
	*/
	//****************************************************************************************FIN CODIGO THIAGO****************************************************************************************//	