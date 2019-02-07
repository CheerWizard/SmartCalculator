package com.example.jeremy.smartcalculator.models;

import android.support.annotation.NonNull;

public class Value {
	
	private String info;
	
	public Value(String info) {
		this.info = info;
	}
	@NonNull
	public String toString() {
		return info;
	}
	
	public double toDouble() {
		return Double.valueOf(info);
	}
	
	public int toInteger() {
		return Integer.valueOf(info);
	}
	
}
