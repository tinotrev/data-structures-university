package TDAGrafoDecorado;

import java.util.Iterator;

import Exceptions.EmptyListException;
import Exceptions.InvalidEdgeException;
import Exceptions.InvalidPositionException;
import Exceptions.InvalidVertexException;
import Interfaces.EdgeDeco;
import Interfaces.GraphDeco;
import Interfaces.Position;
import Interfaces.PositionList;
import Interfaces.VertexDeco;
import TDALista.ListaDoblementeEnlazada;

public class GrafoND<V,E> implements GraphDeco<V,E> {
	
	protected PositionList<Arco<V,E>> arcos;
	protected PositionList<Vertice<V,E>> vertices;
	
	public GrafoND() {
		arcos = new ListaDoblementeEnlazada<>();
		vertices = new ListaDoblementeEnlazada<>();
	}
	
	@Override
	public Iterable<VertexDeco<V>> vertices() {
		PositionList<VertexDeco<V>> toRet = new ListaDoblementeEnlazada<>();
		for(Vertice<V,E> v : vertices) {
			toRet.addLast(v);
		}
		return toRet;
	}

	@Override
	public Iterable<EdgeDeco<E>> Edges() {
		PositionList<EdgeDeco<E>> toRet = new ListaDoblementeEnlazada<>();
		for(Arco<V,E> a : arcos) {
			toRet.addLast(a);
		}
		return toRet;
	}

	@Override
	public Iterable<EdgeDeco<E>> incidentEdges(VertexDeco<V> v) throws InvalidVertexException {
		PositionList<EdgeDeco<E>> toRet = new ListaDoblementeEnlazada<>();
		Vertice<V,E> vert = checkVertexDeco(v);
		for(Arco<V,E> a : vert.getAdyacentes()) {
			toRet.addLast(a);
		}
		return toRet;
	}

	@Override
	public VertexDeco<V> opposite(VertexDeco<V> v, EdgeDeco<E> e) throws InvalidVertexException, InvalidEdgeException {
		Vertice<V,E> vert = checkVertexDeco(v);
		Arco<V,E> arc = checkEdgeDeco(e);
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
	public VertexDeco<V>[] endvertices(EdgeDeco<E> e) throws InvalidEdgeException {
		Arco<V,E> arc = checkEdgeDeco(e);
		VertexDeco<V>[] toRet = (VertexDeco<V>[]) new Vertice[2];
		toRet[0] = arc.getV1();
		toRet[1] = arc.getV2();
		return toRet;
	}

	@Override
	public boolean areAdjacent(VertexDeco<V> v, VertexDeco<V> w) throws InvalidVertexException {
		Vertice<V,E> vv = checkVertexDeco(v);
		Vertice<V,E> ww = checkVertexDeco(w);
		Iterator<Arco<V,E>> it = vv.getAdyacentes().iterator();
		Arco<V,E> cursor = null;
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
	public V replace(VertexDeco<V> v, V x) throws InvalidVertexException {
		Vertice<V,E> ver = checkVertexDeco(v);
		V toRet = ver.element();
		ver.setRotulo(x);
		return toRet;
	}

	@Override
	public VertexDeco<V> insertVertex(V x) {
		Vertice<V,E> aInsertar = new Vertice<>(x);
		vertices.addLast(aInsertar);
		try {
			aInsertar.setPos(vertices.last());
		} catch (EmptyListException e) {
			System.out.println("Algo salio mal... no deberíamos haber llegado hasta aquí");
		}
		return aInsertar;
	}

	@Override
	public EdgeDeco<E> insertEdge(VertexDeco<V> v, VertexDeco<V> w, E e) throws InvalidVertexException {
		Vertice<V,E> vv = checkVertexDeco(v);
		Vertice<V,E> ww = checkVertexDeco(w);
		Arco<V,E> aInsertar = new Arco<>(e);
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
	public V removeVertex(VertexDeco<V> v) throws InvalidVertexException {
		Vertice<V,E> ver = checkVertexDeco(v);
		V toReturn = ver.element();
		//Tenemos que eliminar de cada arco adyacente al vértice, su posición en la lista de arcos del grafo, eliminarlo de la lista de adyacencia del vertice contrario
		try {
			for(Position<Arco<V,E>> a : ver.getAdyacentes().positions()) {
				arcos.remove(a); //eliminamos cada arco de la lista de arcos del grafo
				//ahora elimino al arco de la lista de adyacencia del vertice contrario
				VertexDeco<V> vtx = opposite(ver, a.element());
				Vertice<V,E> opp = checkVertexDeco(vtx);
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
	public E removeEdge(EdgeDeco<E> e) throws InvalidEdgeException {
		Arco<V,E> arc = checkEdgeDeco(e);
		Vertice<V,E> v1 = arc.getV1();
		Vertice<V,E> v2 = arc.getV2();
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
	private Vertice<V,E> checkVertexDeco(VertexDeco<V> v) throws InvalidVertexException {
		Vertice<V,E> toRet = null;
		if(v == null) {
			throw new InvalidVertexException("Vertice nulo");
		}
		try {
			toRet = (Vertice<V,E>) v;
		} catch(ClassCastException e) {
			throw new InvalidVertexException("No es de tipo arco");
		}
		return toRet;
	}
	
	@SuppressWarnings("unchecked")
	private Arco<V,E> checkEdgeDeco(EdgeDeco<E> a) throws InvalidEdgeException {
		Arco<V,E> toRet = null;
		if(a == null) {
			throw new InvalidEdgeException("Arco nulo");
		}
		try {
			toRet = (Arco<V,E>) a;
		} catch(ClassCastException e) {
			throw new InvalidEdgeException("No es de tipo arco");
		}
		return toRet;
	}
}
