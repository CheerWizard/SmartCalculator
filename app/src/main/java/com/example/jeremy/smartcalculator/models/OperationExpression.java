package com.example.jeremy.smartcalculator.models;

public class OperationExpression implements IExpression{

	private String operationName;
	
	public OperationExpression(String operationName) {
		this.operationName = operationName;
	}
	
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	
	public String getOperationName() {
		return operationName;
	}

	@Override
	public Value toValue() {
		return new Value(operationName);
	}
}
