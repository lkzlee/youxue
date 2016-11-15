package com.youxue.core.util;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import com.lkzlee.pay.exceptions.BusinessException;

@Service
public class MailUtil
{
	private static Log log = LogFactory.getLog(MailUtil.class);
	private static Properties props = System.getProperties();
	private static MailAuthenticator authenticator = new MailAuthenticator();
	private static Session session = null;
	private static MimeMessage message;
	private String from = null;
	private static final String MAIL_LIST_SPLIT_KEY = ",";

	public void init(String mailToSendList, String mailCCSendList)
	{
		try
		{
			from = PropertyUtils.getProperty("smtp.user", "xxxx@126.com");
			// Setup mail server
			props.put("mail.smtp.host", PropertyUtils.getProperty("smtp.host", "smtp.126.com"));
			// Get session
			props.put("mail.smtp.auth", "true");//这样才能通过验证
			authenticator.setStrUser(from);
			authenticator.setStrPwd(PropertyUtils.getProperty("smtp.passwd", "xxx"));
			session = Session.getDefaultInstance(props, authenticator);
			session.setDebug(true);//设置为debug
			message = new MimeMessage(session);
			message.addRecipients(Message.RecipientType.TO, pakckgeEmail(mailToSendList));
			//设置抄送名单
			message.addRecipients(Message.RecipientType.CC, pakckgeEmail(mailCCSendList));
		}
		catch (MessagingException e)
		{
			log.error(e.getMessage(), e);
		}
	}

	public void sendEmail(String emailTitle, String subject, String content, String chargeSet)
	{

		try
		{
			message.setFrom(new InternetAddress(from, emailTitle));

			message.setSubject(subject);
			message.setText("激活账号系统", chargeSet);
			message.setContent(content, "text/html; charset=" + chargeSet);
			message.setSentDate(new Date());
			message.saveChanges();
			Transport.send(message);

		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	public void sendEmailWithAttachment(String emailTitle, String subject, String content, File file, String fileName,
			String chargeSet)
	{
		try
		{
			message.setFrom(new InternetAddress(from, emailTitle));
			message.setSubject(subject);
			message.setSentDate(new Date());

			// Create the message part and fill the message
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(content);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(file);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(fileName);
			multipart.addBodyPart(messageBodyPart);
			// Put parts in message
			message.setContent(multipart);
			// Send the message
			Transport.send(message);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	public InternetAddress[] pakckgeEmail(String to)
	{
		if (StringUtils.isBlank(to))
		{
			return null;
		}
		String[] toList = to.split(MAIL_LIST_SPLIT_KEY);

		InternetAddress[] address = new InternetAddress[toList.length];
		for (int i = 0; i < toList.length; i++)
		{
			try
			{
				address[i] = new InternetAddress(toList[i]);
			}
			catch (AddressException e)
			{
				log.error(e.getMessage(), e);
			}
		}
		return address;
	}

	/**
	 * 得到邮件模板的内容，如果为空表示错误
	 * fileName仅表示文件名
	 * @return
	 */
	public static String getMailModulContent(String fileName) throws BusinessException
	{
		String str = null;
		try
		{
			InputStream in = MailUtil.class.getResourceAsStream("/" + fileName);
			if (in == null)
			{
				log.error(fileName + " is null");
				return str;
			}

			SAXReader reader = new SAXReader();
			reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

			try
			{
				Document d = reader.read(in);
				Element e = d.getRootElement();
				Element htmle = e.element("html");
				str = htmle.asXML();
				if (str.indexOf("<![CDATA[") >= 0 && str.indexOf("]]>") >= 0)
				{
					int begin = str.indexOf("<![CDATA[") + "<![CDATA[".length();
					int end = str.indexOf("]]>");
					str = str.substring(begin, end);
				}
				else
				{
					log.error("发送邮件错误:读取模板内容错误" + e);
					return null;
				}
				return str;
			}
			catch (Exception e)
			{
				log.error("发送邮件错误:读取模板错误" + e);
			}
			in.close();
		}
		catch (Exception e)
		{
			log.error("发送直邮错误:读取模板错误" + e);
			throw new BusinessException("发送直邮错误:读取模板错误");
		}
		return str;
	}

	public static void main(String[] args)
	{
		MailUtil ml = new MailUtil();
		ml.init("xxx@qq.com", null);
		ml.sendEmail("xxx@qq.com", "xsdd", "dfasf", "UTF-8");
	}
}
