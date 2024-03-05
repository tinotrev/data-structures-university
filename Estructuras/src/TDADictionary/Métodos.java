package TDADictionary;

import Exceptions.InvalidKeyException;
import Interfaces.*;
import TDAMapeo.MapHashAbierto;

public class Métodos {
	public static void main(String[]args) {
		
	}
	
	@SuppressWarnings("unused")
	private static<K,V> Dictionary<K,V> acomodar(Dictionary<K,V> d) throws InvalidKeyException {
		Dictionary<K,V> toRet = new DiccionarioConLista<K,V>();
		Map<K,V> aux = new MapHashAbierto<K,V>();
		Iterable<Entry<K,V>> recDicc = d.entries();
		for(Entry<K,V> e : recDicc) {
			aux.put(e.getKey(), e.getValue());
		}
		for(Entry<K,V> e : aux.entries()) {
			toRet.insert(e.getKey(), e.getValue());
		}
		return toRet;
	}
	
	@SuppressWarnings("unused")
	private static Map<Character,Integer> listaAMapeo(PositionList<Character> lista) throws InvalidKeyException {
		Map<Character,Integer> toRet = new MapHashAbierto<Character,Integer>();
		Integer valorViejo = null;
		for(Character c : lista) {
			valorViejo = toRet.get(c);
			if(valorViejo != null) { //ya hay al menos un caracter de eso
				toRet.put(c, ++valorViejo);
			}
			else {
				toRet.put(c, 1);
			}
		}
		return toRet;
	}
}