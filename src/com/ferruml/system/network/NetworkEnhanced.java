package com.ferruml.system.network;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

final class NetworkEnhanced {
	
	private NetworkEnhanced() {
		throw new IllegalStateException("Utility Class");
	}

	protected static Map<String, String> getInterfaceAddress() throws SocketException {
		Map<String, String> inetDetails = new HashMap<>();
		Enumeration<NetworkInterface> adapters;
		adapters = NetworkInterface.getNetworkInterfaces();
		while(adapters.hasMoreElements()) {
			NetworkInterface currentAdapter = adapters.nextElement();
			if(!currentAdapter.isLoopback() && currentAdapter.isUp() && !currentAdapter.isVirtual()) {
				Enumeration<InetAddress> ip = currentAdapter.getInetAddresses();
				while(ip.hasMoreElements()) {
					InetAddress currentInet = ip.nextElement();
					if(!currentInet.isLoopbackAddress() && currentInet instanceof Inet4Address) {
						inetDetails.put(currentAdapter.getDisplayName(), currentInet.getHostAddress());
					}
				}
			}
		}
		return inetDetails;
    }
}
