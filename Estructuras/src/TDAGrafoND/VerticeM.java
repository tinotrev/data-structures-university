package TDAGrafoND;

import Interfaces.Position;
import Interfaces.Vertex;

public class VerticeM<V> implements Vertex<V> {
	
	protected V rotulo;
	protected int indice;
	protected Position<VerticeM<V>> posEnLista;

	public VerticeM(V rot) {
		rotulo = rot;
	}
	
	
	//getters
	public Position<VerticeM<V>> getPos() {
		return posEnLista;
	}
	
	public int getIndice() {
		return indice;
	}
	
	@Override
	public V element() {
		return rotulo;
	}
	
	//setters
	public void setPos(Position<VerticeM<V>> p) {
		posEnLista = p;
	}
	
	public void setRotulo(V v) {
		rotulo = v;
	}
	
	public void setIndice(int i) {
		indice = i;
	}
}