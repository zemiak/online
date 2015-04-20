package com.zemiak.online.service;

import com.zemiak.online.model.AliveMailMessage;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.mail.*;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

@Dependent
public class AliveMailFolder {
    private Folder mailFolder;

    @Inject private String account;
    @Inject private String password;
    @Inject private String host;
    @Inject private String folder;
    @Inject private String store;

    @PostConstruct
    public void init() {
        Session session = Session.getInstance(new Properties());
        Store mailStore;
        try {
            mailStore = session.getStore(store);
        } catch (NoSuchProviderException ex) {
            throw new RuntimeException("Cannot get store " + store, ex);
        }

        try {
            mailStore.connect(host, account, password);
        } catch (MessagingException ex) {
            throw new RuntimeException("Cannot connect to GMail", ex);
        }

        try {
            mailFolder = mailStore.getFolder(folder);
            mailFolder.open(Folder.READ_ONLY);
        } catch (MessagingException ex) {
            throw new RuntimeException("Cannot get folder " + folder, ex);
        }
    }

    @PreDestroy
    public void tearDown() {
        try {
            mailFolder.close(true);
        } catch (MessagingException ex) {
            throw new RuntimeException("Cannot close connection", ex);
        }
    }

    public int size() {
        try {
            return mailFolder.getMessageCount();
        } catch (MessagingException ex) {
            throw new RuntimeException("Cannot get folder size", ex);
        }
    }

    public AliveMailMessage get(int i) {
        try {
            return new AliveMailMessage(mailFolder.getMessage(i));
        } catch (MessagingException | RuntimeException ex) {
            throw new RuntimeException("Cannot get message #" + i, ex);
        }
    }

    public boolean isEmpty() {
        return size() < 1;
    }
}
