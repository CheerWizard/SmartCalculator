package com.example.jeremy.smartcalculator;

import com.example.jeremy.smartcalculator.constants.AnimationType;

public interface IContract {
	//view
	interface IView {
		void animate(AnimationType animationType , String text);
	}
	//presenter
	interface IPresenter {
		void onClear();
	}
}
