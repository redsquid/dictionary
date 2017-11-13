package utils;

import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.MissingFormatArgumentException;

public class ArgsLineTest {

    @Test
    public void doTestArgsLine() {
        final String params = "localhost 1000 add hello здравствуйте привет";
        ArgsLine result = ArgsLine.parse(params.split(" "));
        try {
            Assert.assertEquals("Params are not equals", new URL("http://localhost:1000/xmlrpc"), result.getServerUrl());
        } catch (MalformedURLException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertEquals("Params are not equals", "add", result.getCommand());
        String words = String.join(" ", result.getWords());
        Assert.assertEquals("Params are not equals", "hello здравствуйте привет", words);
    }

    @Test
    public void doTestArgsLineException() {
        final String params = "localhost port add hello здравствуйте";
        try {
            ArgsLine.parse(params.split(" "));
        } catch (MissingFormatArgumentException e) {
            return;
        }
        Assert.fail("Exception has not been thrown");
    }
}
