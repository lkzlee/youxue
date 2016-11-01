package com.youxue.core.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class NetUtil
{
	public static Collection<String> getAllLocalIP() throws Exception
	{
		ArrayList<String> ar = new ArrayList<String>();
		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		if (netInterfaces == null)
		{
			return ar;
		}
		while (netInterfaces.hasMoreElements())
		{
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> inetAdrsEnum = ni.getInetAddresses();
			if (inetAdrsEnum == null || !inetAdrsEnum.hasMoreElements())
			{
				continue;
			}
			InetAddress ip = inetAdrsEnum.nextElement();
			if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1)
			{
				//				System.out.println("Interface " + ni.getName() + " seems to be InternetInterface. I'll take it...");
			}
			else
			{
				ar.add(ip.getHostAddress());
			}
		}
		return ar;
	}

	public static boolean containsIp(String ip)
	{
		Enumeration<NetworkInterface> netInterfaces;
		try
		{
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements())
			{
				NetworkInterface ni = netInterfaces.nextElement();

				Enumeration<InetAddress> emu = ni.getInetAddresses();
				while (emu.hasMoreElements())
				{
					InetAddress ipaddr = emu.nextElement();
					//					ScheduleControllerFactory.log.info(ipaddr.getHostAddress()
					//							+ "ip:-------------------------------------");
					if (ip.equals(ipaddr.getHostAddress()))
						return true;
				}
			}
		}
		catch (SocketException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 检查参数ips中是否包含本地服务器ip
	 * @param ips
	 * @param separator
	 * @return
	 */
	public static boolean containsIps(String ips, String separator)
	{
		if (StringUtils.isEmpty(ips))
		{
			return false;
		}
		String[] parts = ips.split(separator);
		for (String s : parts)
		{
			if (containsIp(s))
			{
				return true;
			}
		}
		return false;
	}

	public static String getCurrentLoginUserIp(HttpServletRequest request)
	{
		return getCurrentLoginUserIp(request, null);
	}

	/**
	 * 获得IP
	 * 
	 * @return
	 */
	public static String getCurrentLoginUserIp(HttpServletRequest request, HttpServletResponse response)
	{
		String rip = request.getRemoteAddr();
		String realIp = request.getHeader("X-Real-IP");//运维在nginx配置的ip读取方式
		if (StringUtils.isNotBlank(realIp))
		{
			return realIp;
		}
		String xff = request.getHeader("X-Forwarded-For");
		String ip;
		if (xff != null && xff.length() != 0)
		{
			int px = xff.indexOf(',');
			if (px != -1)
			{
				ip = xff.substring(0, px);
			}
			else
			{
				ip = xff;
			}
		}
		else
		{
			ip = rip;
		}
		return ip.trim();
	}

	/**
	 * 获取本机ip
	 * @return
	 */
	public static String getLocalIp()
	{
		String ip = null;
		try
		{
			InetAddress addr = InetAddress.getLocalHost();
			ip = addr.getHostAddress().toString(); //获取本机ip
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		return ip;
	}
}
