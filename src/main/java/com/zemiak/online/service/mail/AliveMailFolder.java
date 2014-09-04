package com.zemiak.online.service.mail;

import com.zemiak.online.model.AliveMailMessage;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.*;

public class AliveMailFolder {
    private Folder folder;

    public AliveMailFolder() {
        ResourceBundle prop = ResourceBundle.getBundle("mail");
        String account = prop.getString("account");
        String password = prop.getString("password");
        String storeName = prop.getString("store");
        String host = prop.getString("host");
        String folderName = prop.getString("folder");

        Session session = Session.getInstance(new Properties());
        Store store;
        try {
            store = session.getStore(storeName);
        } catch (NoSuchProviderException ex) {
            throw new RuntimeException("Cannot get store " + storeName, ex);
        }


        try {
            store.connect(host, account, password);
        } catch (MessagingException ex) {
            throw new RuntimeException("Cannot connect to GMail", ex);
        }

        try {
            folder = store.getFolder(folderName);
            folder.open(Folder.READ_ONLY);
        } catch (MessagingException ex) {
            throw new RuntimeException("Cannot get folder " + folderName, ex);
        }
    }

    public int size() {
        try {
            return folder.getMessageCount();
        } catch (MessagingException ex) {
            throw new RuntimeException("Cannot get folder size", ex);
        }
    }

    public AliveMailMessage get(int i) {
        try {
            return new AliveMailMessage(folder.getMessage(i));
        } catch (MessagingException ex) {
            throw new RuntimeException("Cannot get message #" + i, ex);
        }
    }

    public boolean isEmpty() {
        return size() < 1;
    }
}
