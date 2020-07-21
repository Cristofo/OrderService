package com.example.orderservice;

public class TestLambda {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int x =10;
		int y =5;
		
		IFuncionLambda ifuncio = (a,b) -> {
			System.out.println(a+b+b);
		};
		
		ifuncio.suma(x, y);
		
	}

}
