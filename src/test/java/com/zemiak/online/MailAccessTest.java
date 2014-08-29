package com.zemiak.online;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import org.junit.Assert;
import org.junit.Test;

public class MailAccessTest {
    @Test
    public void loginToGmail() throws FileNotFoundException, IOException, NoSuchProviderException, MessagingException {
        Properties mailProperties = readProperties(ResourceBundle.getBundle("mail"));

        String account = mailProperties.getProperty("account");
        String password = mailProperties.getProperty("password");

        Session session = Session.getDefaultInstance(mailProperties);
        Store store = session.getStore("imaps");
        store.connect(account, password);

        Folder folder = store.getFolder("alive");
        folder.open(Folder.READ_ONLY);
        int messageCount = folder.getMessageCount();

        Assert.assertTrue(messageCount > 0);
    }

    private Properties readProperties(final ResourceBundle bundle) {
        Properties p = new Properties();

        for (String key: bundle.keySet()) {
            p.put(key, bundle.getString(key));
        }

        return p;
    }
}
