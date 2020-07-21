package com.example.orderservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Genericos<T>{
	public void m(T t) {
		System.out.println(t);
	}
}


class Animal {
	
	void eat() {
		System.out.println("come");
	}
}

class Dog extends Animal{
	
}


class Cat extends Animal{
	
}

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		
		
//		Genericos<String> g1 = new Genericos<>();
//		
//		Genericos<Integer> g2 = new Genericos<>();
//		
//
//			g1.m("hola");
//			g2.m(3);
//			
//			
//			List<? super Animal> l = new ArrayList<>();
//			
//			l.add(new Cat());
			
			
//		Collection<String> collection =  new ArrayList<>();
//		collection.add("A");
//		collection.add("B");
//		collection.add("C");
//		collection.add("D");
//		collection.add("E");
//		
//		Stream.of(collection).forEach(System.out::println);
		
		List<String> names = new ArrayList<String>();
		List<String> empleados = new ArrayList<String>();
		empleados.add("bebecita");
		empleados.add("luan");
		empleados.add("aruan");
		empleados.add("buan");
		empleados.add("batoloco");
		
		names = new ArrayList<>();
		names = empleados.stream().filter(e -> e.startsWith("b")).collect(Collectors.toList());
		Stream.of(names).forEach(System.out::println);
	
			
	}
	
	

}


class UsoStreams {
	
	public static void main(String[] args) {
		
		Collection<String> collection =  new ArrayList<>();
		collection.add("A");
		collection.add("B");
		collection.add("C");
		collection.add("D");
		collection.add("E");
		
		Stream.of(collection).forEach(System.out::println);
		
	}
}
