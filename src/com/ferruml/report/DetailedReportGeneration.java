package com.ferruml.report;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.ferruml.system.currentuser.User;
import com.ferruml.system.hardware.HWID;
import com.ferruml.system.hardware.Win32_BIOS;
import com.ferruml.system.hardware.Win32_Baseboard;
import com.ferruml.system.hardware.Win32_CacheMemory;
import com.ferruml.system.hardware.Win32_DiskDrive;
import com.ferruml.system.hardware.Win32_PhysicalMemory;
import com.ferruml.system.hardware.Win32_PortConnector;
import com.ferruml.system.hardware.Win32_Printer;
import com.ferruml.system.hardware.Win32_Processor;
import com.ferruml.system.hardware.Win32_SoundDevice;
import com.ferruml.system.hardware.Win32_VideoController;
import com.ferruml.system.network.Win32_NetworkAdapter;
import com.ferruml.system.operatingsystem.Win32_OperatingSystem;
import com.ferruml.system.operatingsystem.Win32_TimeZone;

public class DetailedReportGeneration {
	
	private DetailedReportGeneration() {
		throw new IllegalStateException("Utility Class");
	}
	
	protected static void generate(JProgressBar progress, JLabel label, JButton button, JTextArea errorDisplay, JButton btnShowReport) {
		new Thread(()-> {
		try(FileWriter fos = new FileWriter(User.getUsername()+"-"+"FerrumL-Detailed-Report.txt");){
			PrintWriter report = new PrintWriter(fos);
			
			button.setEnabled(false);
			btnShowReport.setEnabled(false);
			button.setText("Generating");
			
			SwingUtilities.invokeLater(() -> label.setText("Generating HWID"));
			SwingUtilities.invokeLater(() -> progress.setValue(2));
			reportHardwareID(report, errorDisplay);
			
			SwingUtilities.invokeLater(() -> label.setText("Gathering OS Info"));
			SwingUtilities.invokeLater(() -> progress.setValue(6));
			reportOS(report, errorDisplay);
			
			SwingUtilities.invokeLater(() -> label.setText("Gathering User Info"));
			SwingUtilities.invokeLater(() -> progress.setValue(13));
			reportUser(report, errorDisplay);
			
			SwingUtilities.invokeLater(() -> label.setText("Gathering TimeZone Info"));
			SwingUtilities.invokeLater(() -> progress.setValue(20));
			reportTimeZone(report, errorDisplay);
			
			SwingUtilities.invokeLater(() -> label.setText("Gathering CPU Info"));
			SwingUtilities.invokeLater(() -> progress.setValue(28));
			reportCPU(report, errorDisplay);
			
			SwingUtilities.invokeLater(() -> label.setText("Gathering CPU Cache Info"));
			SwingUtilities.invokeLater(() -> progress.setValue(35));
			reportCPUCache(report, errorDisplay);
			
			SwingUtilities.invokeLater(() -> label.setText("Gathering RAM Info"));
			SwingUtilities.invokeLater(() -> progress.setValue(42));
			reportRAM(report, errorDisplay);
			
			SwingUtilities.invokeLater(() -> label.setText("Gathering Video Controller Info"));
			SwingUtilities.invokeLater(() -> progress.setValue(50));
			reportGPU(report, errorDisplay);
			
			SwingUtilities.invokeLater(() -> label.setText("Gathering Mainboard Info"));
			SwingUtilities.invokeLater(() -> progress.setValue(57));
			reportMotherboard(report, errorDisplay);
			
			SwingUtilities.invokeLater(() -> label.setText("Gathering BIOS Info"));
			SwingUtilities.invokeLater(() -> progress.setValue(64));
			reportBIOS(report, errorDisplay);
			
			SwingUtilities.invokeLater(() -> label.setText("Gathering I/O Info"));
			SwingUtilities.invokeLater(() -> progress.setValue(70));
			reportIO(report, errorDisplay);
			
			SwingUtilities.invokeLater(() -> label.setText("Gathering Network Info"));
			SwingUtilities.invokeLater(() -> progress.setValue(77));
			reportNetwork(report, errorDisplay);
			
			SwingUtilities.invokeLater(() -> label.setText("Gathering Storage Info"));
			SwingUtilities.invokeLater(() -> progress.setValue(85));
			reportDisk(report, errorDisplay);
			
			SwingUtilities.invokeLater(() -> label.setText("Gathering Printer Info"));
			SwingUtilities.invokeLater(() -> progress.setValue(90));
			reportPrinter(report, errorDisplay);
			
			SwingUtilities.invokeLater(() -> label.setText("Gathering Audio Info"));
			SwingUtilities.invokeLater(() -> progress.setValue(95));
			reportSound(report, errorDisplay);
			
			report.close();
			
			button.setEnabled(true);
			btnShowReport.setEnabled(true);
			if(!errorDisplay.getText().contains("ERROR"))
				label.setText("Finished");
			else
				label.setText("Finished With Errors");
			SwingUtilities.invokeLater(() -> progress.setValue(100));
			button.setText("Generate");
			
		} catch (IOException e) {
			errorDisplay.setText("REPORT FILE ERROR: "+e.getMessage());
		}
	}).start();
	}
	
