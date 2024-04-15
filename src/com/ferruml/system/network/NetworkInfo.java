package com.ferruml.system.network;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class NetworkInfo {
	private NetworkInfo() {
		throw new IllegalStateException("Utility Class");
	}
	
	public static void networkAdapter() throws SocketException {
		Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets))
        	displayInterfaceInformation(netint);   
	}

	private static void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
		List<InterfaceAddress> intfAddress = netint.getInterfaceAddresses();
		for(InterfaceAddress list: intfAddress) {
			if(list.getAddress()!=null && !netint.getDisplayName().startsWith("Software") &&!netint.isVirtual()) {
				System.out.println("Adapter Name : "+netint.getDisplayName());
				System.out.println("Is Up : "+netint.isUp());
				System.out.println("Details : "+list);
			}
			System.out.println();
		}
	}
	
}