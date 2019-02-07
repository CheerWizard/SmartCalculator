package com.example.jeremy.smartcalculator.models;

public class NumericExpression implements IExpression {

	private String number;
	
	public NumericExpression(int numberInteger) {
		this.number = String.valueOf(numberInteger);
	}
	
	public NumericExpression(String numberString) {
		this.number = numberString;
	}
	
	public NumericExpression(double numberDouble) {
		this.number = String.valueOf(numberDouble);
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getNumber() {
		return number;
	}

	@Override
	public Value toValue() {
		return new Value(number);
	}

}