	private static void reportHardwareID(PrintWriter report, JTextArea errorDisplay){
		report.println("----------------------HARDWARE ID----------------------");
		try {
			String hwid = HWID.getHardwareID();
			report.println(hwid);
			if(hwid.isBlank() || hwid.isEmpty())
				errorDisplay.append("HWID Generation: Unavailable\n");
			else
				errorDisplay.append("HWID Generation: Success\n");
		} catch (InterruptedException | ExecutionException e) {
			Throwable cause = e.getCause();
			if(cause instanceof IOException || cause instanceof IndexOutOfBoundsException) {
				report.println("Could not fetch Hardware ID: "+cause.getMessage());
				errorDisplay.append("HWID ERROR: Unable to fetch HWID Info\n"+cause.getMessage()+"\n");
			}
			else {
				report.println("Could not fetch Hardware ID: "+e.getMessage());
				errorDisplay.append("HWID ERROR: Unable to fetch HWID Info\n"+e.getMessage()+"\n");
				Thread.currentThread().interrupt();
			}
		}
	}

	private static void reportSound(PrintWriter report, JTextArea errorDisplay) {
		List<String> deviceIDs;
		Map<String, String> currentAudio = Collections.emptyMap(); //this prevents null pointer exception
		
		report.println("----------------------AUDIO INFO------------------------");
		try {
			deviceIDs = Win32_SoundDevice.getSoundDeviceID();	
			for(String currentID : deviceIDs) {
				currentAudio = Win32_SoundDevice.getCurrentAudioDevice(currentID);
				for(Map.Entry<String, String> entry: currentAudio.entrySet())
					report.println(entry.getKey()+": "+entry.getValue());
				report.println();
			}
			if(currentAudio.isEmpty())
				errorDisplay.append("Audio Info: Unavailable");
			else
				errorDisplay.append("Audio Info: Success");
		}catch (IOException | IndexOutOfBoundsException e) {
			report.println("Unable to fetch Audio Info: "+e);
			errorDisplay.append("AUDIO ERROR: Unable to fetch Audio Info\n"+e);
		}
	}

	private static void reportPrinter(PrintWriter report, JTextArea errorDisplay) {
		List<String> deviceIDs;
		Map<String, String> currentPrinter = Collections.emptyMap();
		
		report.println("----------------------PRINTER INFO------------------------");
		try {
		deviceIDs = Win32_Printer.getDeviceIDList();
		for(String currentID : deviceIDs) {
			currentPrinter = Win32_Printer.getCurrentPrinter(currentID);
			for(Map.Entry<String, String> entry: currentPrinter.entrySet())
				report.println(entry.getKey()+": "+entry.getValue());
			report.println();
	    }
		if(currentPrinter.isEmpty())
			errorDisplay.append("Printer Info: Unavailable\n");
		else
			errorDisplay.append("Printer Info: Success\n");
	  }catch (IOException | IndexOutOfBoundsException e) {
		  report.println("Unable to fetch Audio Info: "+e);
		  errorDisplay.append("PRINTER ERROR: Unable to fetch Printer Info\n"+e+"\n");
	  }
	}

	private static void reportDisk(PrintWriter report, JTextArea errorDisplay) {
		List<String> diskID;
		Map<String, String> disk = Collections.emptyMap();
		
		report.println("----------------------STORAGE INFO------------------------");
		try {
			diskID = Win32_DiskDrive.getDriveID();
			for (String id : diskID) {
				disk = Win32_DiskDrive.getDrive(id);
				for (Map.Entry<String, String> entry : disk.entrySet())
					report.println(entry.getKey() + ": " + entry.getValue());
				report.println();
			}
			if(disk.isEmpty())
				errorDisplay.append("Storage Info: Unavailable\n");
			else
				errorDisplay.append("Storage Info: Success\n");
		} catch (IOException | IndexOutOfBoundsException e) {
			report.println("Unable to fetch Storage Info: "+e);
			errorDisplay.append("STORAGE ERROR: Unable to fetch Storage Info\n"+e+"\n");
		}
	}

