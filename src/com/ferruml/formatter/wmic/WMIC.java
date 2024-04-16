package com.ferruml.formatter.wmic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ferruml.error.ErrorLog;

public class WMIC{
	private WMIC() {
		throw new IllegalStateException("Utility Class");
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
				
				ErrorLog cmdError = new ErrorLog();
				cmdError.log("\n"+WMIC_Class+"-"+Key+"\n"+errorList.toString()+"\nProcess Exited with code:"+exitCode+"\n\n");
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
	
	public static List<String> getIDWhere(String WMIC_Class, String determinantProperty, String determinantValue,  String extractProperty) throws IOException, IndexOutOfBoundsException{
		String[] command = {"cmd","/c", "wmic path "+WMIC_Class+" where "+determinantProperty+"="+"\""+determinantValue+"\""+" get "+extractProperty+" /format:list"};
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
				
				ErrorLog cmdError = new ErrorLog();
				cmdError.log("\n"+WMIC_Class+"-"+extractProperty+"\n"+errorList.toString()+"\nProcess Exited with code:"+exitCode+"\n\n");
			}
			
		}catch (InterruptedException e) {
			System.err.println("\n"+WMIC_Class+"-"+extractProperty+"\n"+e.getMessage()+"\n\n");
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
				
				ErrorLog cmdError = new ErrorLog();
				cmdError.log("\n"+WMIC_Class+"-"+WMIC_Attributes+"\n"+errorList.toString()+"\nProcess Exited with code:"+exitCode+"\n\n");
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
	
	
	public static Map<String, String> getWhere(String WMIC_Class, String determinantProperty, String determinantValue, String WMIC_Attributes) throws IOException, IndexOutOfBoundsException {
		
		String[] command = {"cmd","/c", "wmic path "+WMIC_Class+" where "+determinantProperty+"="+"\""+determinantValue+"\""+" get "+WMIC_Attributes+" /format:list"};
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
				
				ErrorLog cmdError = new ErrorLog();
				cmdError.log("\n"+WMIC_Class+"-"+determinantProperty+"-"+determinantValue+"-"+WMIC_Attributes+"\n"+errorList.toString()+"\nProcess Exited with code:"+exitCode+"\n\n");
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