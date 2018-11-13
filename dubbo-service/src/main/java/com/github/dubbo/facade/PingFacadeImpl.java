package com.github.dubbo.facade;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import model.Response;

import org.springframework.stereotype.Component;

/**
 * PingServie
 * 
 * @author yuzhupeng
 */
@Component
public class PingFacadeImpl implements PingFacade {

    /**
     * 打印服务提供者IP
     */
    public Response showServiceIp() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address) {
                        Response response = new Response();
                        response.setName("IPADRRESS");
                        response.setIpAdrress(ip.getHostAddress());
                        return response;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