	private static void reportNetwork(PrintWriter report, JTextArea errorDisplay) {
		List<String> deviceIDs;
		Map<String, String> networkAdapter = Collections.emptyMap();
		report.println("----------------------NETWORK INFO------------------------");
		try {
			deviceIDs = Win32_NetworkAdapter.getAdapterID();
			for (String currentID : deviceIDs) {
				networkAdapter = Win32_NetworkAdapter.getNetworkAdapters(currentID);
				
				for (Map.Entry<String, String> entry : networkAdapter.entrySet())
					report.println(entry.getKey() + ": " + entry.getValue());
				report.println();
			}
			if(networkAdapter.isEmpty())
				errorDisplay.append("Network Info: Unavailable\n");
			else
				errorDisplay.append("Network Info: Success\n");
		} catch (IOException | IndexOutOfBoundsException e) {
			report.println("Could not fetch Network Info: "+e);
			errorDisplay.append("NETWORK ERROR: Unable to fetch Network Info\n"+e+"\n");
		}
	}

	private static void reportIO(PrintWriter report, JTextArea errorDisplay) {
		List<String> portID;
		Map<String, String> ports= Collections.emptyMap();
		
		report.println("----------------------MAINBOARD I/O INFO------------------------");
		try {
			portID = Win32_PortConnector.getBaseboardPortID();
			for (String id : portID) {
				ports = Win32_PortConnector.getBaseboardPorts(id);
				for (Map.Entry<String, String> port : ports.entrySet())
					report.println(port.getKey() + ": " + port.getValue());
				report.println();
			}
			if(ports.isEmpty())
				errorDisplay.append("I/O Info: Unavailable\n");
			else
				errorDisplay.append("I/O Info: Success\n");
		} catch (IOException | IndexOutOfBoundsException e) {
			report.println("Could not fetch I/O Info: "+e);
			errorDisplay.append("I/O ERROR: Unable to fetch Motherboard Info\n"+e+"\n");
		}	
	}

	private static void reportBIOS(PrintWriter report, JTextArea errorDisplay) {
		report.println("----------------------BIOS INFO------------------------");
		try {
			Map<String, String> BIOS = Win32_BIOS.getPrimaryBIOS();
			for (Map.Entry<String, String> entry : BIOS.entrySet())
				report.println(entry.getKey() + ": " + entry.getValue());
			if(BIOS.isEmpty())
				errorDisplay.append("BIOS Info: Unavailable\n");
			else
				errorDisplay.append("BIOS Info: Success\n");
		} catch (IOException | IndexOutOfBoundsException e) {
			report.println("Could not fetch BIOS Info: "+e);
			errorDisplay.append("BIOS ERROR: Unable to fetch BIOS Info\n"+e+"\n");
		}
	}

	private static void reportMotherboard(PrintWriter report, JTextArea errorDisplay) {
		report.println("----------------------MAINBOARD------------------------");
		try {
			Map<String, String> motherboard = Win32_Baseboard.getMotherboard();
			for (Map.Entry<String, String> entry : motherboard.entrySet())
				report.println(entry.getKey() + ": " + entry.getValue());
			
			if(motherboard.isEmpty())
				errorDisplay.append("Mainboard Info: Unavailable\n");
			else
				errorDisplay.append("Mainboard Info: Success\n");
		} catch (IOException | IndexOutOfBoundsException e) {
			report.println("Could not fetch Motherboard Info : "+e);
			errorDisplay.append("MAINBOARD ERROR: Unable to fetch Motherboard Info\n"+e+"\n");
		}
	}

	private static void reportGPU(PrintWriter report, JTextArea errorDisplay) {
		List<String> gpuIDs;
		Map<String, String> currentGPU = Collections.emptyMap();
		
		report.println("----------------------VIDEO CONTROLLER------------------------");
		try {
			gpuIDs = Win32_VideoController.getGPUID();
			for (String currentID : gpuIDs) {
				currentGPU = Win32_VideoController.getGPU(currentID);
				for (Map.Entry<String, String> entry : currentGPU.entrySet())
					report.println(entry.getKey() + ": " + entry.getValue());
			}
			if(currentGPU.isEmpty())
				errorDisplay.append("GPU Info: Unavailable\n");
			else
				errorDisplay.append("GPU Info: Success\n");
		} catch (IOException | IndexOutOfBoundsException e) {
			report.println("Could not fetch GPU Info : "+e);
			errorDisplay.append("GPU ERROR: Unable to fetch VideoCard Info\n"+e+"\n");
		}
	}

