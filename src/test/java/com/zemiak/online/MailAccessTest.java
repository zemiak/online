package com.zemiak.online;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class MailAccessTest {
    @Test
    @Ignore
    public void loginToGmail() throws FileNotFoundException, IOException, NoSuchProviderException, MessagingException {
        Properties mailProperties = readProperties(ResourceBundle.getBundle("mail"));
        String account = mailProperties.getProperty("account");
        String password = mailProperties.getProperty("password");
        
        mailProperties.remove("account");
        mailProperties.remove("password");
        
        System.err.println("Props: " + mailProperties);

        

        Session session = Session.getDefaultInstance(mailProperties);
        Store store = session.getStore("imaps");
        store.connect(account, password);

        Folder folder = store.getFolder("alive");
        folder.open(Folder.READ_ONLY);
        int messageCount = folder.getMessageCount();

        Assert.assertTrue(messageCount > 0);

        for (int i = 0; i < 10; i++) {
            Message message = folder.getMessage(i);
            String[] receivedHeaders = message.getHeader("Received");
            String received = receivedHeaders.length > 0 ? receivedHeaders[0] : "<unknown>";
            System.out.println("Subject: " + message.getSubject() + ", sent on " + message.getSentDate() + ", arrived " + received);
        }
    }

    private Properties readProperties(final ResourceBundle bundle) {
        Properties p = new Properties();

        for (String key: bundle.keySet()) {
            p.put(key, bundle.getString(key));
        }

        return p;
    }
}
