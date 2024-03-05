package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import TDALista.ListaDoblementeEnlazada;
import TDALista.Métodos;
import Interfaces.PositionList;

/*
Estos tests asumen que los ejercicios 3 y 4 fueron resueltos mediante una clase EjerciciosTP4
con un método público para cada ejercicio.
 
public class EjerciciosTP4<E> {
	estaElem
	elemRepetido
}

Los ejercicios 1 y 2 se asumen fueron resueltos mediantes una clase ListaDoblementeEnlazada.
*/

public class EjerciciosTP4Test {
	
	private Métodos<Integer> ejerciciosInt;
	    
    @Before
    public void setUp() {
        ejerciciosInt = new Métodos<>();
    }

	@Test
	public void testEj2agregarElem(){
		ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
		l.agregarElem(1,2);
		
		int[] expected = {2, 1};
		
		int i = 0;
		for(int e : l) {
			assertEquals(expected[i], e);
			i++;
		}
		
		assertEquals(2, l.size());
		
		l.agregarElem(3,4);
		int[] expected2 = {2, 3, 4, 1};
		
		i = 0;
		for(int e : l) {
			assertEquals(expected2[i], e);
			i++;
		}
		
		l.agregarElem(5,6);
		int[] expected3 = {2, 5, 3, 4, 6, 1};
		
		i = 0;
		for(int e : l) {
			assertEquals(expected3[i], e);
			i++;
		}
	}
		
	@SuppressWarnings("static-access")
	@Test
	public void testestaElem() {
		PositionList<Integer> l = new ListaDoblementeEnlazada<Integer>();
		l.addLast(1);
		l.addLast(3);
		l.addLast(5);
		l.addLast(7);
		assertTrue(ejerciciosInt.estaElem(l, 3));
		assertTrue(ejerciciosInt.estaElem(l, 7));
		assertFalse(ejerciciosInt.estaElem(l, 4));;
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testestaElemVacio() {
		PositionList<Integer> l = new ListaDoblementeEnlazada<Integer>();
		assertFalse(ejerciciosInt.estaElem(l, 3));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testestaElemEquivalencia() {
		Métodos<Persona> ej = new Métodos<Persona>();
		
		PositionList<Persona> l = new ListaDoblementeEnlazada<Persona>();
    	l.addLast(new Persona("Luke"));
    	l.addLast(new Persona("Anakin"));
    	
    	assertTrue("La comparación de elementos debe ser por equivalencia", ej.estaElem(l, new Persona("Anakin")));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testelemRepetido() {
		PositionList<Integer> l = new ListaDoblementeEnlazada<Integer>();
		l.addLast(1);
		l.addLast(3);
		l.addLast(5);
		l.addLast(7);
		
		int[] expected = {1, 1, 3, 3, 5, 5, 7, 7};
		
		int i = 0;
		for(int e : ejerciciosInt.elemRepetidos(l)) {
			assertEquals(expected[i], e);
			i++;
		}
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testelemRepetidoVacia() {
		PositionList<Integer> vacia = new ListaDoblementeEnlazada<Integer>();
		assertEquals(0, ejerciciosInt.elemRepetidos(vacia).size());
	}
}

class Persona {
	private String name;
	
	public Persona(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Persona)) {
            return false;
        }
		Persona p = (Persona) o;
         
		return name.equals(p.getName());		
	}
}