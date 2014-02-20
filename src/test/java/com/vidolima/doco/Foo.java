package com.vidolima.doco;

import java.util.Date;

import com.google.appengine.api.search.GeoPoint;
import com.vidolima.doco.annotation.DocumentField;
import com.vidolima.doco.annotation.DocumentId;
import com.vidolima.doco.annotation.DocumentIndex;
import com.vidolima.doco.annotation.FieldType;

@DocumentIndex
class Foo {

	@DocumentId
	private Integer code;

	@DocumentField
	private String textFieldWithoutTypeAndName;

	@DocumentField(name = "txtName")
	private String textFieldWithoutType;

	@DocumentField(type = FieldType.TEXT)
	private String textFieldWithoutName;

	@DocumentField(name = "justText", type = FieldType.TEXT)
	private String textFieldTest;

	@DocumentField(type = FieldType.HTML)
	private String htmlFieldTest;

	@DocumentField(type = FieldType.ATOM)
	private String atomFieldTest;

	@DocumentField(type = FieldType.NUMBER)
	private Double numberFieldTest;

	@DocumentField(type = FieldType.DATE)
	private Date dateFieldTest;

	@DocumentField(type = FieldType.GEO_POINT)
	private GeoPoint geopointFieldTest;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getTextFieldWithoutTypeAndName() {
		return textFieldWithoutTypeAndName;
	}

	public void setTextFieldWithoutTypeAndName(
			String textFieldWithoutTypeAndName) {
		this.textFieldWithoutTypeAndName = textFieldWithoutTypeAndName;
	}

	public String getTextFieldWithoutType() {
		return textFieldWithoutType;
	}

	public void setTextFieldWithoutType(String textFieldWithoutType) {
		this.textFieldWithoutType = textFieldWithoutType;
	}

	public String getTextFieldWithoutName() {
		return textFieldWithoutName;
	}

	public void setTextFieldWithoutName(String textFieldWithoutName) {
		this.textFieldWithoutName = textFieldWithoutName;
	}

	public String getTextFieldTest() {
		return textFieldTest;
	}

	public void setTextFieldTest(String textFieldTest) {
		this.textFieldTest = textFieldTest;
	}

	public String getHtmlFieldTest() {
		return htmlFieldTest;
	}

	public void setHtmlFieldTest(String htmlFieldTest) {
		this.htmlFieldTest = htmlFieldTest;
	}

	public String getAtomFieldTest() {
		return atomFieldTest;
	}

	public void setAtomFieldTest(String atomFieldTest) {
		this.atomFieldTest = atomFieldTest;
	}

	public Double getNumberFieldTest() {
		return numberFieldTest;
	}

	public void setNumberFieldTest(Double numberFieldTest) {
		this.numberFieldTest = numberFieldTest;
	}

	public Date getDateFieldTest() {
		return dateFieldTest;
	}

	public void setDateFieldTest(Date dateFieldTest) {
		this.dateFieldTest = dateFieldTest;
	}

	public GeoPoint getGeopointFieldTest() {
		return geopointFieldTest;
	}

	public void setGeopointFieldTest(GeoPoint geopointFieldTest) {
		this.geopointFieldTest = geopointFieldTest;
	}

}