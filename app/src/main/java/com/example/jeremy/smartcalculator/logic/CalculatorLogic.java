package com.example.jeremy.smartcalculator.logic;

import android.os.Handler;

import com.example.jeremy.smartcalculator.constants.Symbols;
import com.example.jeremy.smartcalculator.constants.OperationType;
import com.example.jeremy.smartcalculator.constants.ProcessStates;
import com.example.jeremy.smartcalculator.models.IExpression;
import com.example.jeremy.smartcalculator.models.NumericExpression;
import com.example.jeremy.smartcalculator.utils.containers.IContainer;
import com.example.jeremy.smartcalculator.utils.identifiers.NumberIdentifier;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CalculatorLogic implements ICalculatorLogic {

	private IContainer<IExpression> container;
	private Handler handler;
	private Executor executor;
	//global var for calculating method
	private double result;

	public CalculatorLogic(IContainer<IExpression> container , Handler handler) {
		this.container = container;
		this.handler = handler;
		executor = Executors.newSingleThreadExecutor();
	}

	@Override
	public double getResult() {
		calculate();
		return result;
	}

	private void calculate() {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				result = 0;
				int i = 0;
				int j = 1;
				if (container.size() > 2) {
					if (NumberIdentifier.isNumber(container.get(i).toValue().toString()) && NumberIdentifier.isNumber(container.get(j + 1).toValue().toString())) {
						while (j < container.size() - 1) {
							result = result + startCalculating(container.get(i), container.get(j), container.get(j + 1)).toValue().toDouble();
							i = i + 2;
							j = j + 2;
						}
						handler.sendEmptyMessage(ProcessStates.Successfull.STATUS_CALCULATED);
					}
					else handler.sendEmptyMessage(ProcessStates.Failed.STATUS_INCORRECT_INPUT);
				}
				else handler.sendEmptyMessage(ProcessStates.Failed.STATUS_NOT_ENOUGH_EXPRESSION);
			}
		});
	}

	@Override
	public void add(final IExpression expression) {
	    container.add(expression);
	    handler.sendEmptyMessage(ProcessStates.Successfull.STATUS_EXPRESSION_ADDED);
	}

	@Override
	public String getErased(String text) {
		char[] previousChars;
		char[] resultChars;
		previousChars = text.toCharArray();
		if (previousChars.length == 0) resultChars = Symbols.EMPTY.toCharArray();
		else {
			resultChars = new char[previousChars.length - 1];
			System.arraycopy(previousChars, 0, resultChars, 0, previousChars.length - 1);
		}
		return new String(resultChars);
	}

	@Override
	public void clear() {
	    container.clear();
	    handler.sendEmptyMessage(ProcessStates.Successfull.STATUS_CLEARED_ALL);
	}

	private IExpression startCalculating(IExpression number1IExpression , IExpression operationIExpression , IExpression number2IExpression) {
		IExpression expression = null;
		switch (operationIExpression.toValue().toString()) {
			case OperationType.PLUS:
				expression = new NumericExpression(number1IExpression.toValue().toDouble() + number2IExpression.toValue().toDouble());
				break;
			case OperationType.MINUS:
				expression = new NumericExpression(number1IExpression.toValue().toDouble() - number2IExpression.toValue().toDouble());
				break;
			case OperationType.MULTIPLY:
				expression = new NumericExpression(number1IExpression.toValue().toDouble() * number2IExpression.toValue().toDouble());
				break;
			case OperationType.DIVIDE:
				expression = new NumericExpression(number1IExpression.toValue().toDouble() / number2IExpression.toValue().toDouble());
				break;
			case OperationType.POWER:
				expression = new NumericExpression(Math.pow(number1IExpression.toValue().toDouble(), number2IExpression.toValue().toDouble()));
				break;
			case OperationType.SQRT:
				expression = new NumericExpression(Math.sqrt(number1IExpression.toValue().toDouble()));
				break;
			case OperationType.SIN:
				expression = new NumericExpression(Math.sin(number1IExpression.toValue().toDouble()));
				break;
			case OperationType.COS:
				expression = new NumericExpression(Math.cos(number1IExpression.toValue().toDouble()));
				break;
			case OperationType.TAN:
				expression =  new NumericExpression(Math.tan(number1IExpression.toValue().toDouble()));
				break;
			case OperationType.CTAN:
				expression =  new NumericExpression(1 / (Math.tan(number1IExpression.toValue().toDouble())));
				break;
			case OperationType.LN:
				expression = new NumericExpression(Math.log(number1IExpression.toValue().toDouble()));
				break;
			case OperationType.LOG:
				expression = new NumericExpression(Math.log10(number1IExpression.toValue().toDouble()));
				break;
		}
		return expression;
	}
}
