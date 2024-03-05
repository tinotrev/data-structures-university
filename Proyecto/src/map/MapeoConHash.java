package map;

import java.util.Iterator;
import list.*;

public class MapeoConHash<K, V> implements Map<K, V> {
	protected int size;
	protected static final float Factor_Carga = 0.5f;
	protected PositionList<Entry<K,V>> [] CUBETAS;
	protected int cantCubetas;

	@SuppressWarnings("unchecked")
	public MapeoConHash() {
		cantCubetas = 11;
		size = 0;
		CUBETAS = new ListaDoblementeEnlazada[cantCubetas];
		for(int i=0; i < cantCubetas; i++){
			CUBETAS[i] = new ListaDoblementeEnlazada<>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public MapeoConHash(int n) {
		cantCubetas = proxPrimo(n);
		size = 0;
		CUBETAS = new ListaDoblementeEnlazada[cantCubetas];
		for(int i=0; i < cantCubetas; i++){
			CUBETAS[i] = new ListaDoblementeEnlazada<>();
		}
	}
	
	private int getCubeta(K clave) {
		return (clave.hashCode() % cantCubetas);
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size==0;
	}

	public V get(K key) throws InvalidKeyException {
		V toReturn = null;
		if(key == null) {
			throw new InvalidKeyException("Clave invalida");
		}
		else {
			int cubeta = getCubeta(key);
			Entry<K,V> entrada = buscarEnCubeta(cubeta, key);
			if(entrada != null) {
				toReturn = entrada.getValue();
			}
		}
		return toReturn;
	}

	private Entry<K,V> buscarEnCubeta(int cubeta, K key) {
		Entry<K,V> toReturn = null;
		Iterator<Entry<K,V>> it = CUBETAS[cubeta].iterator();
		boolean encontre = false;
		while(it.hasNext() && !encontre) {
			Entry<K,V> cursor = it.next();
			if(cursor.getKey().equals(key)) {
				encontre = true;
				toReturn = cursor;
			}
		}
		return toReturn;
	}

	public V put(K key, V value) throws InvalidKeyException {
		V toReturn = null;
		if(key == null) {
			throw new InvalidKeyException("Clave invalida");
		}
		else {
			if(size/cantCubetas >= Factor_Carga) {
				reHash();
			}
			int cubeta = getCubeta(key);
			Entry<K,V> entrada = buscarEnCubeta(cubeta, key);
			if(entrada != null) {
				toReturn = entrada.getValue();
				((Entrada<K,V>) entrada).setValue(value);
			}
			else {
				Entrada<K,V> nueva = new Entrada<>(key, value);
				CUBETAS[cubeta].addLast(nueva);
				size++;
			}
		}
		return toReturn;
	}

	@SuppressWarnings("unchecked")
	private void reHash() throws InvalidKeyException{
        Iterable<Entry<K,V>> entradas = entries();
        cantCubetas = proxPrimo(cantCubetas*2);
        size = 0;
        CUBETAS = new ListaDoblementeEnlazada[cantCubetas];
        for(int i=0; i < cantCubetas; i++) {
        	CUBETAS[i] = new ListaDoblementeEnlazada<>();
        }
        for(Entry<K,V> e : entradas) {
            put(e.getKey(), e.getValue());
        }
    }

    private int proxPrimo(int num) {
    	int toReturn = 0;
    	boolean encontre = false;
    	boolean esPrimo = true;
    	int cantDiv = 0;
    	while(!encontre) {
    		for(int i = 2; i < num && esPrimo; i++) {
    			if(num % i == 0) {
    				cantDiv++;
    			}
    			if(cantDiv == 1) {
    				esPrimo = false;
    			}
    		}
    		if(esPrimo) {
    			encontre = true;
    			toReturn = num;
    		}
    		else {
    			num++;
    			cantDiv=0;
    			esPrimo = true;
    		}
    	}
    	return toReturn;
    }

	public V remove(K key) throws InvalidKeyException {
		V toReturn = null;
		if(key == null) {
			throw new InvalidKeyException("Clave invalida");
		}
		else {
			int cubeta = getCubeta(key);
			Iterator<Position<Entry<K,V>>> it = CUBETAS[cubeta].positions().iterator();
			boolean encontre = false;
			while(it.hasNext() && !encontre) {
				Position<Entry<K,V>> cursor = it.next();
				if(cursor.element().getKey().equals(key)) {
					encontre = true;
					toReturn = cursor.element().getValue();
					try {
						CUBETAS[cubeta].remove(cursor);
						size--;
					}
					catch (InvalidPositionException ex) {
						ex.getMessage();
					}
				}
			}
		}
		return toReturn;
	}

	public Iterable<K> keys() {
		PositionList<K> toReturn = new ListaDoblementeEnlazada<>();
		for(int i = 0; i < cantCubetas; i++) {
			for(Entry<K,V> entradas : CUBETAS[i]) {
				toReturn.addLast(entradas.getKey());
			}
		}
		return toReturn;
	}

	public Iterable<V> values() {
		PositionList<V> toReturn = new ListaDoblementeEnlazada<>();
		for(int i = 0; i < cantCubetas; i++) {
			for(Entry<K,V> entradas : CUBETAS[i]) {
				toReturn.addLast(entradas.getValue());
			}
		}
		return toReturn;
	}

	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K, V>> toReturn = new ListaDoblementeEnlazada<>();
		for(int i = 0; i < cantCubetas; i++) {
			for(Entry<K,V> entradas : CUBETAS[i]) {
				toReturn.addLast(entradas);
			}
		}
		return toReturn;
	}

	
}
