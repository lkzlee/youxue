package com.youxue.core.util;

import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends javax.mail.Authenticator
{

	private String strUser;
	private String strPwd;

	public String getStrUser()
	{
		return strUser;
	}

	public void setStrUser(String strUser)
	{
		this.strUser = strUser;
	}

	public String getStrPwd()
	{
		return strPwd;
	}

	public void setStrPwd(String strPwd)
	{
		this.strPwd = strPwd;
	}

	public MailAuthenticator()
	{

	}

	public MailAuthenticator(String user, String password)
	{
		this.strUser = user;
		this.strPwd = password;
	}

	protected PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(strUser, strPwd);
	}

}