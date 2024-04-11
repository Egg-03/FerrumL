package com.egg.formatter.wmic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class WMICFormat{
	private WMICFormat() {
		throw new IllegalStateException("Utility Class");
	}
	
	private static String runCommand(String WMIC_Class, String WMIC_Attribute) throws IOException {
  
		String[] command = {"cmd","/c", "wmic "+WMIC_Class+" get "+WMIC_Attribute+" /format:list"};
		Process process = Runtime.getRuntime().exec(command);
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		String currentLine;
		String actualName = "";
		
		while((currentLine=br.readLine())!=null)
			if(!currentLine.isBlank() || !currentLine.isEmpty())
				actualName = currentLine;
		
		br.close();
		return actualName.substring(actualName.indexOf("=")+1).strip();
	}
	
	//access the private method runCommand() from here
	protected static String accessrunCommand (String WMIC_Class, String WMIC_Attribute) throws IOException{
		return runCommand(WMIC_Class, WMIC_Attribute);
	}
}