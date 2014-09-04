package com.zemiak.online.model;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.MessagingException;

public class AliveMailMessage {
    private String system;
    private Date sent;
    private Date received;

    public AliveMailMessage() {
    }

    public AliveMailMessage(Message msg) {
        parseSystem(msg);
        parseSentDate(msg);
        parseReceivedDate(msg);
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public Date getSent() {
        return sent;
    }

    public void setSent(Date sent) {
        this.sent = sent;
    }

    public Date getReceived() {
        return received;
    }

    public void setReceived(Date received) {
        this.received = received;
    }

    private void parseReceivedDate(Message msg) {
        try {
            setReceived(msg.getReceivedDate());
        } catch (MessagingException ex) {
            throw new RuntimeException("Cannot get Received Date", ex);
        }
    }

    private void parseSentDate(Message msg) {
        try {
            setSent(msg.getSentDate());
        } catch (MessagingException ex) {
            throw new RuntimeException("Cannot get Sent Date", ex);
        }
    }

    private void parseSystem(Message msg) {
        String subject;

        try {
            subject = msg.getSubject();
        } catch (MessagingException ex) {
            throw new RuntimeException("Cannot get Subject", ex);
        }

        // "[online] raspberry-server"
        Matcher matcher = Pattern.compile("^(\\[online\\] )(.+)$").matcher(subject);
        if (matcher.matches()) {
            setSystem(matcher.group(2));
        } else {
            throw new IllegalStateException("Cannot parse alive status");
        }
    }
}
