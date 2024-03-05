package TDADictionary;

import Interfaces.*;

import java.util.Iterator;

import Exceptions.*;
import TDALista.*;

public class DiccionarioConLista<K,V> implements Dictionary<K,V> {
	
	protected PositionList<Entry<K,V>> diccionario;
	
	public DiccionarioConLista( ) {
		diccionario = new ListaDoblementeEnlazada<Entry<K,V>>();
	}
	
	@Override
	public int size() {
		return diccionario.size();
	}

	@Override
	public boolean isEmpty() {
		return diccionario.size() == 0;
	}

	@Override
	public Entry<K, V> find(K key) throws InvalidKeyException {
		Entry<K,V> retornar = null;
		if(key != null) {
			Iterator<Entry<K,V>> it = diccionario.iterator();
			boolean encontre = false;
			while(it.hasNext() && !encontre) {
				Entry<K,V> entrada = it.next();
				if(entrada.getKey().equals((key))) {
					retornar = entrada;
					encontre = true;
				}
			}
		}
		else {
			throw new InvalidKeyException("La llave no es válida");
		}
		return retornar;
	}

	@Override
	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		PositionList<Entry<K,V>> retornar = new ListaDoblementeEnlazada<Entry<K,V>>();
		if(key != null) {
			for(Entry<K,V> rl : diccionario) {
				if(rl.getKey().equals(key)) {
					retornar.addLast(rl);
				}
			}
		}
		else {
			throw new InvalidKeyException("La llave no es válida");
		}
		return retornar;
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		Entry<K,V> retornar = null;
		if(key != null ) {
			retornar = new Entrada<K,V>(key, value);
			diccionario.addLast(retornar);
		}
		else {
			throw new InvalidKeyException("La llave no es válida");
		}
		return retornar;
	}

	@Override
	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		if(e != null) {
			for(Position<Entry<K,V>> p : diccionario.positions()) {
				if(p.element() == e) {
					try {
						diccionario.remove(p);
						return e;
					} catch (InvalidPositionException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		throw new InvalidEntryException("La entrada no pertenece al diccionario.");
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		return diccionario;
	}
	//***********************************EJERCICIO 5 TP5*************************************//
	public Iterable<Entry<K,V>> eliminarTodas(K k, V v) throws InvalidEntryException {
		PositionList<Entry<K,V>> toRet = new ListaDoblementeEnlazada<Entry<K,V>>();
		Entry<K,V> entrada = new Entrada<K,V>(k,v);
		
		
		
		
		for(Entry<K,V> e : diccionario) {
			if(e == entrada) {
				this.remove(e);
				toRet.addLast(e);
			}
		}
		return toRet;
	}
	//***************************************************************************************//
	
}
