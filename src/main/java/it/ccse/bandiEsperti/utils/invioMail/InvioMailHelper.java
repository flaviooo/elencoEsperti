package it.ccse.bandiEsperti.utils.invioMail;

import it.ccse.bandiEsperti.utils.ConfigHelper;

import java.io.FileReader;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class InvioMailHelper {

	private Properties mailProperties = new Properties();
	

	public InvioMailHelper() {
		ConfigHelper helper = new ConfigHelper();
		Properties configProps = helper.getConfigProperties();
		String mailCfgFile = configProps.getProperty(ConfigHelper.MAIL_KEY);
		try {
			mailProperties.load(new FileReader(mailCfgFile));
		} catch (Exception e) {

		}
	}

	public void invioMailRecupero(String indirizzoA, String espertoUsername, String espertoPassword)
	{
		String msgBodyPar = mailProperties.getProperty("mail.recuperoMail.msgBody");
		String msgSubject = mailProperties.getProperty("mail.recuperoMail.msgSubject");
		String msgMailDa = mailProperties.getProperty("mail.mailMittente");
		String msgBody = MessageFormat.format(msgBodyPar, espertoUsername, espertoPassword);
		invioMail(msgMailDa, indirizzoA, msgSubject, msgBody);
	}

	public void invioMailRegistrazione(String indirizzoA, String nome, String cognome, String username, String password)
	{

		String msgBodyPar = mailProperties.getProperty("mail.registrazione.msgBody");
		String msgSubject = mailProperties.getProperty("mail.registrazione.msgSubject");
		String msgMailDa=mailProperties.getProperty("mail.mailMittente");
		String msgBody = MessageFormat.format(msgBodyPar, nome, cognome, username, password);
		invioMail(msgMailDa, indirizzoA, msgSubject, msgBody);
	}
	

	
	public void invioNotificaRegistrazione(String nomeUtente, String cognomeUtente, Date dataRegistrazione)
	{
		String msgBodyPar = mailProperties.getProperty("mail.registrazione.notifica.msgBody");
		String msgSubject = mailProperties.getProperty("mail.registrazione.notifica.msgSubject");
		String msgMailDa=mailProperties.getProperty("mail.mailMittente");
		String msgBody = MessageFormat.format(msgBodyPar, nomeUtente, cognomeUtente, dataRegistrazione);
		invioMail(msgMailDa, msgMailDa, msgSubject, msgBody);
	}


	public void invioMailCVInviato(String indirizzoA, String nome, String cognome)
	{

		String msgBodyPar = mailProperties.getProperty("mail.invioCV.msgBody");
		String msgSubject = mailProperties.getProperty("mail.invioCV.msgSubject");
		String msgMailDa=mailProperties.getProperty("mail.mailMittente");
		String msgBody = MessageFormat.format(msgBodyPar, nome, cognome);
		invioMail(msgMailDa, indirizzoA, msgSubject, msgBody);
	}
	

	
	public void invioNotificaCVInviato(String nomeUtente, String cognomeUtente, Date dataInvio)
	{
		String msgBodyPar = mailProperties.getProperty("mail.invioCV.notifica.msgBody");
		String msgSubject = mailProperties.getProperty("mail.invioCV.notifica.msgSubject");
		String msgMailDa=mailProperties.getProperty("mail.mailMittente");
		String msgBody = MessageFormat.format(msgBodyPar, nomeUtente, cognomeUtente, dataInvio);
		invioMail(msgMailDa, msgMailDa, msgSubject, msgBody);
	}
	

	public void invioMailCVAggiornato(String indirizzoA, String nome, String cognome)
	{

		String msgBodyPar = mailProperties.getProperty("mail.aggiornaCV.msgBody");
		String msgSubject = mailProperties.getProperty("mail.aggiornaCV.msgSubject");
		String msgMailDa=mailProperties.getProperty("mail.mailMittente");
		String msgBody = MessageFormat.format(msgBodyPar, nome, cognome);
		invioMail(msgMailDa, indirizzoA, msgSubject, msgBody);
	}
	

	
	public void invioNotificaCVAggiornato(String nomeUtente, String cognomeUtente, Date dataAggiornamento)
	{
		String msgBodyPar = mailProperties.getProperty("mail.aggiornaCV.notifica.msgBody");
		String msgSubject = mailProperties.getProperty("mail.aggiornaCV.notifica.msgSubject");
		String msgMailDa=mailProperties.getProperty("mail.mailMittente");
		String msgBody = MessageFormat.format(msgBodyPar, nomeUtente, cognomeUtente, dataAggiornamento);
		invioMail(msgMailDa, msgMailDa, msgSubject, msgBody);
	}
	
	public void invioCredenziali(String nomeUtente, String cognomeUtente, String emailUtente,
			String username, String password) {
		String msgBodyPar = mailProperties.getProperty("mail.invioCred.msgSubject");
		String msgSubject = mailProperties.getProperty("mail.invioCred.msgBody");
		String msgMailDa=mailProperties.getProperty("mail.mailMittente");
		String msgBody = MessageFormat.format(msgBodyPar, nomeUtente, cognomeUtente, username, password);
		invioMail(msgMailDa, msgMailDa, msgSubject, msgBody);
		
	}
	
	private void invioMail(String indirizzoDa, String indirizzoA, String msgSubject, String msgText)
	{
		
		try
		{
			Message message = new MimeMessage(getMailSession());
			InternetAddress fromAddress = new InternetAddress(indirizzoDa);
			InternetAddress toAddress = new InternetAddress(indirizzoA);
			message.setFrom(fromAddress);
			message.addRecipient(RecipientType.TO, toAddress);
			message.setSubject(msgSubject);
			message.setText(msgText);
			message.setSentDate(new Date());
			Transport.send(message);
		}
		catch (Exception e) {

		}
	}

	private Session getMailSession()
	{
		return Session.getDefaultInstance(mailProperties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				String username = mailProperties.getProperty("mail.auth.username");
				String password = mailProperties.getProperty("mail.auth.password");
				return new PasswordAuthentication(username, password);

			}
		});
	}




}
