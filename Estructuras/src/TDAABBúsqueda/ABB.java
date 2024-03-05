package TDAABBúsqueda;

import java.util.Comparator;

//Árbol binario de búsqueda
public class ABB<E extends Comparable<E>> {

		protected NodoABB<E> raiz;
		protected Comparator<E> comp;
		
		//constructor
		public ABB(Comparator <E> c) {
			raiz = new NodoABB<>(null); //nodo dummy
			comp = c;
		}
		
		//getter
		public NodoABB<E> getRaiz() {
			return raiz;
		}
		
		//búsqueda de nodo con rótulo "e" recursivamente desde la raíz
		public NodoABB<E> buscar(E e) {
			return buscarAux(e, raiz);
		}
		
		//método auxiliar para buscar();
		private NodoABB<E> buscarAux(E e, NodoABB<E> n) {
			if(n.getRotulo() == null) { //llegue a un dummy
				return n; 
			}
			else { //no estoy en un dummy
				int c = comp.compare(e, n.getRotulo());
				if(c == 0) { //encontré
					return n;
				}
				else {
					if(c < 0) { //si el elemento es menor que el rotulo busco a izquierda
						return buscarAux(e, n.getIzq());
					}
					else { //si el elemento es mayor que el rotulo busco a derecha
						return buscarAux(e, n.getDer());
					}
				}
			}
		}
		
		//expando el árbol creando dos hijos dummies al nodo "n" 
		//no controla que el nodo ya tengo hijos no dummies creados
		public void expandir(NodoABB<E> n) {
			n.setIzq(new NodoABB<>(null));
			n.setDer(new NodoABB<>(null));
		}
		
		//eliminación física: busco el dato y elimino el nodo del árbol
		public void eliminar(NodoABB<E> n) {
			if(isExternal(n)) { //caso 1: el nodo es una hoja
				n.setRotulo(null); //convierto al nodo en un dummy
				n.setIzq(null); n.setDer(null); //suelto a sus hijos dummy
			}
			else { // el nodo no es hoja
				if(soloTieneHijoIzq(n)) { //caso 2: el nodo solo tiene un hijo izquierdo
					NodoABB<E> padreN = n.getPadre();
					NodoABB<E> hijoN = n.getIzq();
					hijoN.setPadre(padreN);
					if(n == padreN.getIzq()) { //el nodo a eliminar es hijo izquierdo de su padre
						padreN.setIzq(hijoN);
					}
					else { //el nodo a eliminar es hijo derecho de su padre
						padreN.setDer(hijoN);
					}
				}
				else {
					if(soloTieneHijoDer(n)) { //caso 3: el nodo solo tiene un hijo derecho
						NodoABB<E> padreN = n.getPadre();
						NodoABB<E> hijoN = n.getDer();
						hijoN.setPadre(padreN);
						if(n == padreN.getIzq()) { //el nodo a eliminar es hijo izquierdo de su padre
							padreN.setIzq(hijoN);
						}
						else { //el nodo a eliminar es hijo derecho de su padre
							padreN.setDer(hijoN);
						}
					}
					else { //caso 4: el nodo tiene dos hijos
						n.setRotulo(eliminarMinimo(n.getDer())); //seteo como rótulo del nodo al rótulo del sucesor inorder del nodo
					}
				}
			}
		}
		
		//método auxiliar para eliminar()
		private boolean isExternal(NodoABB<E> n) {
			return (n.getIzq().getRotulo() == null && n.getDer().getRotulo() == null);
		}
		
		//método auxiliar para eliminar()
		private boolean soloTieneHijoIzq(NodoABB<E> n) {
			return (n.getIzq() != null && n.getDer() == null);
		}
		
		//método auxiliar para eliminar()
		private boolean soloTieneHijoDer(NodoABB<E> n) {
			return (n.getDer() != null && n.getIzq() == null);
		}
		
		//método auxiliar para eliminar()
		//El minimo rotulo del subárbol que tiene como raíz al nodo "n" es el rótulo del primer nodo que encuentro yendo a la izquierda que no tiene hijo izquierdo
		private E eliminarMinimo(NodoABB<E> n) {
			E toReturn = null;
			NodoABB<E> padreN = n.getPadre();
			NodoABB<E> izqN = n.getIzq();
			NodoABB<E> derN = n.getDer();
			if(izqN.getRotulo() == null) { //el hijo izquierdo del nodo "n" es un dummy, encontre el mínimo
				toReturn = n.getRotulo(); //guardo el rotulo
				if(derN.getRotulo() == null) { //el nodo "n" es una hoja entonces lo convierto en dummy
					n.setRotulo(null);
					n.setIzq(null);
					n.setDer(null);
					//el método eliminar() podría encargarse del trabajo ejecutando el caso 1
				}
				else { //el nodo "n" tiene un solo hijo derecho		
					derN.setPadre(padreN);
					if(n == padreN.getIzq()) { //el nodo a eliminar es hijo izquierdo de su padre
						padreN.setIzq(derN);
					}
					else { //el nodo a eliminar es hijo derecho de su padre
						padreN.setDer(derN);
					}
					//el método eliminar() podría encargarse del trabajo ejecutando el caso 3
				}
				return toReturn;
			}
			else { //el nodo "n" tiene hijo izquierdo, no encontre el mínimo asique sigue buscando hacia la izquierda
				return eliminarMinimo(n.getIzq());
			}
		}
}
