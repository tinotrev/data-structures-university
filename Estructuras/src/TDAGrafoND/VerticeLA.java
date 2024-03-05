package TDAGrafoND;

import Interfaces.Position;
import Interfaces.PositionList;
import Interfaces.Vertex;
import TDALista.ListaDoblementeEnlazada;

public class VerticeLA<V,E> implements Vertex<V> {
	
	protected Position<VerticeLA<V,E>> posEnLista;
	protected V rotulo;
	protected PositionList<ArcoLA<V,E>> adyacentes;
	
	public VerticeLA(V rot) {
		rotulo = rot;
		adyacentes = new ListaDoblementeEnlazada<>();
	}
	
	//getters
	public Position<VerticeLA<V,E>> getPos() {
		return posEnLista;
	}
	
	public PositionList<ArcoLA<V,E>> getAdyacentes() {
		return adyacentes;
	}
	
	@Override
	public V element() {
		return rotulo;
	}
	
	//setters
	public void setPos(Position<VerticeLA<V,E>> p) {
		posEnLista = p;
	}
	
	public void setAdyacentes(PositionList<ArcoLA<V,E>> a) {
		adyacentes = a;
	}
	
	public void setRotulo(V v) {
		rotulo = v;
	}
}
