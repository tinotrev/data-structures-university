package TDAGrafoDecorado;

import Interfaces.EdgeDeco;
import Interfaces.Position;
import TDAMapeo.MapHashAbierto;

public class Arco<V,E> extends MapHashAbierto<Object, Object> implements EdgeDeco<E> {

	protected Position<Arco<V,E>> posEnLista;
	protected E rotulo;
	protected Position<Arco<V,E>> posEnLV1, posEnLV2;
	protected Vertice<V,E> v1, v2;
	
	public Arco(E rot) {
		rotulo = rot;
	}
	
	//getters
	public Position<Arco<V,E>> getPosL() {
		return posEnLista;
	}
	
	public Position<Arco<V,E>> getPosV1() {
		return posEnLV1;
	}
	
	public Position<Arco<V,E>> getPosV2() {
		return posEnLV2;
	}
	
	public Vertice<V,E> getV1() {
		return v1;
	}
	
	public Vertice<V,E> getV2() {
		return v2;
	}
	
	@Override
	public E element() {
		return rotulo;
	}

	//setters
	public void setPosL(Position<Arco<V,E>> p) {
		posEnLista = p;
	}
	
	public void setPosV1(Position<Arco<V,E>> p) {
		posEnLV1 = p;
	}
	
	public void setPosV2(Position<Arco<V,E>> p) {
		posEnLV2 = p;
	}
	
	public void setV1(Vertice<V,E> v) {
		v1 = v;
	}
	
	public void setV2(Vertice<V,E> v) {
		v2 = v;
	}
	
	public void setRotulo(E rot) {
		rotulo = rot;
	}
}
