package TDAMapeo;

import java.util.Comparator;

import Exceptions.InvalidKeyException;
import Interfaces.Entry;
import Interfaces.Map;
import Interfaces.PositionList;
import TDAABBúsqueda.NodoABB;
import TDALista.ListaDoblementeEnlazada;

public class MapeoABB<K,V> implements Map<K,V> {
	
	NodoABB<Entry<K,V>> raiz;
	Comparator<K> comp;
	int size;
	
	public MapeoABB(Comparator<K> c) {
		raiz = new NodoABB<>(null);
		comp = c;
		size = 0;
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	private NodoABB<Entry<K,V>> buscar(K key) {
		return buscarAux(key, raiz);
		
	}
	
	private NodoABB<Entry<K,V>> buscarAux(K key, NodoABB<Entry<K,V>> n) {
		if(n.getRotulo() == null)
			return n; //Dummy
		else {
			int c = comp.compare(key, n.getRotulo().getKey());
			if(c == 0) {  
				return n; //se encontró
			}
			else {
				if(c < 0)
					return buscarAux(key, n.getIzq());
				else
					return buscarAux(key, n.getDer());
			}
		}
	}
	
	@Override
	public V get(K key) throws InvalidKeyException {
		if(key == null)
			throw new InvalidKeyException("");
		NodoABB<Entry<K,V>> n =  buscar(key);
		if(n.getRotulo() != null) {
			return n.getRotulo().getValue();
		}
		return null;
	}

	@Override
	public V put(K key, V value) throws InvalidKeyException {
		if(key == null)
			throw new InvalidKeyException("");
		NodoABB<Entry<K,V>> nodo = buscar(key);
		Entry<K,V> e = new Entrada<>(key, value);
		if(nodo.getRotulo() == null) {
			nodo.setRotulo(e);
			nodo.setIzq(new NodoABB<>(null, nodo));
			nodo.setDer(new NodoABB<>(null, nodo));
			size++;
			return null;
		}
		else {
			V valorViejo = nodo.getRotulo().getValue();
			nodo.setRotulo(e);
			return valorViejo;
		}
	}
	
	@Override
	public V remove(K key) throws InvalidKeyException {
		if(key == null)
			throw new InvalidKeyException("");
		NodoABB<Entry<K,V>> nodo = buscar(key);
		if(nodo.getRotulo() != null) { //si no es dummy
			V retornar = nodo.getRotulo().getValue();
			removeAux(nodo);
			return retornar;
		}
		else
			return null;
	}
	
	private void removeAux(NodoABB<Entry<K,V>> nodo) {
		boolean esRaiz = nodo == raiz;
		if(nodo.getIzq().getRotulo() == null && nodo.getDer().getRotulo() == null) { //es hoja, sus hijos son dummy
			nodo.setRotulo(null);
			nodo.setIzq(null);
			nodo.setDer(null);
			//si es raiz no hacemos nada pues, el nodo no se destruye solo se transforma en dummy
		}
		else {
			NodoABB<Entry<K,V>> padre = nodo.getPadre();
			if(nodo.getIzq().getRotulo() != null && nodo.getDer().getRotulo() == null) { //tiene un solo hijo izquierdo
				NodoABB<Entry<K,V>> hijo = nodo.getIzq();
				if(esRaiz) {
					raiz = hijo;
					hijo.setPadre(null);
				}
				else { //si no es raíz entonces tiene padre
					hijo.setPadre(padre);
					if(padre.getIzq() == nodo) {
						padre.setIzq(hijo);
					}
					else {
						padre.setDer(hijo);
					}
				}
			}
			else {
				if(nodo.getDer().getRotulo() != null && nodo.getIzq().getRotulo()  == null) { //tiene un solo hijo derecho
					NodoABB<Entry<K,V>> hijo = nodo.getDer();
					if(esRaiz) {
						raiz = hijo;
						hijo.setPadre(null);
					}
					else { //si no es raíz entonces tiene padre
						hijo.setPadre(padre);
						if(padre.getIzq() == nodo) {
							padre.setIzq(hijo);
						}
						else {
							padre.setDer(hijo);
						}
					}
				}
				else { //el nodo tiene dos hijos
					Entry<K,V> nuevo = sucesorInorder(nodo);
					nodo.setRotulo(nuevo);
					//si es raiz no hacemos nada pues el nodo no se destruye solo cambia su entry;
				}
			}
		}
		size--;
	}
	
	private Entry<K,V> sucesorInorder(NodoABB<Entry<K,V>> nodo) {
		NodoABB<Entry<K,V>> cursor = nodo.getDer();
		while(true) {
			if(cursor.getIzq().getRotulo() == null) { //encontre el minimo
				Entry<K,V> retornar = cursor.getRotulo();
				removeAux(cursor);
				return retornar;
			}
			else {
				cursor = cursor.getIzq(); //sigo buscando a izquierda
			}
		}
	}
	
	@Override
	public Iterable<K> keys() {
		PositionList<K> retornar = new ListaDoblementeEnlazada<>();
		for(Entry<K,V> e : entries()) {
			retornar.addLast(e.getKey());
		}
		return retornar;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> retornar = new ListaDoblementeEnlazada<>();
		for(Entry<K,V> e : entries()) {
			retornar.addLast(e.getValue());
		}
		return retornar;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> retornar = new ListaDoblementeEnlazada<>();
		if(raiz.getRotulo() != null)
			preOrden(raiz, retornar);
		return retornar;
	}
	
	//recorrido preOrden para entries()
	public void preOrden(NodoABB<Entry<K,V>> nodo, PositionList<Entry<K,V>> retornar) {
		retornar.addLast(nodo.getRotulo()); //visito
		if(nodo.getIzq().getRotulo() != null) {
			preOrden(nodo.getIzq(), retornar);
		}
		if(nodo.getDer().getRotulo() != null) {
			preOrden(nodo.getDer(), retornar);
		}
	}
	
	
	//***********************************************************************CODIGO AUXILIAR PARA LA CLASE METODOS***********************************************************************//
	public void imprimirPreOrdenShell() {
		imprimirPreOrden(raiz);
		
		System.out.println();
		System.out.println(raiz.getRotulo().getKey());
	}
	
	private void imprimirPreOrden(NodoABB<Entry<K,V>> nodo) {
		if(nodo.getRotulo() != null)
			System.out.print(nodo.getRotulo().getKey() + " ");
		//else
			//System.out.print("null ");
		
		if(nodo.getIzq() != null)
			imprimirPreOrden(nodo.getIzq());
		if(nodo.getDer() != null)
			imprimirPreOrden(nodo.getDer());
	}
}

//***********************************************************************FIN CODIGO AUXILIAR PARA LA CLASE METODOS***********************************************************************//
	
