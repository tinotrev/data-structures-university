package TDAGrafoDecorado;

import java.util.Iterator;

import Exceptions.EmptyQueueException;
import Exceptions.InvalidEdgeException;
import Exceptions.InvalidKeyException;
import Exceptions.InvalidVertexException;
import Interfaces.EdgeDeco;
import Interfaces.GraphDeco;
import Interfaces.Map;
import Interfaces.Queue;
import Interfaces.VertexDeco;
import TDACola.ColaEnlazada;
import TDAMapeo.MapHashAbierto;

public class Métodos {
	
	private static final Object VISITADO = new Object();
	private static final Object NOVISITADO = new Object();
	private static final Object ESTADO = new Object();
	
	@SuppressWarnings("unused")
	public static void main(String args[]) {
		GraphDeco<String, Integer> g1 = new GrafoND<>();
		GraphDeco<String, Integer> g2 = new GrafoND<>();
		
		try {
			VertexDeco<String> a = g1.insertVertex("a");
			VertexDeco<String> b = g1.insertVertex("b");
			VertexDeco<String> c = g1.insertVertex("c");
			
			EdgeDeco<Integer> ab = g1.insertEdge(a, b, 1);
			EdgeDeco<Integer> bc = g1.insertEdge(b, c, 2);
			EdgeDeco<Integer> ac = g1.insertEdge(a, c, 3);
			
			VertexDeco<String> d = g1.insertVertex("d");
			VertexDeco<String> e = g1.insertVertex("e");
			
			EdgeDeco<Integer> de = g1.insertEdge(d, e, 4);
			
			VertexDeco<String> f = g1.insertVertex("f");
			VertexDeco<String> g = g1.insertVertex("g");
			
			EdgeDeco<Integer> fg = g1.insertEdge(f, g, 5);
			
			//EdgeDeco<Integer> cd = g1.insertEdge(c, d, 8);
			//EdgeDeco<Integer> eg = g1.insertEdge(e, g, 9);
			
			System.out.println(esConexo(g1));
			
			VertexDeco<String> h = g2.insertVertex("h");
			VertexDeco<String> i = g2.insertVertex("i");
			VertexDeco<String> j = g2.insertVertex("j");
			
			EdgeDeco<Integer> hi = g2.insertEdge(h, i, 6);
			EdgeDeco<Integer> hj = g2.insertEdge(h, j, 7);
			
			//VertexDeco<String> l = g2.insertVertex("l");
			
			System.out.println(esConexo(g2));
			
			System.out.println("---------------------------------------------------------------------------------------");
			
			mapearAreas(g1);
			mapearAreas(g2);
			
			System.out.println("---------------------------------------------------------------------------------------");
			
			dfsShell(g1);
			System.out.println();
			dfsShell(g2);
			
			System.out.println("---------------------------------------------------------------------------------------");
			
			bfsShell(g1);
			System.out.println();
			bfsShell(g2);
			
		} catch(InvalidVertexException ex) {
			ex.printStackTrace();
		}
	}
	
	public static<V,E> boolean esConexo(GraphDeco<V,E> g) {
		try {
			for(VertexDeco<V> v : g.vertices()) {
				v.put(ESTADO, NOVISITADO);
			}
			Iterator<VertexDeco<V>> it = g.vertices().iterator();
			
			boolean retornar = true;
			VertexDeco<V> cursor = null;
			
			if(it.hasNext()) {
				cursor = it.next();
				dfs(g, cursor);
			}
			else
				System.out.print("Algo anda mal, no hay vertices en la lista");
			
			
			while(retornar && it.hasNext()) {
				cursor = it.next();
				if(cursor.get(ESTADO) == NOVISITADO) {
					retornar = false;
				}
			}
			return retornar;
		} catch(InvalidKeyException e) {
			e.printStackTrace();
		}
		System.out.println("No deberiamos de poder llegar aquí");
		return false;
	}
	