	private static void reportRAM(PrintWriter report, JTextArea errorDisplay) {
		List<String> memoryID;
		Map<String, String> memory = Collections.emptyMap();
		
		report.println("----------------------SPD------------------------");
		try {
			memoryID=Win32_PhysicalMemory.getTag();
			for (String id : memoryID) {
				memory = Win32_PhysicalMemory.getMemory(id);
				for (Map.Entry<String, String> entry : memory.entrySet())
					report.println(entry.getKey() + ": " + entry.getValue());
				report.println();
			}
			if(memory.isEmpty())
				errorDisplay.append("Memory Info: Unavailable\n");
			else
				errorDisplay.append("Memory Info: Success\n");
		} catch (IOException | IndexOutOfBoundsException e) {
			report.println("Could not fetch Memory Info : "+e);
			errorDisplay.append("MEMORY ERROR: Unable to fetch Memory Info\n"+e+"\n");
		}
	}

	private static void reportCPUCache(PrintWriter report, JTextArea errorDisplay) {
		List<String> cacheIDs;
		
		Map<String, String> cache = Collections.emptyMap();
		report.println("----------------------CPU CACHE------------------------");
		try {
			cacheIDs = Win32_CacheMemory.getCacheID();
		
			for(String currentCacheID : cacheIDs) {
				cache = Win32_CacheMemory.getCPUCache(currentCacheID);
				for(Map.Entry<String, String> entry: cache.entrySet())
					report.println(entry.getKey()+": "+entry.getValue());
				report.println();
			}
			if(cache.isEmpty())
				errorDisplay.append("CPU Cache Info: Unavailable\n");
			else
				errorDisplay.append("CPU Cache Info: Success\n");
		} catch (IOException | IndexOutOfBoundsException e) {
			report.println("Could not fetch CPU Cache: "+e);
			errorDisplay.append("CPU CACHE ERROR: Unable to fetch CPU Cache Info\n"+e+"\n");
		}
	}

	private static void reportOS(PrintWriter report, JTextArea errorDisplay) {
		List<String> oslist;
		Map<String, String> osinfo = Collections.emptyMap();
		
		report.println("----------------------OS INFO------------------------");
		try {
			oslist = Win32_OperatingSystem.getOSList();
			for (String currentOS : oslist) {
				osinfo = Win32_OperatingSystem.getOSInfo(currentOS);
				for (Map.Entry<String, String> entry : osinfo.entrySet())
					report.println(entry.getKey() + ": " + entry.getValue());
			}
			if(osinfo.isEmpty())
				errorDisplay.append("OS Info: Unavailable\n");
			else
				errorDisplay.append("OS Info: Success\n");
		} catch (IOException | IndexOutOfBoundsException e) {
			report.println("Could not fetch OS Info: "+e);
			errorDisplay.append("OS ERROR: Unable to fetch Operating System Info\n"+e+"\n");
		}
	}
	
	private static void reportCPU(PrintWriter report, JTextArea errorDisplay) {
		List<String> deviceIDs;
		Map<String, String> currentCPU = Collections.emptyMap();
		
		report.println("----------------------CPU INFO------------------------");
		try {
			deviceIDs = Win32_Processor.getProcessorList();
			for (String currentID : deviceIDs) {
				currentCPU = Win32_Processor.getCurrentProcessor(currentID);
				for (Map.Entry<String, String> entry : currentCPU.entrySet())
					report.println(entry.getKey() + ": " + entry.getValue());
				report.println();
			}
			if(currentCPU.isEmpty())
				errorDisplay.append("CPU Info: Unavailable\n");
			else
				errorDisplay.append("CPU Info: Success\n");
		} catch (IOException | IndexOutOfBoundsException e) {
			report.println("Could not fetch CPU Info: "+e);
			errorDisplay.append("CPU ERROR: Unable to fetch CPU Info\n"+e+"\n");
		}
	}
	
	private static void reportTimeZone(PrintWriter report, JTextArea errorDisplay) {
		Map<String, String> currentTimeZone= Collections.emptyMap();
		
		report.println("----------------------TIMEZONE------------------------");
		try {
			currentTimeZone= Win32_TimeZone.getOSTimeZone();
			for (Map.Entry<String, String> entry : currentTimeZone.entrySet())
				report.println(entry.getKey() + ": " + entry.getValue());
			
			if(currentTimeZone.isEmpty())
				errorDisplay.append("Time-zone Info: Unavailable\n");
			else
				errorDisplay.append("Time-zone Info: Success\n");
		} catch (IOException | IndexOutOfBoundsException e) {
			report.println("Could not fetch Timezone Info : "+e);
			errorDisplay.append("TIMEZONE ERROR: Unable to fetch TimeZone Info\n"+e+"\n");
		}
    }

	private static void reportUser(PrintWriter report, JTextArea errorDisplay){
		report.println("----------------------USER INFO------------------------");
		report.println("Current Username: "+User.getUsername());
		report.println("User Home Directory: "+User.getHome());
		errorDisplay.append("User Info: Success\n");
	}
}
