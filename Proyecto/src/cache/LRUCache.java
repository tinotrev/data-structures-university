package cache;

public interface LRUCache<K,V> {
   
    /**
     * Devuelve el valor de la clave si la clave existe; de ​​lo contrario, devuelve null.
     * Este método debe ser O(1).
     * @param key
     * @return el valor asociado a la clave si la clave está en la cache. null si la clave no está.
     */
    public V get(K key);
    
    /**
     * Actualiza el valor de la clave si la clave existe.
     * De lo contrario, agrega el par clave-valor a la cache.
     * Si la cantidad de claves excede la capacidad, remueve la clave utilizada menos recientemente.
     * Este método debe ser O(1).
     * @param key
     * @param value
     */
    public void put(K key, V value);
    
    /**
     * Remueve la entrada clave-valor correspondiente a la clave recibida.
     * Si la clave no está en la cache, no hace nada.
     * Este método debe ser O(1).
     * @param key la clave de la entrada a remover
     */
    public void remove(K key);
    
    /**
     * Remueve todas las entradas de la cache.
     */
    public void clear();
    
    /**
     * Retorna la cantidad de entradas clave-valor en la cache.
     * Este método debe ser O(1).
     * @return la cantidad de entradas clave-valor en la cache
     */
    public int size();
    
}