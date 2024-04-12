package com.ferruml.formatter.wmic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WMIC{
	private WMIC() {
		throw new IllegalStateException("Utility Class");
	}
	
	public static Map<String, String> get(String WMIC_Class, String WMIC_Attributes) throws IOException, IndexOutOfBoundsException {
		
		String[] command = {"cmd","/c", "wmic path "+WMIC_Class+" get "+WMIC_Attributes+" /format:list"};
		Process process = Runtime.getRuntime().exec(command);
		
		try {
			int exitCode = process.waitFor();
			if(exitCode!=0) {
				BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				String errorLine;
     			List<String> errorList = new ArrayList<>();
				
				while((errorLine=error.readLine())!=null)
					if(!errorLine.isBlank() || !errorLine.isEmpty())
						errorList.add(errorLine);
				
				error.close();
				
				System.err.println("\n"+WMIC_Class+"-"+WMIC_Attributes+"\n"+errorList.toString()+"\nProcess Exited with code:"+exitCode+"\n");
				return Collections.emptyMap();
			}
			
		}catch (InterruptedException e) {
			System.err.println("\n"+WMIC_Class+"-"+WMIC_Attributes+"\n"+e.getMessage()+"\n\n");
			Thread.currentThread().interrupt();
			return Collections.emptyMap();
		}
		
		//get cmd contents
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
		String[] command = {"cmd","/c", "wmic path "+WMIC_Class+" get "+Key+" /format:list"};
		Process process = Runtime.getRuntime().exec(command);
		try {
			int exitCode = process.waitFor();
			if(exitCode!=0) {
				BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				String errorLine;
     			List<String> errorList = new ArrayList<>();
				
				while((errorLine=error.readLine())!=null)
					if(!errorLine.isBlank() || !errorLine.isEmpty())
						errorList.add(errorLine);
				
				error.close();
				
				System.err.println("\n"+WMIC_Class+"-"+Key+"\n"+errorList.toString()+"\nProcess Exited with code:"+exitCode+"\n");
			}
			
		}catch (InterruptedException e) {
			System.err.println("\n"+WMIC_Class+"-"+Key+"\n"+e.getMessage()+"\n\n");
			Thread.currentThread().interrupt();
			return Collections.emptyList();
		}
		
		BufferedReader stream = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		String currentLine;
		
		String value = "";
		List<String> ID = new ArrayList<>();
		
		while((currentLine=stream.readLine())!=null)
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
		stream.close();
		return ID;
	}
	
	public static Map<String, String> getWhere(String WMIC_Class, String Precedent, String Antecedent, String WMIC_Attributes) throws IOException, IndexOutOfBoundsException {
		
		String[] command = {"cmd","/c", "wmic path "+WMIC_Class+" where "+Precedent+"="+"\""+Antecedent+"\""+" get "+WMIC_Attributes+" /format:list"};
		//TODO Remove this debug code later
		System.out.println(Arrays.toString(command));
		Process process = Runtime.getRuntime().exec(command);
		try {
			int exitCode = process.waitFor();
			if(exitCode!=0) {
				BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				String errorLine;
     			List<String> errorList = new ArrayList<>();
				
				while((errorLine=error.readLine())!=null)
					if(!errorLine.isBlank() || !errorLine.isEmpty())
						errorList.add(errorLine);
				
				error.close();
				
				System.err.println("\n"+WMIC_Class+"-"+Precedent+"-"+Antecedent+"-"+WMIC_Attributes+"\n"+errorList.toString()+"\nProcess Exited with code:"+exitCode+"\n");
			}
			
		}catch (InterruptedException e) {
			System.err.println("\n"+WMIC_Class+"-"+WMIC_Attributes+"\n"+e.getMessage()+"\n\n");
			Thread.currentThread().interrupt();
			return Collections.emptyMap();
		}
		
		BufferedReader stream = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		String currentLine;
		
		String key = "";
		String value = "";
		Map<String, String> property = new LinkedHashMap<>();
		
		while((currentLine=stream.readLine())!=null)
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
		stream.close();
		return property;
	}
}