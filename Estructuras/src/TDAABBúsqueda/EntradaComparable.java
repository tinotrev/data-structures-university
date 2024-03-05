package TDAABBúsqueda;

import Interfaces.EntryABB;

public class EntradaComparable<K extends Comparable<K>,V> implements EntryABB<K,V> {
	
	K key;
	V value;
	
	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public int compareTo(EntryABB<K, V> o) {
		return this.getKey().compareTo(o.getKey());
	}
}