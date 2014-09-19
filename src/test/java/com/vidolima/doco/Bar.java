package com.vidolima.doco;

import com.vidolima.doco.annotation.DocumentField;
import com.vidolima.doco.annotation.DocumentIndexSubClass;
import com.vidolima.doco.annotation.FieldType;

@DocumentIndexSubClass
public class Bar extends Foo {
    @DocumentField(type = FieldType.NUMBER)
    private long subClassNumberField;

    public long getSubClassNumberField() {
        return subClassNumberField;
    }

    public void setSubClassNumberField(long subClassNumberField) {
        this.subClassNumberField = subClassNumberField;
    }
}
