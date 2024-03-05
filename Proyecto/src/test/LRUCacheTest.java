package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cache.LRUCache;
import cache.LRUCacheMap;

public class LRUCacheTest {

	private LRUCache<String, Integer> cache;

    @Before
    public void setUp() {
        cache = getCacheWithCapacity(3);
    }
    
    private LRUCache<String, Integer> getCacheWithCapacity(int capacity) {
    	return new LRUCacheMap<>(capacity);
    }

    @Test
    public void testGet() {
        cache.put("A", 1);
        assertEquals(Integer.valueOf(1), cache.get("A"));
        assertNull(cache.get("B"));
    }

    @Test
    public void testPut() {
        cache.put("A", 1);
        cache.put("B", 2);
        cache.put("C", 3);
        assertEquals(Integer.valueOf(1), cache.get("A"));
        assertEquals(Integer.valueOf(2), cache.get("B"));
        assertEquals(Integer.valueOf(3), cache.get("C"));
        cache.put("D", 4);
        
        assertNull(cache.get("A"));
    }

    @Test
    public void testRemove() {
        cache.put("A", 1);
        cache.put("B", 2);
        cache.remove("A");
        assertNull(cache.get("A"));
    }

    @Test
    public void testClear() {
        cache.put("A", 1);
        cache.put("B", 2);
        cache.clear();
        assertEquals(0, cache.size());
        assertNull(cache.get("A"));
        assertNull(cache.get("B"));
    }
    
    @Test
    public void testReorderGet3() {
    	int capacity = 3;
    	cache = getCacheWithCapacity(capacity);
    	
    	cache.put("0", 0);
    	assertEquals(Integer.valueOf(0), cache.get("0"));
    	cache.put("1", 1);
    	assertEquals(Integer.valueOf(0), cache.get("0"));
    	assertEquals(Integer.valueOf(1), cache.get("1"));
    	
    	cache.put("2", 2);
    	assertEquals(Integer.valueOf(0), cache.get("0"));
    	assertEquals(Integer.valueOf(1), cache.get("1"));
    	assertEquals(Integer.valueOf(2), cache.get("2"));

    	assertEquals(3, cache.size());
    }

    @Test
    public void testReorderGet10() {
    	int capacity = 3;
    	cache = getCacheWithCapacity(capacity);
    	for(int i = 0 ; i < 10 ; i++)
    	{
    		int inicio = 0;
    		if (i > 2) inicio = i - capacity + 1;
    		cache.put(String.valueOf(i), i);
    		for (int j = inicio; j<=i ;j++)
    		{
    			assertEquals(Integer.valueOf(j), cache.get(String.valueOf(j)));
    		}
    	}
    	assertEquals(3, cache.size());
    }

    @Test
    public void testSize() {
        cache.put("A", 1);
        cache.put("B", 2);
        assertEquals(2, cache.size());
        cache.remove("A");
        assertEquals(1, cache.size());
    }
    
    @Test
    public void testSizeAdvanced() {
    	for(int i = 0 ; i < 1000 ; i++)
    		cache.put(String.valueOf(i), i);
    	assertEquals(3, cache.size());
    }
    
    @Test
    public void testSizeAdvancedBig() {
    	cache = getCacheWithCapacity(5000);
    	for(int i = 0 ; i < 1000 ; i++) {
    		cache.put(String.valueOf(i), i);
    		assertEquals(i+1, cache.size());    		
    	}    	
    } 
    
    @Test
    public void testRemoveAdvanced() {
        cache.put("A", 1);
        cache.put("B", 2);
        for(int i = 0 ; i < 1000 ; i++)
        	cache.remove("A");        
        assertNull(cache.get("A"));
        assertEquals(1, cache.size());
    }
    
    @Test
    public void testRemoveManyElements() {
    	cache = getCacheWithCapacity(5000);
    	for(int i = 0 ; i < 1000 ; i++)
    		cache.put(String.valueOf(i), i);
    	
    	for(int i = 0 ; i < 1000 ; i++) {
    		cache.remove(String.valueOf(i));
    		assertEquals(1000-i-1, cache.size());
    	}
    	
    	for(int i = 0 ; i < 1000 ; i++)
    		assertNull(cache.get(String.valueOf(i)));    	
    }
    