	public static<V,E> Map<V,E> mapearAreas(GraphDeco<V,E> g) {
		Map<VertexDeco<V>,IntEspecial> retornar = new MapHashAbierto<>();
		IntEspecial area = new IntEspecial();
		area.setNum(1);
		try {
			for(VertexDeco<V> v : g.vertices()) {
				v.put(ESTADO, NOVISITADO);
			}
			for(VertexDeco<V> v :g.vertices()) {
				try {
					if(v.get(ESTADO) == NOVISITADO) {
						dfsMapeo(g, v, retornar, area);
						area.setNum(area.getNum() + 1);
						
					}
				} catch (InvalidKeyException e) {
					e.printStackTrace();
				}
			}
			
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//metodo auxiliar para mapearAreas()
	private static<V,E> void dfsMapeo(GraphDeco<V,E> g, VertexDeco<V> v, Map<VertexDeco<V>, IntEspecial> map, IntEspecial num) {
		try { 
			map.put(v, num); //PROCESAMIENTO DEL VERTICE
			System.out.println("El vertice: " + v.element() + " tiene de area a: " + num.toString());
			
			v.put(ESTADO, VISITADO);
			Iterable<EdgeDeco<E>> adyacentes = g.incidentEdges(v);
			for(EdgeDeco<E> a : adyacentes) {
				VertexDeco<V> w = g.opposite(v, a);
				if(w.get(ESTADO) == NOVISITADO) {
					dfsMapeo(g,w,map,num);
				}
			}
		} catch(InvalidKeyException | InvalidVertexException | InvalidEdgeException ex) {
			ex.printStackTrace();
		}
	}
	
	//recorrido generico de todos los vértices
	public static<V,E> void dfsShell(GraphDeco<V,E> g) {
		for(VertexDeco<V> v : g.vertices()) { //primero que nada seteamos a todos los vertices como no visitados
			try {
				v.put(ESTADO, NOVISITADO);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
		}
		for(VertexDeco<V> v :g.vertices()) {
			try {
				if(v.get(ESTADO) == NOVISITADO) {
					dfs(g, v);
				}
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
		}
	}
	
	//metoto auxiliar para dfsShell(), procesamiento generico de todos los vértices
	private static<V,E> void dfs(GraphDeco<V,E> g, VertexDeco<V> v) {
		
		System.out.println(v.element()); //PROCESAMIENTO DEL VERTICE
			
		try {
			v.put(ESTADO, VISITADO);
			Iterable<EdgeDeco<E>> adyacentes = g.incidentEdges(v);
			for(EdgeDeco<E> a : adyacentes) {
				VertexDeco<V> w = g.opposite(v, a);
				if(w.get(ESTADO) == NOVISITADO) {
					dfs(g,w);
				}
			}
		} catch(InvalidKeyException | InvalidVertexException | InvalidEdgeException ex) {
			ex.printStackTrace();
		}
	}
	
	//recorrido génerico de todos los vertices por anchura
	private static<V,E> void bfsShell(GraphDeco<V,E> g) {
		try {
			for(VertexDeco<V> v : g.vertices()) {
				v.put(ESTADO, NOVISITADO);
			}
			for(VertexDeco<V> v : g.vertices()) {
				if(v.get(ESTADO) == NOVISITADO) {
					bfs(g, v);
				}
			}
		} catch(InvalidKeyException e) {
			e.printStackTrace();
		}
	}
	
	//método auxiliar para bfsShell(), procesamiento génerico de todos los vertices
	private static<V,E> void bfs(GraphDeco<V,E> g, VertexDeco<V> ver) {
		Queue<VertexDeco<V>> cola = new ColaEnlazada<>();
		try {
			cola.enqueue(ver);
			ver.put(ESTADO, VISITADO);
			while(!cola.isEmpty()) {
				VertexDeco<V> v = cola.dequeue();
				System.out.println(v.element()); //proceso el vertice
				for(EdgeDeco<E> a : g.incidentEdges(v)) {
					VertexDeco<V> w = g.opposite(v, a);
					if(w.get(ESTADO) == NOVISITADO) {
						w.put(ESTADO, VISITADO);
						cola.enqueue(w);
					}
				}
			}
			
		} catch(InvalidKeyException | EmptyQueueException | InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
	}
	
	
	//clase auxiliar para pasar variables "int" como parámetro
	private static class IntEspecial {
		
		private Integer num;
		
		public IntEspecial() {
			num = null;
		}
		
		public int getNum() {
			return num;
		}
		
		public void setNum(int n) {
			num = n;
		}
		
		public String toString() {
			return num.toString();
		}
	}
}


