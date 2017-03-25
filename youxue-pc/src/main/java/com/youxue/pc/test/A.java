package com.youxue.pc.test;

public class A
{
	static A test = new A();
	static int a;
	static int b = 0;

	public A()
	{
		a++;
		b++;
	}

	public static void main(String[] args)
	{
		System.out.println(A.a + " " + A.b);
	}
}
