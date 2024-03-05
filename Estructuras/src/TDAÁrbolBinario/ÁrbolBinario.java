package TDAÁrbolBinario;

import Interfaces.*;
import java.util.Iterator;
import Exceptions.*;
import TDALista.*;

public class ÁrbolBinario<E> implements BinaryTree<E> {
	
	BTNodo<E> root;
	int size;
	
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
		PositionList<E> pl = new ListaDoblementeEnlazada<>();
		if(size > 0)
			preOrden(root, pl);
		return pl.iterator();
	}
	
	//método auxiliar para iterator()
	private void preOrden(BTNodo<E> n, PositionList<E> pl) {
		pl.addLast(n.element()); //visito
		if(n.getLeft() != null) {
			preOrden(n.getLeft(), pl);
		}
		if(n.getRight() != null) {
			preOrden(n.getRight(), pl);
		}
			
	}
	
	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> pl = new ListaDoblementeEnlazada<>();
		if(size > 0)
			preOrdenPos(root, pl);
		return pl;
	}
	
	//método auxiliar para positions()
	private void preOrdenPos(BTNodo<E> n, PositionList<Position<E>> pl) {
		pl.addLast(n);
		if(n.getLeft() != null) {
			preOrdenPos(n.getRight(), pl);
		}
		if(n.getRight() != null) {
			preOrdenPos(n.getRight(), pl);
		}
	}
	
	@Override
	public E replace(Position<E> v, E e) throws InvalidPositionException {
		BTNodo<E> n = checkPosition(v);
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
		BTNodo<E> n = checkPosition(v);
		if(root == n)
			throw new BoundaryViolationException("árbol vacio");
		return n.getPadre();
	}
	
	@Override
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		BTNodo<E> n = checkPosition(v);
		PositionList<Position<E>> pl = new ListaDoblementeEnlazada<>();
		if(n.getLeft() != null)
			pl.addLast(n.getLeft());
		if(n.getRight() != null)
			pl.addLast(n.getRight());
		return pl;
	}
	
	@Override
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		BTNodo<E> n = checkPosition(v);
		return n.getRight() != null || n.getLeft() != null;
	}
	
	@Override
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		return !isInternal(v);
	}
	
	@Override
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		BTNodo<E> n = checkPosition(v);
		return n == root;
	}
	
	@Override
	public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTNodo<E> n = checkPosition(v);
		if(n.getLeft() == null)
			throw new BoundaryViolationException("No tiene hijo izquierdo");
		return n.getLeft();
	}
	
	@Override
	public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTNodo<E> n = checkPosition(v);
		if(n.getRight() == null)
			throw new BoundaryViolationException("No tiene hijo derecho");
		return n.getRight();
	}
	
	@Override
	public boolean hasLeft(Position<E> v) throws InvalidPositionException {
		BTNodo<E> n = checkPosition(v);
		return n.getLeft() != null;
	}
	
	@Override
	public boolean hasRight(Position<E> v) throws InvalidPositionException {
		BTNodo<E> n = checkPosition(v);
		return n.getRight() != null;
	}
	
	@Override
	public Position<E> createRoot(E r) throws InvalidOperationException {
		if(root != null) {
			throw new InvalidOperationException("Ya hay una raíz creada");
		}
		root = new BTNodo<>(r,null);
		size = 1;
		return root;
	}
	
	@Override
	public Position<E> addLeft(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		if(size == 0)
			throw new InvalidPositionException("árbol vacio");
		BTNodo<E> n = checkPosition(v);
		if(n.getLeft() != null)
			throw new InvalidOperationException("Ya tiene hijo izquierdo");
		n.setLeft(new BTNodo<E>(r,n));
		size++;
		return n.getLeft();
	}
	
	@Override
	public Position<E> addRight(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		if(size == 0)
			throw new InvalidPositionException("árbol vacio");
		BTNodo<E> n = checkPosition(v);
		if(n.getRight() != null)
			throw new InvalidOperationException("Ya tiene hijo derecho");
		n.setRight(new BTNodo<E>(r,n));
		size++;
		return n.getRight();
	}
	
	@Override
	public E remove(Position<E> v) throws InvalidOperationException, InvalidPositionException {
		if(size == 0)
			throw new InvalidPositionException("árbol vacio");
		BTNodo<E> n = checkPosition(v);
		boolean tieneIzq = n.getLeft() != null;
		boolean tieneDer = n.getRight() != null;
		if(tieneIzq && tieneDer)
			throw new InvalidOperationException("El nodo tiene dos hijos");
		E toRet = n.element();
		if(n == root) { //es raíz sin o máximo un hijo
			if(size == 1) {
				root = null;
				size = 0;
			}
			else { //la raíz tiene un solo hijo
				if(tieneIzq) { //el hijo único es izquierdo
					root = n.getLeft();
				}
				else {//el hijo único es derecho
					root = n.getRight();
				}
				root.setPadre(null);
				size--;
			}
		} else { //no es raíz
			BTNodo<E> pn = n.getPadre();
			if(!tieneIzq && !tieneDer) { //es externo y no es raíz
				if(pn.getLeft() == n)
					pn.setLeft(null);
				else {
					if(pn.getRight() == null)
						pn.setRight(null);
					else
						System.out.println("El padre del nodo a eliminar no lo tiene como hijo. ERROR");
				}
				size--;
			} else { //es interno y no es raíz (un solo hijo)
				if(tieneIzq) { //tiene un hijo izquierdo
					if(n == pn.getLeft()) { //n es hijo izquierdo de su padre
						pn.setLeft(n.getLeft());
					}
					else { //n es hijod erecho de su padre
						pn.setRight(n.getLeft());
					}
					n.getLeft().setPadre(pn);
				}
				else { //n tiene hijo derecho
					if(n == pn.getLeft()) { //n es el hijo izquierdo de su padre
						pn.setLeft(n.getRight());
					}
					else { //n es el hijo derecho de padre
						pn.setRight(n.getRight());
					}
					n.getRight().setPadre(pn);
				}
				size--;
			}
		}
		return toRet;
	}
	
	@Override
	public void attach(Position<E> r, BinaryTree<E> T1, BinaryTree<E> T2) throws InvalidPositionException {
		if(size == 0)
			throw new InvalidPositionException("árbol vacio");
		BTNodo<E> n = checkPosition(r);
		if(isInternal(r))
			throw new InvalidPositionException("El nodo es interno");
		try {
			if(!T1.isEmpty()) {
				BTNodo<E> n1 = checkPosition(T1.root());
				n.setLeft(n1);
				n1.setPadre(n);
			}
			if(!T2.isEmpty()) {
				BTNodo<E> n2 = checkPosition(T2.root());
				n.setRight(n2);
				n2.setPadre(n);
			}
		} catch(EmptyTreeException ex) {
			System.out.println("Algo salio mal... no deberíamos estar aquí");
		}
		size += (T1.size() + T2.size());
	}
	
	private BTNodo<E> checkPosition(Position<E> p) throws InvalidPositionException {
		BTNodo<E> toRet = null;
		if(p == null)
			throw new InvalidPositionException("La posición es nula");
		try {
			toRet = (BTNodo<E>) p;
		} catch(ClassCastException ex) {
			throw new InvalidPositionException("No es de tipo BTNodo");
		}
		return toRet;
	}
	
}
	//***************************************************************************************IMPLEMENTACION VIEJA***************************************************************************************//
	/*
	public ÁrbolBinario() {
		root = null;
		size = 0;
	}
	
	@SuppressWarnings("unused")
	public ÁrbolBinario(BTNodo<E> r) {
		BTNodo<E> root = r;
		size = 0;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
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
		E toRet = null;
		BTNodo<E> nodo = checkPosition(v);
		toRet = nodo.element();
		nodo.setElement(e);
		return toRet;
	}

	@Override
	public Position<E> root() throws EmptyTreeException {
		// TODO Auto-generated method stub
		return root;
	}

	@Override
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTNodo<E> toRet = checkPosition(v);
		if(toRet == null) {
			throw new BoundaryViolationException("El nodo es un árbol");
		}
		return toRet.getPadre();
	}

	@Override
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		PositionList<Position<E>> toRet = new ListaDoblementeEnlazada<Position<E>>();
		BTNodo<E> nodo = checkPosition(v);
		if(hasLeft(nodo)) {
			toRet.addFirst(nodo.getLeft());
		}
		if(hasRight(nodo)) {
			toRet.addLast(nodo.getRight());
		}
		return toRet;
	}

	@Override
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return hasLeft(v) || hasRight(v);
	}

	@Override
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return !isInternal(v);
	}

	@Override
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		BTNodo<E> nodo = checkPosition(v);
		return nodo == root;
	}

	@Override
	public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		Position<E> toRet = null;
		BTNodo<E> nodo = checkPosition(v);
		if(hasLeft(v))
			toRet = nodo.getLeft();
		else
			throw new BoundaryViolationException("No tiene izquierdo");
		return toRet;
	}

	@Override
	public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		Position <E> toRet = null;
		BTNodo<E> nodo = checkPosition(v);
		if(!hasRight(v))
			throw new BoundaryViolationException("No tiene hijo derecho.");
		toRet = nodo.getRight();
		return toRet;
	}

	@Override
	public boolean hasLeft(Position<E> v) throws InvalidPositionException {
		BTNodo<E> nodo = checkPosition(v);
		return nodo.getLeft() != null;
	}

	@Override
	public boolean hasRight(Position<E> v) throws InvalidPositionException {
		BTNodo<E> nodo = checkPosition(v);
		return nodo.getRight() != null;
	}

	@Override
	public Position<E> createRoot(E r) throws InvalidOperationException {
		if(root != null)
			throw new InvalidOperationException("El árbol ya tiene raíz");
		BTNodo<E> raiz = new BTNodo<E>(r,null);
		root = raiz;
		size = 1;
		return root;
	}

	@Override
	public Position<E> addLeft(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		BTNodo<E> toInsert = null;
		if(size == 0 || hasLeft(v)) {
			throw new InvalidOperationException("Árbol vacio o ya tiene un hijo izquiedo");
		}
		else {
			BTNodo<E> nodo = checkPosition(v);
			toInsert = new BTNodo<E>(r, nodo);
			nodo.setLeft(toInsert);
		}
		return toInsert;
	}

	@Override
	public Position<E> addRight(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		BTNodo<E> toInsert = null;
		if(size == 0 || hasRight(v))
			throw new InvalidOperationException("El árbol está vacio o ya tiene hijo derecho");
		else {
			BTNodo<E> nodo = checkPosition(v);
			toInsert = new BTNodo<E>(r, nodo);
			nodo.setRight(toInsert);
		}
		return toInsert;
	}

	@Override
	public E remove(Position<E> v) throws InvalidOperationException, InvalidPositionException {
		E toRet = null;
		BTNodo<E> toRemove = checkPosition(v);
		BTNodo<E> parentRemove = toRemove.getPadre();
		if(hasLeft(toRemove) && hasRight(toRemove))
			throw new InvalidOperationException("El nodo a eliminar tiene dos hijos");
		if(size == 0)
				throw new InvalidOperationException("El árbol está vacio");
		if(toRemove == root) {	
			if(size == 1) {
				root = null;
				size--;
			}
			if(hasRight(toRemove)) {
				root = toRemove.getRight();
				toRemove.getRight().setPadre(null);
			}
			else {
				root = toRemove.getLeft();
				toRemove.getLeft().setPadre(null);
			}
		}
		else {
			try {
				toRet = toRemove.element();
				if(hasLeft(toRemove)) { //si remove tiene un solo hijo izquierdo
					if(left(parentRemove) == toRemove) { //si remove es un hijo izquierdo
						parentRemove.setLeft(toRemove.getLeft());
						parentRemove.getLeft().setPadre(parentRemove);
					}
					else {//remove es un hijo derecho
						parentRemove.setRight(toRemove.getLeft());
						parentRemove.getRight().setPadre(parentRemove);
					}
				}
				else {
					if(hasRight(toRemove)) { //el hijo de remove es derecho 
						if(left(parentRemove) == toRemove) { //remove es hijo izquierdo
							parentRemove.setLeft(toRemove.getRight());
							parentRemove.getLeft().setPadre(parentRemove);
						}
						else { //remove es hijo derecho
							parentRemove.setRight(toRemove.getRight());
							parentRemove.getRight().setPadre(parentRemove);
						}
					}
					else { //remove es una hoja
						if(left(parentRemove) == toRemove) { //es hijo izquierdo
							parentRemove.setLeft(null);
						}
						else { //es hijo derecho
							parentRemove.setRight(null);
						}
					}
				}
				size--;
			} catch(BoundaryViolationException ex) {
				ex.getMessage();
			}
		}
		return toRet;
	}

	@SuppressWarnings("unused")
	@Override
	public void attach(Position<E> r, BinaryTree<E> T1, BinaryTree<E> T2) throws InvalidPositionException {
		BTNodo<E> nodo = checkPosition(r);
		if(isEmpty()) { throw new InvalidPositionException(""); }
		if(isInternal(r)) { throw new InvalidPositionException(""); }
		try {
		if(!T1.isEmpty()) {
			BTNodo<E> rootT1 = checkPosition(T1.root());
		}
		if(!T2.isEmpty()) {
			this.addLeft(r, T1.root().element());
		}
		} catch (EmptyTreeException | InvalidOperationException ex) {
			ex.printStackTrace();
		}
	}

	private BTNodo<E> checkPosition(Position<E> p) throws InvalidPositionException {
		BTNodo<E> toRet = null;
		if(p != null) {
			try {
				toRet = (BTNodo<E>) p;
			} catch (ClassCastException e) {
				throw new InvalidPositionException("La position no es BTNodo válido");
			}
		}
		else {
			throw new InvalidPositionException("La position es nula");
		}
		return toRet;
	}
	
	
	public void completarDerechos(E r) throws EmptyTreeException {
		try{	
			preOrden(r, this.root());
		} catch(InvalidPositionException | BoundaryViolationException | InvalidOperationException ex) {
			ex.getMessage();
		}
	}
	private void preOrden(E r, Position<E> pos) throws InvalidPositionException, BoundaryViolationException, InvalidOperationException {
		visitar(r, pos);
		if(hasLeft(pos)) {
			preOrden(r, left(pos));
		}
		if(hasRight(pos)) {
			preOrden(r, right(pos));
		}
	}
	
	@SuppressWarnings("unused")
	private void visitar(E r, Position<E> pos) throws InvalidPositionException, InvalidOperationException {
		BTNodo<E> toInsert = null;
		BTNodo<E> nodo = checkPosition(pos);
		if(hasLeft(pos)) {
			if(!hasRight(pos)) {
				this.addRight(pos, r);
			}
		}
	}
	*/
	//************************************************************************************FIN IMPLEMENTACION VIEJA**************************************************************************************//
	


	//******************************************************************************************CODIGO THIAGO******************************************************************************************//
	/*
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> list = new ListaDoblementeEnlazada<Position<E>>();
		if(!isEmpty())
			preOrden(root, list);
		return list;
	}
	private void preOrden(Position<E> v, PositionList<Position<E>> list) {
		list.addLast(v);
		try {
			if(hasLeft(v))
				preOrden(left(v),list);
			if(hasRight(v))
			    preOrden(right(v),list);
		} catch (InvalidPositionException | BoundaryViolationException e) {
			throw new RuntimeException(e);
		}
	*/
	//**************************************************************************************FIN CODIGO THIAGO**************************************************************************************//
