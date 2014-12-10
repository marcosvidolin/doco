package com.vidolima.doco;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.search.Document;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.vidolima.doco.annotation.DocumentEmbed;
import com.vidolima.doco.annotation.DocumentField;
import com.vidolima.doco.annotation.DocumentId;
import com.vidolima.doco.annotation.FieldType;

public class DocumentEmbedTest {

    @Test
    public void testRefFieldConversionSuccess() {
        final String cId = "id of class C";
        C c = new C(cId);
        final long b_numberVal = 10L;
        B b = new B(b_numberVal, c);
        final String textVal = "myText";
        final long a_numberVal = 20L;
        A a = new A(textVal, a_numberVal, b);

        Doco doco = new Doco();
        // now save 'a' which contains ref for object 'b'
        Document document = doco.toDocument(a);
        System.out.println(document.toString());
        // resulting document should have fields of all 'a', 'b' and 'c'
        assertEquals(textVal, document.getOnlyField("text").getText());
        assertEquals(a_numberVal, document.getOnlyField("number").getNumber().longValue());
        assertEquals(b_numberVal, document.getOnlyField("B_number").getNumber().longValue());
        assertEquals(cId, document.getOnlyField("B_C_cId").getText());
    }

    static class A {
        public A(String text, long number, B bRef) {
            this.text = text;
            this.bRef = bRef;
            this.number = number;
            this.bNull = null; // intentional null to make sure doco works fine with nulls
        }

        @DocumentId
        @DocumentField(type = FieldType.TEXT)
        String text;
        @DocumentField(type = FieldType.NUMBER)
        long number;
        @DocumentEmbed
        B bRef;
        @DocumentEmbed
        B bNull;
    }

    @Entity
    static class B {
        @DocumentField(type = FieldType.NUMBER)
        @Id
        long number;
        @DocumentField(type = FieldType.GEO_POINT)
        GeoPt geoPt;
        @DocumentEmbed
        C cRef;

        public B(long num, C cRef) {
            this.number = num;
            this.cRef = cRef;
            this.geoPt = new GeoPt(10.23f, 45.67f);
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
