package TDAGrafoND;

import Exceptions.EmptyListException;
import Exceptions.InvalidEdgeException;
import Exceptions.InvalidPositionException;
import Exceptions.InvalidVertexException;
import Interfaces.Edge;
import Interfaces.Graph;
import Interfaces.PositionList;
import Interfaces.Vertex;
import TDALista.ListaDoblementeEnlazada;

public class GrafoM<V,E> implements Graph<V,E> {
	
	protected PositionList<VerticeM<V>> vertices;
	protected PositionList<ArcoM<V,E>> arcos;
	protected ArcoM<V,E>[][] matriz;
	public int cantVert;
	
	@SuppressWarnings("unchecked")
	public GrafoM(int n) {
		vertices = new ListaDoblementeEnlazada<>();
		arcos = new ListaDoblementeEnlazada<>();
		cantVert = 0;
		matriz = (ArcoM<V,E>[][]) new ArcoM[n][n];
	}
	
	@Override
	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> toRet = new ListaDoblementeEnlazada<>();
		for(VerticeM<V> v : vertices) {
			toRet.addLast(v);
		}
		return toRet;
	}

	@Override
	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> toRet = new ListaDoblementeEnlazada<>();
		for(ArcoM<V,E> a : arcos) {
			toRet.addLast(a);
		}
		return toRet;
	}

	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		PositionList<Edge<E>> toRet = new ListaDoblementeEnlazada<>();
		VerticeM<V> ver = checkVertex(v);
		int ind = ver.getIndice();
		for(int j = 0; j < cantVert; j++) {
			if(matriz[ind][j] != null) {
				toRet.addLast(matriz[ind][j]);
			}
		}
		return toRet;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		VerticeM<V> ver = checkVertex(v);
		ArcoM<V,E> arc = checkEdge(e);
		if(ver == arc.getV1())
			return arc.getV2();
		else {
			if(ver == arc.getV2())
				return arc.getV1();
			else
				throw new InvalidEdgeException("El arco y el vertice no están conectados");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		ArcoM<V,E> a = checkEdge(e);
		Vertex<V>[] toRet = (VerticeM<V>[]) new VerticeM[2];
		toRet[0] = a.getV1();
		toRet[1] = a.getV2();
		return toRet;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		VerticeM<V> vv = checkVertex(v);
		VerticeM<V> ww = checkVertex(w);
		if(matriz[vv.getIndice()][ww.getIndice()] != null && matriz[ww.getIndice()][vv.getIndice()] != null)
			return true;
		else
			return false;
	}

	@Override
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		VerticeM<V> ver = checkVertex(v);
		V toRet = ver.element();
		ver.setRotulo(x);
		return toRet;
	}

	@Override
	public Vertex<V> insertVertex(V x) {
		VerticeM<V> aInsertar = new VerticeM<>(x);
		aInsertar.setIndice(cantVert++);
		vertices.addLast(aInsertar);
		try {
			aInsertar.setPos(vertices.last());
		} catch(EmptyListException e) {
			System.out.println("Algo salio mal... no deberíamos haber llegado hasta aquí");
		}
		return aInsertar;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		VerticeM<V> v1 = checkVertex(v);
		VerticeM<V> v2 = checkVertex(w);
		ArcoM<V,E> aInsertar = new ArcoM<>(e);
		aInsertar.setV1(v1);
		aInsertar.setV2(v2);
		matriz[v1.getIndice()][v2.getIndice()] = matriz[v2.getIndice()][v1.getIndice()] = aInsertar;
		arcos.addLast(aInsertar);
		try {
			aInsertar.setPosL(arcos.last());
		} catch(EmptyListException ex) {
			System.out.println("Algo salio mal... no deberíamos haber llegado hasta aquí");
		}
		return aInsertar;
	}

	@Override
	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		VerticeM<V> ver = checkVertex(v);
		int ind = ver.getIndice();
		V toRet = v.element();
		try {
			//tenemos que eliminar todos los arcos incidentes de la matriz (anulamos la fila y columna correspondiente)
			for(int j = 0; j < cantVert; j++) {
				if(matriz[ind][j] != null) {
					arcos.remove(matriz[ind][j].getPosL());
					matriz[ind][j] = matriz[j][ind] = null;
				}
			}
			vertices.remove(ver.getPos()); //por último lo eliminamos de la lista de vértices del grafo
		} catch (InvalidPositionException e) {
			System.out.println("Algo salio mal...");
		} //
		return toRet;
	}

	@Override
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		ArcoM<V,E> arc = checkEdge(e);
		E toRet = arc.element();
		int i1 = arc.getV1().getIndice();
		int i2 = arc.getV2().getIndice();
		matriz[i1][i2] = matriz[i2][i1] = null;
		try {
			arcos.remove(arc.getPosL());
		} catch(InvalidPositionException ex) {
			System.out.println("Algo salio mal...");
		}
		return toRet;
	}
	
	private VerticeM<V> checkVertex(Vertex<V> v) throws InvalidVertexException {
		VerticeM<V> toRet = null;
		if(v == null)
			throw new InvalidVertexException("Vertex nulo");
		try {
			toRet = (VerticeM<V>) v;
		} catch(ClassCastException e) {
			throw new InvalidVertexException("No es de tipo vérticeM");
		}
		return toRet;
	}
	
	@SuppressWarnings("unchecked")
	private ArcoM<V,E> checkEdge(Edge<E> e) throws InvalidEdgeException {
		ArcoM<V,E> toRet = null;
		if(e == null)
			throw new InvalidEdgeException("Edge nulo");
		try {
			toRet = (ArcoM<V,E>) e;
		} catch(ClassCastException ex) {
			throw new InvalidEdgeException("No es de tipo ArcoM");
		}
		return toRet;
	}
}
