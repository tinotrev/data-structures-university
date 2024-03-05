package TDAGrafoND;

import Interfaces.Edge;
import Interfaces.Position;

public class ArcoM<V,E> implements Edge<E> {
	
	protected Position<ArcoM<V,E>> posEnLista;
	protected VerticeM<V> v1, v2;
	protected E rotulo;
	
	public ArcoM(E rot) {
		rotulo = rot;
	}
	
	//getters
	public Position<ArcoM<V,E>> getPosL() {
		return posEnLista;
	}
	
	public VerticeM<V> getV1() {
		return v1;
	}
	
	public VerticeM<V> getV2() {
		return v2;
	}
	
	@Override
	public E element() {
		return rotulo;
	}

	//setters
	public void setPosL(Position<ArcoM<V,E>> p) {
		posEnLista = p;
	}
	
	public void setV1(VerticeM<V> v) {
		v1 = v;
	}
	
	public void setV2(VerticeM<V> v) {
		v2 = v;
	}
	
	public void setRotulo(E e) {
		rotulo = e;
	}
}