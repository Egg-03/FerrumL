package com.egg.formatter.wmic;

import java.io.IOException;

public class WMIC {
	private WMIC() {
		throw new IllegalStateException("Utility Class");
	}
	
	public static String getValues(String WMIC_Class, String WMIC_Attribute) throws IOException {
		return WMICFormat.accessrunCommand(WMIC_Class, WMIC_Attribute);
	}
}
