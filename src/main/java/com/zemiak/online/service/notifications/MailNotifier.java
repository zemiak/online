package com.zemiak.online.service.notifications;

import com.sun.mail.util.MailConnectException;
import com.zemiak.online.model.event.NewProtectedSystemEvent;
import com.zemiak.online.model.event.OutageStartEvent;
import com.zemiak.online.model.event.OutageStopEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
public class MailNotifier {
    private static final Logger LOG = Logger.getLogger(MailNotifier.class.getName());

    @Resource(name = "java:/online/mail/default")
    private Session mailSession;

    @Inject private String mailTo;
    @Inject private String mailFrom;
    @Inject private String mailSubjectNew;
    @Inject private String mailSubjectStart;
    @Inject private String mailSubjectStop;

    public void newSystem(@Observes NewProtectedSystemEvent event) {
        try {
            send(mailSubjectNew, event.getName());
        } catch (MessagingException ex) {
            LOG.log(Level.SEVERE, "Cannot send mail: {0}", ex);
        }
    }

    public void startOutage(@Observes OutageStartEvent event) {
        try {
            send(mailSubjectStart, event.getProtectedSystem().getName());
        } catch (MessagingException ex) {
            LOG.log(Level.SEVERE, "Cannot send mail: {0}", ex);
        }
    }

    public void stopOutage(@Observes OutageStopEvent event) {
        try {
            send(mailSubjectStop, event.getOutage().getProtectedSystem().getName());
        } catch (MessagingException ex) {
            LOG.log(Level.SEVERE, "Cannot send mail: {0}", ex);
        }
    }

    private void send(String mailSubject, String system) throws MessagingException, MailConnectException {
        String subject = String.format(mailSubject, system);

        final Message message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(mailFrom));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
        message.setSubject(subject);
        message.setText("Please, check the Online portal for details");

        Transport.send(message);

        LOG.log(Level.INFO, "Sent {0} to {1}", new Object[]{subject, mailTo});
    }
}
