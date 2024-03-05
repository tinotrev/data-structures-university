package TDAABBúsqueda;

public class NodoABB<E> {
	
	protected E rotulo;
	protected NodoABB<E> padre;
	protected NodoABB<E> hijoIzq, hijoDer;
	
	//constructores
	public NodoABB(E rot, NodoABB<E> p) {
		rotulo = rot;
		padre = p;
	}
	
	public NodoABB(E rot) {
		rotulo = rot;
	}
	
	//getters
	public NodoABB<E> getPadre() {
		return padre;
	}
	
	public NodoABB<E> getIzq() {
		return hijoIzq;
	}
	
	public NodoABB<E> getDer() {
		return hijoDer;
	}
	
	public E getRotulo() {
		return rotulo;
	}
	
	//setters
	public void setPadre(NodoABB<E> n) {
		padre = n;
	}
	
	public void setIzq(NodoABB<E> n) {
		hijoIzq = n;
	}
	
	public void setDer(NodoABB<E> n) {
		hijoDer = n;
	}
	
	public void setRotulo(E e) {
		rotulo = e;
	}
}