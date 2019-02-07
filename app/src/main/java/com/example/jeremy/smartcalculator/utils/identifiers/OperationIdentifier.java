package com.example.jeremy.smartcalculator.utils.identifiers;

import com.example.jeremy.smartcalculator.constants.OperationType;

public final class OperationIdentifier {

	public static boolean isOperation(String info) {
		return (isArithmeticOperation(info) || 
				isTrigonometricOperation(info) ||
				isSingleMathOperation(info));
	}
	
	private static boolean isTrigonometricOperation(String info) {
		return (info == OperationType.COS ||
				info == OperationType.SIN ||
				info == OperationType.CTAN ||
				info == OperationType.TAN );
	}
	
	private static boolean isArithmeticOperation(String info) {
		return (info == OperationType.PLUS ||
				info == OperationType.MINUS ||
				info == OperationType.MULTIPLY ||
				info == OperationType.DIVIDE ||
				info == OperationType.POWER);
	}
	
	private static boolean isSingleMathOperation(String info) {
		return (info == OperationType.LN ||
				info == OperationType.LOG ||
				info == OperationType.SQRT);
	}
}
