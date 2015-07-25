package com.vidolima.doco;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Facet;
import com.vidolima.doco.annotation.DocumentField;
import com.vidolima.doco.annotation.DocumentId;
import com.vidolima.doco.annotation.DocumentIndex;
import com.vidolima.doco.annotation.FacetField;
import com.vidolima.doco.annotation.FacetType;
import com.vidolima.doco.annotation.FieldType;

public class FacetTest {

    @DocumentIndex
    class Foo {
        static final String STRING_FIELD_NAME = "stringField";
        static final String FACET_NAME = "differentName";
        static final String DOUBLE_FIELD_NAME = "doubleField";

        public Foo(String stringField, long numberField, double doubleField) {
            this.stringField = stringField;
            this.fieldWithFacetName = numberField;
            this.doubleField = doubleField;
        }

        @DocumentId
        @FacetField(type = FacetType.ATOM)
        String stringField;

        @DocumentField(type = FieldType.NUMBER)
        @FacetField(type = FacetType.NUMBER, name = FACET_NAME)
        long fieldWithFacetName;

        @FacetField(type = FacetType.NUMBER)
        double doubleField;
    }

    @Test
    public void testFacetGenerationSuccess() {
        Doco doco = new Doco();
        // now save 'a' which contains ref for object 'b'
        Document document = doco.toDocument(new Foo("blah", 1L, 2.0));

        // verification
        Facet stringFieldFacet = document.getOnlyFacet(Foo.STRING_FIELD_NAME);
        assertEquals(Foo.STRING_FIELD_NAME, stringFieldFacet.getName());
        assertEquals("blah", stringFieldFacet.getAtom());

        Facet longFieldFacet = document.getOnlyFacet(Foo.FACET_NAME);
        assertEquals(Foo.FACET_NAME, longFieldFacet.getName());
        assertEquals(1, longFieldFacet.getNumber().longValue());

        Facet doubleFieldFacet = document.getOnlyFacet(Foo.DOUBLE_FIELD_NAME);
        assertEquals(Foo.DOUBLE_FIELD_NAME, doubleFieldFacet.getName());
        assertEquals(2.0, doubleFieldFacet.getNumber().doubleValue(), 0.001);
    }
}
