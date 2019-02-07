package com.example.jeremy.smartcalculator.presenters;

import android.os.Handler;

import com.example.jeremy.smartcalculator.constants.AnimationType;
import com.example.jeremy.smartcalculator.logic.CalculatorLogic;
import com.example.jeremy.smartcalculator.logic.ICalculatorLogic;
import com.example.jeremy.smartcalculator.models.NumericExpression;
import com.example.jeremy.smartcalculator.models.OperationExpression;
import com.example.jeremy.smartcalculator.utils.containers.ExpressionContainer;
import com.example.jeremy.smartcalculator.utils.identifiers.NumberIdentifier;
import com.example.jeremy.smartcalculator.IContract;
import com.example.jeremy.smartcalculator.utils.identifiers.OperationIdentifier;

public class CalculatorPresenter implements IContract.IPresenter {

	private IContract.IView view;
	private ICalculatorLogic calculatorLogic;

	public CalculatorPresenter(IContract.IView view , Handler handler) {
		this.view = view;
		calculatorLogic = new CalculatorLogic(new ExpressionContainer() , handler);
	}

	public void onTouchNumberExpressionBtn(String expression) {
	   	view.animate(AnimationType.RENDER_NUMBER , expression);
    }

    public void onTouchOperationExpressionBtn(String expression) {
	   	view.animate(AnimationType.RENDER_OPERATION , expression);
    }

    public void onTouchNextExpressionBtn(String expression) {
		addExpression(expression);
		view.animate(AnimationType.RENDER_EMPTY_FIELD , null);
	}

	public void onTouchEraseBtn(String expression) {
		view.animate(AnimationType.RENDER_ERASE , calculatorLogic.getErased(expression));
	}

	public void onTouchEqualsBtn(String expression) {
		addExpression(expression);
		final String result = String.valueOf(calculatorLogic.getResult());
		calculatorLogic.clear();
		view.animate(AnimationType.RENDER_RESULT , result);
	}

	private void addExpression(String expression) {
		if (NumberIdentifier.isNumber(expression)) calculatorLogic.add(new NumericExpression(expression));
		else if (OperationIdentifier.isOperation(expression)) calculatorLogic.add(new OperationExpression(expression));
	}
	
	@Override
	public void onClear() {
		calculatorLogic.clear();
		view.animate(AnimationType.CLEAR_FIELD , null);
	}
}
