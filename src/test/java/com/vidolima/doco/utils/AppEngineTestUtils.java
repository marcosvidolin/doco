package com.vidolima.doco.utils;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

/**
 * Needed to test classes with annotations specific to app-engine.
 */
public class AppEngineTestUtils {
    /**
     * This utility is provided by AppEngine to run tests locally. {@link LocalDatastoreServiceTestConfig} has multiple
     * methods to simulate actual environment.
     * 
     * Initialize data store as HRD by specifying 100% High Rep job policy.
     */
    private final LocalServiceTestHelper datastoreServiceTestHelper;

    public AppEngineTestUtils() {
        datastoreServiceTestHelper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    }

    public void setUp() {
        datastoreServiceTestHelper.setUp();
    }

    public void tearDown() {
        datastoreServiceTestHelper.tearDown();
    }
}
