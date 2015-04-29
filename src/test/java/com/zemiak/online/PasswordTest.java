package com.zemiak.online;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

public class PasswordTest {
    private static final Logger LOG = Logger.getLogger(PasswordTest.class.getName());

    @Test
    public void encryptPassword() {
        System.out.println("Password: " + base64Encode(getHash("xxx")));
    }

    private String base64Encode(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }

    private byte[] getHash(String text) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            LOG.log(Level.SEVERE, "Cannot get SHA256 hash encoder", ex);
            return new byte[]{};
        }

        try {
            return digest.digest(text.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            LOG.log(Level.SEVERE, "Cannot get UTF8 text encoder", ex);
            return new byte[]{};
        }
    }
}
