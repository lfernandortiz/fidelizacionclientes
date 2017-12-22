package com.dromedicas.test;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TestConection {

	public static void main(String[] args) {
		int x = 50000;
		NumberFormat nf = new DecimalFormat("#,###");
		System.out.println(nf.format(x));

	}

}