    @Test
    public void testClearAdvanced() {
    	for(int i = 0 ; i < 1000 ; i++)
    		cache.put(String.valueOf(i), i);
    	assertEquals(3, cache.size());
    	cache.clear();    	
        assertEquals(0, cache.size());
        for(int i = 0 ; i < 1000 ; i++)
    		assertNull(cache.get(String.valueOf(i)));
    }
    
    @Test
    public void testPut2() {
        cache.put("A", 1);
        cache.put("B", 2);
        cache.put("C", 3);
        cache.put("C", 4);
        assertEquals(Integer.valueOf(1), cache.get("A"));
        assertEquals(Integer.valueOf(2), cache.get("B"));
        assertEquals(Integer.valueOf(4), cache.get("C"));
        assertEquals(3, cache.size());
    }
    
    @Test
    public void testPutCountsAsUsage() {
        cache.put("A", 1);
        cache.put("B", 2);
        cache.put("A", 3);
        cache.put("C", 3);
        cache.put("D", 3);
        assertEquals(3, cache.size());
        assertEquals(Integer.valueOf(3), cache.get("A"));
        assertEquals(Integer.valueOf(3), cache.get("C"));
        assertEquals(Integer.valueOf(3), cache.get("D"));            
        assertNull(cache.get("B"));
    }
    
    @Test
    public void testPutSameValues() {
    	 cache.put("A", 42);
         cache.put("B", 42);
         cache.put("C", 42);
         assertEquals(Integer.valueOf(42), cache.get("A"));
         assertEquals(Integer.valueOf(42), cache.get("B"));
         assertEquals(Integer.valueOf(42), cache.get("C"));
    }
    
    @Test
    public void testCacheSize1() {
    	 cache = getCacheWithCapacity(1);
    	 for(int i = 0 ; i < 1000 ; i++) {
    		 cache.put(String.valueOf(i), i);
	     	 assertEquals(1, cache.size());
	     }    	
    }
    
    @Test
    public void testIntegerCache() {
    	LRUCache<Integer, Integer> cache = new LRUCacheMap<>(3);
    	
		cache.put(1, 1);
		cache.put(4, 4);
		cache.put(1, 1);
		cache.put(4, 4);
		cache.put(2, 2);
		cache.put(4, 4);
		cache.put(3, 3);		          
        assertNull(cache.get(1));
        
		cache.put(4, 4);
		cache.put(1, 1);
		cache.put(1, 1);
		cache.put(1, 1);
		assertNull(cache.get(2));
		
		cache.put(4, 4);
		cache.put(2, 2);
		assertNull(cache.get(3));
		
		cache.put(3, 3);
		cache.put(3, 3);
		assertNull(cache.get(1));
		
		cache.put(2, 2);
		cache.put(2, 2);
		
		cache.put(3, 3);
		cache.put(3, 3);
		cache.put(3, 3);
		cache.put(4, 4);
		assertEquals(Integer.valueOf(2), cache.get(2));
		assertEquals(Integer.valueOf(3), cache.get(3));
		assertEquals(Integer.valueOf(4), cache.get(4));
    }
    
    @Test
    public void testPut1000() {
    	cache = getCacheWithCapacity(1000);
    	for(int i = 0 ; i < 1001 ; i++)
    		cache.put(String.valueOf(i), i);
    	
    	assertEquals(1000, cache.size());
    	assertNull(cache.get("0"));
    }
    
    @Test
    public void testRefreshesUsage() {
        cache.put("A", 1);
        cache.put("B", 2);
        cache.put("C", 3);
        cache.get("A");
        cache.put("D", 4);
        
        assertNull(cache.get("B"));
    }
    
    @Test
    public void testRemoveUpdatesUsage() {
    	cache = getCacheWithCapacity(2);
        cache.put("A", 1);
        cache.remove("A");
        cache.put("B", 2);
        cache.put("A", 1);
        cache.put("C", 3);
        assertNull(cache.get("B"));
        assertEquals(Integer.valueOf(1), cache.get("A"));
    }
    
    @Test
    public void testRemoveEmptyCache() {
    	cache.remove("A");
    }
    
    @Test
    public void testGetEmptyCache() {
    	assertNull(cache.get("B"));
    }
    
    @Test
    public void testSizeEmptyCache() {
    	assertEquals(0, cache.size());
    }
    
    @Test
    public void testClearEmptyCache() {
    	cache.clear();
    	assertEquals(0, cache.size());
    }    


}