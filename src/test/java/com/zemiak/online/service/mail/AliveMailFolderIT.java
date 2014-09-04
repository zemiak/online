/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.zemiak.online.service.mail;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author vasko
 */
public class AliveMailFolderIT {

    public AliveMailFolderIT() {
    }

    AliveMailFolder folder;

    @Before
    public void setUp() {
        folder = new AliveMailFolder();
    }

    @Test
    public void size() {
        assertTrue(folder.size() > 0);
    }

    @Test
    public void isEmpty() {
        assertFalse(folder.isEmpty());
    }

    @Test
    public void get() {
        assertNotNull(folder.get(1));
    }

}
