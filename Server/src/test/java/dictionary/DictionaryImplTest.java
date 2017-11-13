package dictionary;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class DictionaryImplTest {

    private final static String DEVIDER = " ";

    private Dictionary dict = DictionaryImpl.INSTANCE;

    @Before
    public void prepareData() {
        dict.clear();
    }

    @Test
    public void doInsertTest() {
        final String word = "hello";
        final String translate = buildParam("алло", "привет", "здравствуйте");
        long n = dict.insert(word, translate);
        Assert.assertEquals("Wrong translates", translate, dict.get(word));
        Assert.assertEquals("Wrong words number", translate.split(DEVIDER).length, n);
    }

    @Test
    public void doInsertDuplicateTest() {
        final String word = "hello";
        final String translate = buildParam("алло", "привет", "алло", "привет");
        final String expected = buildParam("алло", "привет");
        long n = dict.insert(word, translate);
        Assert.assertEquals("Wrong returned translates", expected, dict.get(word));
        Assert.assertEquals("Wrong words number", expected.split(DEVIDER).length, n);
    }

    @Test
    public void doInsertDuplicateTest2() {
        final String word = "hello";
        final String translate1 = buildParam("алло", "привет");
        final String translate2 = buildParam("здравствуйте", "привет");
        final String expected = buildParam("алло", "привет", "здравствуйте");
        long n = dict.insert(word, translate1);
        long m = dict.insert(word, translate2);
        Assert.assertEquals("Wrong returned translates", expected, dict.get(word));
        Assert.assertEquals("Wrong words number", translate1.split(DEVIDER).length, n);
        Assert.assertEquals("Wrong words number", 1, m);
    }

    @Test
    public void doDeleteTest() {
        final String word = "hello";
        final String translate = buildParam("алло", "привет", "здравствуйте");
        final String expected = buildParam("алло", "привет");
        final String deleted =  buildParam("здравствуйте", "дороф");
        long n = dict.insert(word, translate);
        long m = dict.delete(word, deleted);
        Assert.assertEquals("Wrong returned translates", expected, dict.get(word));
        Assert.assertEquals("Wrong words number", translate.split(DEVIDER).length, n);
        Assert.assertEquals("Wrong words number", 1, m);
    }

    @Test
    public void doDeleteTest2() {
        final String word = "hello";
        final String translate = buildParam("алло", "привет", "здравствуйте");
        dict.insert(word, translate);
        dict.delete(word, translate);
        Assert.assertEquals("Wrong returned translates", 0, dict.get(word).length());
    }

    @Test
    public void doGetAbsentTest() {
        final String word = "hello";
        final String translate = buildParam("алло", "привет");
        final String absent = "Man";
        dict.insert(word, translate);
        Assert.assertEquals("Wrong returned translates", 0, dict.get(absent).length());
    }

    @Test
    public void doAddTestWithUpperLowerCase() {
        dict.insert("Hello", "алло");
        dict.insert("Hello", "Алло");
        dict.insert("HeLLo", "Привет");
        dict.insert("Hello", "АЛЛО");
        dict.insert("HELLO", "Привет");
        final String expected = "алло привет";
        Assert.assertEquals("Wrong translates", expected, dict.get("hello"));
        Assert.assertEquals("Wrong translates", expected, dict.get("Hello"));
    }

    @Test
    public void doDeleteTestWithUpperLowerCase() {
        dict.insert("Hello", "алло");
        dict.insert("HeLLo", "Привет");
        dict.insert("Hello", "Здравствуйте");
        dict.delete("Hello", "ПРИВЕТ");
        dict.delete("HELLO", "здравствуйте");
        Assert.assertEquals("Wrong translates", "алло", dict.get("hello"));
    }

    private String buildParam(String ... params) {
        return Arrays.asList(params).stream().collect(Collectors.joining(DEVIDER));
    }
}
