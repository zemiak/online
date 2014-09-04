/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.zemiak.online.model;

import java.sql.Date;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author vasko
 */
public class AliveMailMessageTest {
    AliveMailMessage aliveMessage;
    Message message;

    public AliveMailMessageTest() {
    }

    @Before
    public void setUp() throws MessagingException {
        message = new MimeMessage((Session) null);
        message.setSubject("[online] system-name");
        message.setSentDate(Date.valueOf("2011-01-01"));
        aliveMessage = new AliveMailMessage(message);
    }

    @Test
    public void getSystem() {
        assertEquals("system-name", aliveMessage.getSystem());
    }

    @Test
    public void getSent() {
        assertEquals(Date.valueOf("2011-01-01"), aliveMessage.getSent());
    }
}
