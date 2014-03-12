package com.leon.run;

import java.util.Locale;
import java.util.ResourceBundle;
import com.leon.MainFrame;

public class RunTest {
	public static ResourceBundle bundle = null;
	
	public static void main(String args[]){
		
		Locale myLocale = Locale.getDefault();
		RunTest.bundle = ResourceBundle.getBundle("messages",myLocale);
		MainFrame mf = new MainFrame();
		mf.init();
	}
}
