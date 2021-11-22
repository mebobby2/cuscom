package com.company.cuscom;

import java.io.File;
import java.io.IOException;
import java.io.*;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
    extends CamelTestSupport
{
    static PipedInputStream in;
    static PipedOutputStream out;
    static InputStream originalIn;

    @BeforeClass()
    public static void setup() throws IOException {
        // System.in is an InputStream which is typically connected to
        // keyboard input of console programs
        originalIn = System.in;
        out = new PipedOutputStream();
        in = new PipedInputStream(out);
        System.setIn(in);
        FileUtils.deleteDirectory(new File("test"));
    }

    @After()
    public void tearDown() throws IOException {
        out.close();
        System.setIn(originalIn);
    }

    @Test()
    public void testAppRoute() throws Exception {
        out.write("This is a test message!\n".getBytes());
        Thread.sleep(2000);
        assertTrue(new File("test").listFiles().length == 1);
    }

    @Override
    public boolean isCreateCamelContextPerClass() {
        return false;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new App.AppRoute();
    }
}
