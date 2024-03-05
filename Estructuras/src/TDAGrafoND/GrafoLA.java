package TDAGrafoND;

import java.util.Iterator;

import Exceptions.EmptyListException;
import Exceptions.InvalidEdgeException;
import Exceptions.InvalidPositionException;
import Exceptions.InvalidVertexException;
import Interfaces.Edge;
import Interfaces.Graph;
import Interfaces.Position;
import Interfaces.PositionList;
import Interfaces.Vertex;
import TDALista.ListaDoblementeEnlazada;

public class GrafoLA<V,E> implements Graph<V,E> {
	
	protected PositionList<ArcoLA<V,E>> arcos;
	protected PositionList<VerticeLA<V,E>> vertices;
	
	public GrafoLA() {
		arcos = new ListaDoblementeEnlazada<>();
		vertices = new ListaDoblementeEnlazada<>();
	}
	
	@Override
	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> toRet = new ListaDoblementeEnlazada<>();
		for(VerticeLA<V,E> v : vertices) {
			toRet.addLast(v);
		}
		return toRet;
	}

	@Override
	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> toRet = new ListaDoblementeEnlazada<>();
		for(ArcoLA<V,E> a : arcos) {
			toRet.addLast(a);
		}
		return toRet;
	}

	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		PositionList<Edge<E>> toRet = new ListaDoblementeEnlazada<>();
		VerticeLA<V,E> vert = checkVertex(v);
		for(ArcoLA<V,E> a : vert.getAdyacentes()) {
			toRet.addLast(a);
		}
		return toRet;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		VerticeLA<V,E> vert = checkVertex(v);
		ArcoLA<V,E> arc = checkEdge(e);
		if(vert == arc.getV1()) {
			return arc.getV2();
		}
		else {
			if(vert == arc.getV2()) {
				return arc.getV1();
			}
			else
				throw new InvalidEdgeException("El arco no es incidente al vertice dado");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		ArcoLA<V,E> arc = checkEdge(e);
		Vertex<V>[] toRet = (Vertex<V>[]) new VerticeLA[2];
		toRet[0] = arc.getV1();
		toRet[1] = arc.getV2();
		return toRet;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		VerticeLA<V,E> vv = checkVertex(v);
		VerticeLA<V,E> ww = checkVertex(w);
		Iterator<ArcoLA<V,E>> it = vv.getAdyacentes().iterator();
		ArcoLA<V,E> cursor = null;
		boolean adjacent = false;;
		while(it.hasNext() && !adjacent) {
			try {
				cursor = it.next();
				adjacent = opposite(vv, cursor) == ww;
			} catch (InvalidEdgeException e) {
				throw new InvalidVertexException("Algo salió mal");
			} 
		}
		return adjacent;
	}

	@Override
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		VerticeLA<V,E> ver = checkVertex(v);
		V toRet = ver.element();
		ver.setRotulo(x);
		return toRet;
	}

	@Override
	public Vertex<V> insertVertex(V x) {
		VerticeLA<V,E> aInsertar = new VerticeLA<>(x);
		vertices.addLast(aInsertar);
		try {
			aInsertar.setPos(vertices.last());
		} catch (EmptyListException e) {
			System.out.println("Algo salio mal... no deberíamos haber llegado hasta aquí");
		}
		return aInsertar;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		VerticeLA<V,E> vv = checkVertex(v);
		VerticeLA<V,E> ww = checkVertex(w);
		ArcoLA<V,E> aInsertar = new ArcoLA<>(e);
		//seteo al arco los vertices correspondientes a los cuales va a incidir
		aInsertar.setV1(vv);
		aInsertar.setV2(ww);
		try {
			arcos.addLast(aInsertar); //agrego el arco a la lista de arcos del grafo
			aInsertar.setPosL(arcos.last()); //le pido a la lista de arcos del grafo la posición del arco a insertar y lo guardo en el atributo de instancia correspondiente en el propio arco
			//Ahora tengo que añadir el arco a la lista de adyacentes de los dos vertices
			vv.getAdyacentes().addLast(aInsertar);
			ww.getAdyacentes().addLast(aInsertar);
			//Ahora debo pedir de la lista de adyacentes de los vertices la posición del arco insertado y guardarlas en los atributos del propio arco
			aInsertar.setPosV1(vv.getAdyacentes().last());
			aInsertar.setPosV2(ww.getAdyacentes().last());
		} catch(EmptyListException ex) {
			System.out.println("Algo salio mal... no deberíamos haber llegado hasta aquí");
		}
		return aInsertar;
	}

	@Override
	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		VerticeLA<V,E> ver = checkVertex(v);
		V toReturn = ver.element();
		//Tenemos que eliminar de cada arco adyacente al vértice, su posición en la lista de arcos del grafo, eliminarlo de la lista de adyacencia del vertice contrario
		try {
			for(Position<ArcoLA<V,E>> a : ver.getAdyacentes().positions()) {
				arcos.remove(a); //eliminamos cada arco de la lista de arcos del grafo
				//ahora elimino al arco de la lista de adyacencia del vertice contrario
				Vertex<V> vtx = opposite(ver, a.element());
				VerticeLA<V,E> opp = checkVertex(vtx);
				opp.getAdyacentes().remove(a);
			}
			vertices.remove(ver.getPos());
			return toReturn;
		} catch(InvalidPositionException | InvalidEdgeException ex) {
			System.out.println("Algo salio mal...");
		}
		return null; //si el vértice no se pudo borrar correctamente no devolvemos nada
	}

	@Override
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		ArcoLA<V,E> arc = checkEdge(e);
		VerticeLA<V,E> v1 = arc.getV1();
		VerticeLA<V,E> v2 = arc.getV2();
		E toRet = arc.element();
		try {
			//ahora elimino al arco de las dos listas de adyacencias
			v1.getAdyacentes().remove(arc.getPosV1());
			v2.getAdyacentes().remove(arc.getPosV2());
			//elimino también el arco de la lista de arcos del grafo
			arcos.remove(arc.getPosL());
			return toRet;
		} catch(InvalidPositionException ex) {
			System.out.println("Algo salio mal...");
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private VerticeLA<V,E> checkVertex(Vertex<V> v) throws InvalidVertexException {
		VerticeLA<V,E> toRet = null;
		if(v == null) {
			throw new InvalidVertexException("Vertice nulo");
		}
		try {
			toRet = (VerticeLA<V,E>) v;
		} catch(ClassCastException e) {
			throw new InvalidVertexException("No es de tipo arco");
		}
		return toRet;
	}
	
	@SuppressWarnings("unchecked")
	private ArcoLA<V,E> checkEdge(Edge<E> a) throws InvalidEdgeException {
		ArcoLA<V,E> toRet = null;
		if(a == null) {
			throw new InvalidEdgeException("Arco nulo");
		}
		try {
			toRet = (ArcoLA<V,E>) a;
		} catch(ClassCastException e) {
			throw new InvalidEdgeException("No es de tipo arco");
		}
		return toRet;
	}
}