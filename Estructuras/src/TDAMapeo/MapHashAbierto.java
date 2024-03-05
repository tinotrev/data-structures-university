package TDAMapeo;

import Exceptions.*;
import Interfaces.*;
import TDALista.*;

//sin reHash()
public class MapHashAbierto<K,V> implements Map<K,V> {
	
	protected PositionList<Entry<K,V>> [] buckets;
	protected int size; //cantidad de entradas
	protected int N; //tamaño del arreglo
	
	@SuppressWarnings("unchecked")
	public MapHashAbierto() {
		N = 11;
		//Creo que el arreglo de buckets (listas)
		buckets = (PositionList<Entry<K,V>>[]) new ListaDoblementeEnlazada[N];
		//Creo en cada casilla del arreglo una lista doblemente enlazada
		for(int i = 0; i < N; i++)
			buckets[i] = (PositionList<Entry<K, V>>) new ListaDoblementeEnlazada<Entry<K,V>>();
	}
	
	public int hash(K clave) {
		return clave.hashCode() % N;
	}
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	//método que nos va a ayudar a buscar el elemento que necesitamos en el bucket, no controla que la clave no sea nula
	private Position<Entry<K,V>> buscarEnCubeta(PositionList<Entry<K,V>> b, K clave) {
		for(Position<Entry<K,V>> e : b.positions()) {
			if(e.element().getKey() == clave)
				return e;
		}
		return null;
	}
	
	@Override
	public V get(K clave) throws InvalidKeyException {
		V retornar = null;
		if(clave == null)
			throw new InvalidKeyException("Llave nula");
		int b = hash(clave); //nos va a indicar en que bucket buscar
		if(!buckets[b].isEmpty()) { //chequeamos que el bucket (lista) tenga algo
			Position<Entry<K,V>> posicion = buscarEnCubeta(buckets[b], clave); //invocamos al método buscar
			if(posicion != null) { //si encontramos algo lo guardamos en resultado
				retornar = posicion.element().getValue();
			}
		}
		return retornar; //retorna null si la llave no es válida o no se encontró un valor asociado a la clave
	}
	
	@Override
	public V put(K clave, V valor) throws InvalidKeyException {
		V retornar = null;
		if(clave == null)
			throw new InvalidKeyException("Llave nula");
		int b = hash(clave); //nos va a indicar en que bucket buscar
		Position<Entry<K,V>> posicion = buscarEnCubeta(buckets[b], clave); //revisamos si ya existe un valor asociado a esa clave
		if(posicion != null) {
			retornar = posicion.element().getValue();
			((Entrada<K,V>) posicion.element()).setValue(valor);
		}
		else {
			Entry<K,V> e = new Entrada<K,V>(clave,valor);  
			buckets[b].addLast(e); 
		}
		size++;
		return retornar;
	}

	@Override
	public V remove(K key) throws InvalidKeyException {
		V retornar = null;
		if(key == null)
			throw new InvalidKeyException("Llave nula");
		int b = hash(key);
		Position<Entry<K,V>> posicion = buscarEnCubeta(buckets[b], key);
		if(posicion != null) { //hay algo para eliminar
			try {
				retornar = buckets[b].remove(posicion).getValue();
				size--;
			} catch (InvalidPositionException ex) {
				ex.getMessage();
			}
		}
		else {
			System.out.println("No se removió nada");
		}
		return retornar;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> toRet = new ListaDoblementeEnlazada<K>();
		for(Entry<K,V> ent : entries()) {
			toRet.addLast(ent.getKey());
		}
		return toRet;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> toRet = new ListaDoblementeEnlazada<V>();
		for(Entry<K,V> ent : entries()) {
			toRet.addLast(ent.getValue());
		}
		return toRet;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> toRet = new ListaDoblementeEnlazada<Entry<K,V>>();
		for(int i = 0; i < N; i++) {
			for(Entry<K,V> ent : buckets[i]) {
				toRet.addLast(ent);
			}
		}
		return toRet;
	}
}

/******************************************************************************CODIGO VIEJO/NO FUNCIONAL******************************************************************************/

//método que nos va a ayudar a buscar el elemento que necesitamos en el bucket, no controla que la clave no sea nula
	/*private Position<Entry<K,V>> buscarEnCubeta(PositionList<Entry<K,V>> b, K clave) {
		//Declaramos la variable que vamos a retornar
		Position<Entry<K,V>> retornar = null;
		boolean encontre = false;
		try {
			Position<Entry<K,V>> rl = b.first();
			//recorro la lista usando sus método
			while(!encontre && rl != null) {
				if(rl.element().getKey().equals(clave)) {
					retornar = rl;
					encontre = true;
				}
				else {
					rl = b.next(rl);
					if(rl == b.last())
						rl = null;
				}
			}
		} catch(EmptyListException | BoundaryViolationException | InvalidPositionException ex) {
			ex.getMessage();
		}
		return retornar;
	}*/

/*************************************************************************************************************************************************************************************/