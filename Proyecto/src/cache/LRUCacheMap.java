package cache;

import list.*;
import map.*;

public class LRUCacheMap<K,V> implements LRUCache<K,V> {
	
	protected int maxCapacity; //capacidad máxima del cache
	protected Map<K,V> mapeo; //mapeo original
	protected Map<K,Position<K>> mapPos; //guarda referencias a las posiciones de la lista para su rápido acceso
	protected PositionList<K> lista; //lista para controlar el orden de uso, basada en las claves
	
	public LRUCacheMap(int capacity){
		maxCapacity = capacity;
		mapeo = new MapeoConHash<>();
		mapPos = new MapeoConHash<>();
		lista = new ListaDoblementeEnlazada<>();
	}

	public V get(K key) {
		V retorno = null;
		try {
			retorno = mapeo.get(key); //guardo el elemento que luego voy a retornar pidiendoselo al mapeo original
			if(retorno != null) { //si el elemento no es nulo (si ya existe un valor asociado a la clave)
				Position<K> posBorrar = mapPos.get(key); //guardo la posición a borrar pidiendosela al mapeo auxiliar
				lista.remove(posBorrar); //borro la posición de la lista
				lista.addFirst(key); //inmediatamente la agrego al principio de lista
				Position<K> nuevaPos = lista.first(); //ahora vuelvo a guardar la posición con sus nuevas referencias de donde está
				mapPos.put(key, nuevaPos); //inserto esa posición al mapeo auxiliar para tenerla preparada
			}
		}
		catch (InvalidKeyException | InvalidPositionException | EmptyListException e) {
			e.getMessage(); //algo salio mal
		}
		return retorno; //retornamos el valor pedido
	}

	public void put(K key, V value) {
		try {
			Position<K> pos = mapPos.get(key); //guardo la posición asociada a la clave pidiendosela al mapeo auxiliar
			if(pos != null) { //si la posición no es nula (si ya existe un valor asociado a la clave)
				lista.remove(pos); //borro la posición en la lista
				lista.addFirst(key); //la agrego inmediatamente al inicio
				Position<K> nuevaPos = lista.first(); //guardo la posición con sus nuevas referencias del lugar en el que está
				mapPos.put(key, nuevaPos); //agrego la posición al mapeo auxiliar
				mapeo.put(key, value); //inserto el nuevo valor al mapeo original reemplazandolo por el valor anterior
			}
			else { //si no es nulo (si no existe un valor asociado a esa clave)
				lista.addFirst(key); //añado la clave al principio de la lista
				Position<K> nuevaPos = lista.first(); //le pido a la lista la posición de la clave y la guardo
				mapPos.put(key, nuevaPos); //inserto al mapeo auxiliar la posición
				mapeo.put(key, value); //inserto al mapeo original el valor
				if(lista.size() > maxCapacity) { //si excedimos la capacidad del cache
					Position<K> ultimo = lista.last(); //guardamos la posición del último elemento de la lista
					lista.remove(ultimo); //borramos la posición de la lista
					mapeo.remove(ultimo.element()); //borramos la posición del mapeo auxiliar
					mapPos.remove(ultimo.element()); //borramos la entrada clave-valor correspondiente del mapeo original
				}
			}
		}
		catch(InvalidKeyException | InvalidPositionException | EmptyListException e) {
			e.getMessage(); //algo salió mal
		}
	} 

	public void remove(K key) {
		try {
			Position<K> posBorrar = mapPos.get(key); //guardo la posición a borrar
			lista.remove(posBorrar); //la borro de la lista
			mapeo.remove(key); //borramos la entrada clave-valor del mapeo original
			mapPos.remove(key); //borramos la posición del mapeo auxiliar
		}
		catch(InvalidKeyException | InvalidPositionException e){
			e.getMessage(); //algo salió mal
		}
	}

	public void clear() {
		//inicializo de cero todos los atributos del cache
		lista = new ListaDoblementeEnlazada<>();
		mapeo = new MapeoConHash<>();
		mapPos = new MapeoConHash<>();
	}

	public int size() {
		return mapeo.size();
	}
}