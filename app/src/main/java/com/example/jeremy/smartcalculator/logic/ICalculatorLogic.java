package com.example.jeremy.smartcalculator.logic;

import com.example.jeremy.smartcalculator.models.IExpression;

public interface ICalculatorLogic {
	double getResult();
	String getErased(String text);
	void add(IExpression expression);
	void clear();
}
