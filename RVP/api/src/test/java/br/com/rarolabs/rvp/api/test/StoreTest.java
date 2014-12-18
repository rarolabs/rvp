package br.com.rarolabs.rvp.api.test;


import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.rarolabs.rvp.api.MyEndpoint;

import static org.junit.Assert.*;


public class StoreTest {
    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void testSayHi() throws Exception {
        assertTrue(true);
    }

    @Test
    public void testSayHi2() throws Exception {
        new MyEndpoint().sayHi2("ola");
    }
}