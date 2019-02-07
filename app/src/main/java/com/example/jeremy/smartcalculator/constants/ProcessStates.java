package com.example.jeremy.smartcalculator.constants;

public final class ProcessStates {
    public static final class Successfull {
        public static final int STATUS_CALCULATED = 1;
        public static final int STATUS_EXPRESSION_ADDED = 2;
        public static final int STATUS_CLEARED_ALL = 3;
    }
    public static final class Failed {
        public static final int STATUS_NOT_ENOUGH_EXPRESSION = -1;
        public static final int STATUS_INCORRECT_INPUT = -2;
    }
}
