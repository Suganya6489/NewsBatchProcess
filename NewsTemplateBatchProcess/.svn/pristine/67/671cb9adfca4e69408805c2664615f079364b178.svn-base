package com.scansee.batch.common;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * The class for sending emails.
 * 
 * @author vaidehi.ne
 */

public class EmailComponent {
	/**
	 * default constructor for EmailComponent.
	 */
	public EmailComponent() {
	}

	/**
	 * The method for sending emails.
	 * 
	 * @param fromAddress
	 *            of the user.
	 * @param toAddrArray
	 *            as multiple recepients.
	 * @param subject
	 *            of the mail.
	 * @param msgBody
	 *            as email content are the request parameters.
	 * @throws MessagingException
	 *             for any exceptions occured while sending email.
	 */
	public static void mailingComponent(String fromAddress, String toAddrArray, String subject, String msgBody, String smtpHost, String smtpPort)
			throws MessagingException {
		Session session;
		MimeMessage mimeMessage = null;
		final Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpHost);
		props.setProperty("mail.smtp.port", smtpPort);
		session = Session.getDefaultInstance(props, null);
		final InternetAddress[] toAddress = { new InternetAddress(toAddrArray) };

		// create a message
		mimeMessage = new MimeMessage(session);
		mimeMessage.setFrom(new InternetAddress(fromAddress));
		if (toAddrArray != null) {
			mimeMessage.setRecipients(Message.RecipientType.TO, toAddress);
		}
		mimeMessage.setSubject(subject);

		// create and fill the first message part
		final MimeBodyPart bodyText = new MimeBodyPart();
		// bodyText.setText(msgBody);
		bodyText.setContent(msgBody, "text/html");
		// create the Multipart and add its parts to it
		final Multipart multipart = new MimeMultipart();

		multipart.addBodyPart(bodyText);
		// add the Multipart to the message
		mimeMessage.setContent(multipart);

		// set the Date: header
		mimeMessage.setSentDate(new Date());
		// send the message
		Transport.send(mimeMessage);

	}

	/**
	 * The method for sending emails.
	 * 
	 * @param fromAddress
	 *            of the user.
	 * @param toAddrArray
	 *            as multiple recepients.
	 * @param subject
	 *            of the mail.
	 * @param msgBody
	 *            as email content are the request parameters.
	 * @throws MessagingException
	 *             for any exceptions occured while sending email.
	 */
	public static void multipleUsersmailingComponent(String fromAddress, String[] toAddrArray, String subject, String msgBody, String smtpHost, String smtpPort)
			throws MessagingException {

		Session session;
		MimeMessage mimeMessage = null;
		final Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpHost);
		props.setProperty("mail.smtp.port", smtpPort);
		session = Session.getDefaultInstance(props, null);
		mimeMessage = new MimeMessage(session);
		mimeMessage.setFrom(new InternetAddress(fromAddress));
		final InternetAddress[] toAddress2 = new InternetAddress[toAddrArray.length];

		for (int i = 0; i < toAddrArray.length; i++) {
			toAddress2[i] = new InternetAddress(toAddrArray[i]);
		}

		mimeMessage.setRecipients(Message.RecipientType.TO, toAddress2);
		mimeMessage.addRecipients(Message.RecipientType.BCC, toAddress2);
		mimeMessage.setSubject(subject);

		// create and fill the first message part
		final MimeBodyPart bodyText = new MimeBodyPart();
		// bodyText.setText(msgBody);
		bodyText.setContent(msgBody, "text/html");
		// create the Multipart and add its parts to it
		final Multipart multipart = new MimeMultipart();

		multipart.addBodyPart(bodyText);
		// add the Multipart to the message
		mimeMessage.setContent(multipart);

		// set the Date: header
		mimeMessage.setSentDate(new Date());
		// send the message
		Transport.send(mimeMessage);

	}

}
