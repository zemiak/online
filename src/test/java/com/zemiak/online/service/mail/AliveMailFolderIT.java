package com.zemiak.online.service.mail;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

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
