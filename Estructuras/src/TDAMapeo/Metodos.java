package TDAMapeo;

import Exceptions.InvalidKeyException;
import Interfaces.Map;
import TDAColaPrioridad.DefaultComparator;

public class Metodos<K,V> {
	
	public static void main(String args[]) {
		Map<Integer,String> m1 = new MapeoABB<Integer, String>(new DefaultComparator<>());
		
		try {
			m1.put(10, "10");
			m1.put(8, "8");
			m1.put(23, "23");
			m1.put(12, "12");
			m1.put(3, "3");
			m1.put(7, "7");
			m1.put(4, "4");
			m1.put(90, "90");
			m1.put(78, "78");
			m1.put(100, "100");
			m1.put(95, "95");
			m1.put(45, "45");
			m1.put(44, "44");
			m1.put(89, "89");
			
			((MapeoABB<Integer, String>) m1).imprimirPreOrdenShell();
			System.out.println("");
			
			m1.remove(23);
			System.out.println("");
			
			m1.remove(3);
			
			((MapeoABB<Integer, String>) m1).imprimirPreOrdenShell();
			System.out.println("");
			
			m1.remove(8);
			
			((MapeoABB<Integer, String>) m1).imprimirPreOrdenShell();
			
		} catch(InvalidKeyException ex) {
			ex.printStackTrace();
		}
	}
}
