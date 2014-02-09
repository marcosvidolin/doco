package com.vidolima.doco;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.GeoPoint;
import com.google.appengine.api.search.Index;

@RunWith(JUnit4.class)
public class DocoTest {
	
	@Test
	public void testConversionFromDocumentToObject() {

		Foo t = new Foo();
		t.setCode("123456");
		t.setAtomFieldTest("This is a Atom field");
		t.setDateFieldTest(new Date());
		t.setGeopointFieldTest(new GeoPoint(1, 0));
		t.setHtmlFieldTest("<html><title>Doco</title><body>Test doco</body></html>");
		t.setNumberFieldTest(1986d);
		t.setTextFieldTest("Text field declararion with name and type");
		t.setTextFieldWithoutName("Text field declaration without name");
		t.setTextFieldWithoutType("Text field declaration without type");
		t.setTextFieldWithoutTypeAndName("Simple text field deplaration without type and name");

		Doco doco = new Doco();
		Document doc = doco.toDocument(t);

		Assert.assertEquals(doc.getId(), t.getCode());
		Assert.assertEquals(doc.getOnlyField("atomFieldTest").getAtom(), t.getAtomFieldTest());
		Assert.assertEquals(doc.getOnlyField("dateFieldTest").getDate(), t.getDateFieldTest());
		Assert.assertEquals(doc.getOnlyField("geopointFieldTest").getGeoPoint(), t.getGeopointFieldTest());
		Assert.assertEquals(doc.getOnlyField("htmlFieldTest").getHTML(), t.getHtmlFieldTest());
		Assert.assertEquals(doc.getOnlyField("numberFieldTest").getNumber(), t.getNumberFieldTest());
		Assert.assertEquals(doc.getOnlyField("justText").getText(), t.getTextFieldTest());
		Assert.assertEquals(doc.getOnlyField("textFieldWithoutName").getText(), t.getTextFieldWithoutName());
		Assert.assertEquals(doc.getOnlyField("txtName").getText(), t.getTextFieldWithoutType());
		Assert.assertEquals(doc.getOnlyField("textFieldWithoutTypeAndName").getText(), t.getTextFieldWithoutTypeAndName());
	}

	@Test
	public void testConversionFromObjectToDocument() {
		
		Document doc = Document.newBuilder()
			    .setId("123456")
			    .addField(Field.newBuilder().setName("atomFieldTest").setAtom("This is a Atom field"))
			    .addField(Field.newBuilder().setName("dateFieldTest").setDate(new Date()))
			    .addField(Field.newBuilder().setName("geopointFieldTest").setGeoPoint(new GeoPoint(1, 0)))
			    .addField(Field.newBuilder().setName("htmlFieldTest").setHTML("<html><title>Doco</title><body>Test doco</body></html>"))
			    .addField(Field.newBuilder().setName("numberFieldTest").setNumber(1986))
			    .addField(Field.newBuilder().setName("justText").setText("Text field declararion with name and type"))
			    .addField(Field.newBuilder().setName("textFieldWithoutName").setText("Text field declaration without name"))
			    .addField(Field.newBuilder().setName("txtName").setText("Text field declaration without type"))
			    .addField(Field.newBuilder().setName("textFieldWithoutTypeAndName").setText("Simple text field deplaration without type and name"))
			    .build();

		Doco doco = new Doco();
		Foo f = doco.fromDocument(doc, Foo.class);

		Assert.assertEquals(doc.getId(), f.getCode());
		Assert.assertEquals(doc.getOnlyField("atomFieldTest").getAtom(), f.getAtomFieldTest());
		Assert.assertEquals(doc.getOnlyField("dateFieldTest").getDate(), f.getDateFieldTest());
		Assert.assertEquals(doc.getOnlyField("geopointFieldTest").getGeoPoint(), f.getGeopointFieldTest());
		Assert.assertEquals(doc.getOnlyField("htmlFieldTest").getHTML(), f.getHtmlFieldTest());
		Assert.assertEquals(doc.getOnlyField("numberFieldTest").getNumber(), f.getNumberFieldTest());
		Assert.assertEquals(doc.getOnlyField("justText").getText(), f.getTextFieldTest());
		Assert.assertEquals(doc.getOnlyField("textFieldWithoutName").getText(), f.getTextFieldWithoutName());
		Assert.assertEquals(doc.getOnlyField("txtName").getText(), f.getTextFieldWithoutType());
		Assert.assertEquals(doc.getOnlyField("textFieldWithoutTypeAndName").getText(), f.getTextFieldWithoutTypeAndName());		
	}

}