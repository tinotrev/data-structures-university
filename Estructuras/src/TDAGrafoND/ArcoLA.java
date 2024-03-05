package TDAGrafoND;

import Interfaces.Edge;
import Interfaces.Position;

public class ArcoLA<V,E> implements Edge<E>{
	
	protected Position<ArcoLA<V,E>> posEnLista;
	protected E rotulo;
	protected Position<ArcoLA<V,E>> posEnLV1, posEnLV2;
	protected VerticeLA<V,E> v1, v2;
	
	public ArcoLA(E rot) {
		rotulo = rot;
	}
	
	//getters
	public Position<ArcoLA<V,E>> getPosL() {
		return posEnLista;
	}
	
	public Position<ArcoLA<V,E>> getPosV1() {
		return posEnLV1;
	}
	
	public Position<ArcoLA<V,E>> getPosV2() {
		return posEnLV2;
	}
	
	public VerticeLA<V,E> getV1() {
		return v1;
	}
	
	public VerticeLA<V,E> getV2() {
		return v2;
	}
	
	@Override
	public E element() {
		return rotulo;
	}

	//setters
	public void setPosL(Position<ArcoLA<V,E>> p) {
		posEnLista = p;
	}
	
	public void setPosV1(Position<ArcoLA<V,E>> p) {
		posEnLV1 = p;
	}
	
	public void setPosV2(Position<ArcoLA<V,E>> p) {
		posEnLV2 = p;
	}
	
	public void setV1(VerticeLA<V,E> v) {
		v1 = v;
	}
	
	public void setV2(VerticeLA<V,E> v) {
		v2 = v;
	}
	
	public void setRotulo(E rot) {
		rotulo = rot;
	}
}