	//***************************************************************************************IMPLEMENTACION VIEJA***************************************************************************************//
	/*
	public V remove(K key) throws InvalidKeyException {
		V resultado = null;
		if(key != null) {
			NodoABB<Entry<K, V>> nodo = buscar(key);
			if(nodo.getRotulo() != null && nodo.getRotulo().getKey().equals(key)) {
				resultado = nodo.getRotulo().getValue();
				eliminar(nodo, key);
				size--;
			}
		}
		else {
			throw new InvalidKeyException("La clave es invalida");
		}
		return resultado;
	}

	private void eliminar(NodoABB<Entry<K, V>> nodo, K key) {
		
		System.out.println("EL NODO QUE VOY A ELIMINAR ES: " + nodo.getRotulo().getKey());
		
		
		NodoABB<Entry<K, V>> padre = nodo.getPadre();
		if(nodo.getIzq().getRotulo() == null && nodo.getDer().getRotulo() == null) {
			nodo.setRotulo(null);
		}
		else {
			if(nodo.getIzq().getRotulo() != null && nodo.getDer().getRotulo() == null) {
				
				NodoABB<Entry<K, V>> hijo = nodo.getIzq();
				
				System.out.println("TIENE UN SOLO HIJO IZQUIERDO Y ES: " + hijo.getRotulo().getKey());
				System.out.println("EL PADRE DEL NODO ES: " + padre.getRotulo().getKey());
				
				if(padre.getIzq().getRotulo() != null)
					System.out.println("EL PADRE TIENE DE HIJO IZQUIERDO A: "  + padre.getIzq().getRotulo().getKey());
				
				if(padre.getDer().getRotulo() != null)
					System.out.println("EL PADRE TIENE DE HIJO DERECHO A: " + padre.getDer().getRotulo().getKey());
				
				nodo.getIzq().setPadre(padre);
				nodo = nodo.getIzq();
				
				System.out.println("YA BORRE EL NODO");
				
				if(padre.getIzq().getRotulo() != null)
					System.out.println("EL HIJO IZQUIERDO DEL PADRE AHORA ES: " + padre.getIzq().getRotulo().getKey());
				
				if(padre.getDer().getRotulo() != null)
					System.out.println("EL HIJO DERECHO DEL PADRE AHORA ES: " + padre.getDer().getRotulo().getKey());
				
				System.out.println("ESE HIJO IZQUIERDO AHORA TIENE QUE TENER DE PADRE A SU ABUELO: " + hijo.getPadre().getRotulo().getKey());
			}
			else {
				if(nodo.getIzq().getRotulo() == null && nodo.getDer().getRotulo() != null) {
					
					NodoABB<Entry<K, V>> hijo = nodo.getDer();
					
					System.out.println("TIENE UN SOLO HIJO DERECHO Y ES: " + hijo.getRotulo().getKey());
					System.out.println("EL PADRE DEL NODO ES: " + padre.getRotulo().getKey());
					
					if(padre.getIzq().getRotulo() != null)
						System.out.println("EL PADRE TIENE DE HIJO IZQUIERDO A: "  + padre.getIzq().getRotulo().getKey());
					
					if(padre.getDer().getRotulo() != null)
						System.out.println("EL PADRE TIENE DE HIJO DERECHO A: " + padre.getDer().getRotulo().getKey());
					
					nodo.getDer().setPadre(padre);
					nodo = nodo.getDer();
					
					System.out.println("YA BORRE EL NODO");
					
					if(padre.getIzq().getRotulo() != null)
						System.out.println("EL HIJO IZQUIERDO DEL PADRE AHORA ES: " + padre.getIzq().getRotulo().getKey());
					
					if(padre.getDer().getRotulo() != null)
						System.out.println("EL HIJO DERECHO DEL PADRE AHORA ES: " + padre.getDer().getRotulo().getKey());
					
					System.out.println("ESE HIJO DERECHO AHORA TIENE QUE TENER DE PADRE A SU ABUELO: " + hijo.getPadre().getRotulo().getKey());
				}
				else {
					NodoABB<Entry<K, V>> nuevo = buscarIndicado(nodo.getIzq());
					Entry<K, V> entrada = nuevo.getRotulo();
					eliminar(nuevo, nuevo.getRotulo().getKey());
					nodo.setRotulo(entrada);
				}
			}
		}
	}
	
	private NodoABB<Entry<K, V>> buscarIndicado(NodoABB<Entry<K, V>> nodo) {
		NodoABB<Entry<K, V>> resultado = nodo;
		boolean encontre = false;
		while(!encontre) {
			if(resultado.getDer().getRotulo() == null) {
				encontre = true;
			}
			else {
				resultado = resultado.getDer();
			}
		}
		return resultado;
	}
	*/
	//************************************************************************************FIN IMPLEMENTACION VIEJA**************************************************************************************//

