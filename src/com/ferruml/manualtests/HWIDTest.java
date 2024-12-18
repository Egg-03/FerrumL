package com.ferruml.manualtests;

import java.util.concurrent.ExecutionException;

import com.ferruml.system.hardware.HWID;

public class HWIDTest {
	public static void main(String[] args) {
		try {
			String hwid = HWID.getHardwareID();
			System.out.println(hwid);
			if(hwid.isBlank() || hwid.isEmpty())
				System.err.println("HWID Generation: Unavailable\n");
			else
				System.out.println("HWID Generation: Success\n");
		} catch (ExecutionException e) {
			System.err.println("Could not fetch Hardware ID: "+e.getCause().getMessage());
			System.err.println("HWID ERROR: Unable to fetch HWID Info\n"+e.getCause().getMessage()+"\n");
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
}
