package com.vidolima.doco;

import com.vidolima.doco.annotation.DocumentIndex;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Ivan Martinov on 8/12/2015.
 */
public class ObjectParserTest {

    @Test
    public void testGetIndex() {

        Assert.assertEquals("Foo", ObjectParser.getIndexName(Foo.class));
        Assert.assertEquals("Bar", ObjectParser.getIndexName(Bar.class));
        Assert.assertEquals("myIndex", ObjectParser.getIndexName(FooNamed.class));
    }

    @DocumentIndex(name="myIndex")
    private static class FooNamed  {

    }
}
