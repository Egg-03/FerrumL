package com.ferruml.formatter.wmic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WMIC{
	private WMIC() {
		throw new IllegalStateException("Utility Class");
	}
	
	public static Map<String, String> get(String WMIC_Class, String WMIC_Attributes) throws IOException, IndexOutOfBoundsException {
		
		String[] command = {"cmd","/c", "wmic "+WMIC_Class+" get "+WMIC_Attributes+" /format:list"};
		Process process = Runtime.getRuntime().exec(command);
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		String currentLine;
		
		String key = "";
		String value = "";
		Map<String, String> property = new LinkedHashMap<>();
		
		while((currentLine=br.readLine())!=null)
			if(!currentLine.isBlank() || !currentLine.isEmpty()) {
				if(currentLine.contains("=")) {
					key = currentLine.substring(0, currentLine.indexOf("=")).strip();
					value = currentLine.substring(currentLine.indexOf("=")+1).strip();
					property.put(key, value);
				}
				else {
					value=value.concat(currentLine);
					property.replace(key, value);
				}
			}
		br.close();
		return property;
	}
	
	public static List<String> getID(String WMIC_Class, String Key) throws IOException, IndexOutOfBoundsException{
		String[] command = {"cmd","/c", "wmic "+WMIC_Class+" get "+Key+" /format:list"};
		Process process = Runtime.getRuntime().exec(command);
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		String currentLine;
		
		String value = "";
		List<String> ID = new ArrayList<>();
		
		while((currentLine=br.readLine())!=null)
			if(!currentLine.isBlank() || !currentLine.isEmpty()) {
				if(currentLine.contains("=")) {
					value = currentLine.substring(currentLine.indexOf("=")+1).strip();
					ID.add(value);
				}
				else {
					value=value.concat(currentLine);
					ID.add(ID.indexOf(ID.getLast()), value);
				}
			}
		br.close();
		return ID;
	}
	
	public static Map<String, String> getWhere(String WMIC_Class, String Precedent, String Antecedent, String WMIC_Attributes) throws IOException, IndexOutOfBoundsException {
		
		String[] command = {"cmd","/c", "wmic "+WMIC_Class+" where "+Precedent+"="+"\""+Antecedent+"\""+" get "+WMIC_Attributes+" /format:list"};
		Process process = Runtime.getRuntime().exec(command);
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		String currentLine;
		
		String key = "";
		String value = "";
		Map<String, String> property = new LinkedHashMap<>();
		
		while((currentLine=br.readLine())!=null)
			if(!currentLine.isBlank() || !currentLine.isEmpty()) {
				if(currentLine.contains("=")) {
					key = currentLine.substring(0, currentLine.indexOf("=")).strip();
					value = currentLine.substring(currentLine.indexOf("=")+1).strip();
					property.put(key, value);
				}
				else {
					value=value.concat(currentLine);
					property.replace(key, value);
				}
			}
		br.close();
		return property;
	}
}