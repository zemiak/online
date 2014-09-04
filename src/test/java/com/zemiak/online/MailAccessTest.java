package com.zemiak.online;

import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class MailAccessTest {
    private static final Logger LOG = Logger.getLogger(MailAccessTest.class.getName());

    @Test
    @Ignore
    public void loginToGmail() throws MessagingException {
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
            LOG.log(Level.SEVERE, "Cannot get store " + storeName, ex);
            return;
        }


        try {
            store.connect(host, account, password);
        } catch (MessagingException ex) {
            LOG.log(Level.SEVERE, "Cannot connect to GMail", ex);
            return;
        }

        Folder folder;
        try {
            folder = store.getFolder(folderName);
            folder.open(Folder.READ_ONLY);
        } catch (MessagingException ex) {
            LOG.log(Level.SEVERE, "Cannot get folder " + folderName, ex);
            return;
        }

        int messageCount = folder.getMessageCount();
        System.err.println("Message count: " + messageCount);
        Assert.assertTrue(messageCount > 0);
    }
}
