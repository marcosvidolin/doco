package com.vidolima.doco;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.search.Document;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.vidolima.doco.annotation.DocumentField;
import com.vidolima.doco.annotation.DocumentId;
import com.vidolima.doco.annotation.DocumentRef;
import com.vidolima.doco.annotation.FieldType;
import com.vidolima.doco.utils.AppEngineTestUtils;

public class DocumentRefTest {

    private AppEngineTestUtils testUtils = new AppEngineTestUtils();

    static {
        ObjectifyService.register(B.class);
        ObjectifyService.register(C.class);
    }

    @Before
    public void setupTests() {
        testUtils.setUp();
    }

    @After
    public void teardownTests() {
        testUtils.tearDown();
    }

    @Test
    public void testRefFieldConversionSuccess() {
        final String cId = "id of class C";
        C c = new C(cId);
        Key<C> cKey = ObjectifyService.ofy().save().entity(c).now();
        final long b_numberVal = 10L;
        B b = new B(b_numberVal, Ref.create(cKey));
        Key<B> bKey = ObjectifyService.ofy().save().entity(b).now();
        final String textVal = "myText";
        final long a_numberVal = 20L;
        A a = new A(textVal, a_numberVal, Ref.create(bKey));

        Doco doco = new Doco();
        // now save 'a' which contains ref for object 'b'
        Document document = doco.toDocument(a);
        // resulting document should have fields of both 'a', 'b' and 'c'
        assertEquals(textVal, document.getOnlyField("text").getText());
        assertEquals(a_numberVal, document.getOnlyField("number").getNumber().longValue());
        assertEquals(b_numberVal, document.getOnlyField("B_number").getNumber().longValue());
        assertEquals(cId, document.getOnlyField("B_C_cId").getText());
    }

    static class A {
        public A(String text, long number, Ref<B> bRef) {
            this.text = text;
            this.bRef = bRef;
            this.number = number;
        }

        @DocumentId
        @DocumentField(type = FieldType.TEXT)
        String text;
        @DocumentField(type = FieldType.NUMBER)
        long number;
        @DocumentRef(type = B.class)
        Ref<B> bRef;
    }

    @Entity
    static class B {
        @DocumentField(type = FieldType.NUMBER)
        @Id
        long number;
        @DocumentRef(type = C.class)
        Ref<C> cRef;

        public B(long num, Ref<C> cRef) {
            this.number = num;
            this.cRef = cRef;
        }
    }

    @Entity
    static class C {
        @DocumentField(type = FieldType.TEXT)
        @Id
        String cId;

        public C(String id) {
            this.cId = id;
        }
    }
}
