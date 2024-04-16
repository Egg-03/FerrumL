package com.ferruml.error;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorLog {
	private static boolean use_flag = false;
	
	public synchronized void log(String message) {
		while(ErrorLog.use_flag)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
				}
		
		use_flag = true;
		
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		try(FileOutputStream fos = new FileOutputStream("FerrumL_ERRORLOG-"+ldt.format(dtf2)+".log", true);) {
			
			String timestamp = ldt.format(dtf);
			
			fos.write(timestamp.getBytes());
			fos.write(message.getBytes());
		} catch (IOException e) {e.printStackTrace();} 
		finally {
			use_flag= false;
			notifyAll();
		}
	}
}