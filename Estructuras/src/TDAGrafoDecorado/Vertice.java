package TDAGrafoDecorado;

import Interfaces.Position;
import Interfaces.PositionList;
import Interfaces.VertexDeco;
import TDALista.ListaDoblementeEnlazada;
import TDAMapeo.MapHashAbierto;

public class Vertice<V,E> extends MapHashAbierto<Object,Object> implements VertexDeco<V> {

	protected Position<Vertice<V,E>> posEnLista;
	protected V rotulo;
	protected PositionList<Arco<V,E>> adyacentes;
	
	public Vertice(V rot) {
		rotulo = rot;
		adyacentes = new ListaDoblementeEnlazada<>();
	}
	
	//getters
	public Position<Vertice<V,E>> getPos() {
		return posEnLista;
	}
	
	public PositionList<Arco<V,E>> getAdyacentes() {
		return adyacentes;
	}
	
	@Override
	public V element() {
		return rotulo;
	}
	
	//setters
	public void setPos(Position<Vertice<V,E>> p) {
		posEnLista = p;
	}
	
	public void setAdyacentes(PositionList<Arco<V,E>> a) {
		adyacentes = a;
	}
	
	public void setRotulo(V v) {
		rotulo = v;
	}
}