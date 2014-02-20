# Doco
Doco (Document Converter) is a lightweight Java library used to convert (from and to) indexed Documents provided by Search API in Google App Engine.

### Homepage
http://www.vidolima.com/projects/doco

# How to Use Doco

Before converting objects into documents (or vice versa), you need to tell Doco which fields will be used. Maps the fields you want to search in a document with following annotations.

## Annotations:

### @DocumentIndex

This annotation is applied to the entity class. This annotation defines the name of the IndexSpec.
Doco by default assume the name of the class as index id
```java
// the name of the index id will be "Foo"
@DocumentIndex
public class Foo {}
```
You can set the index id as you want using the parameter ___name___
```java
@DocumentIndex(name = "customIndexId")
public class Foo {}
```
### @DocumentId

Place this annotation on fields of an entity POJO. This field defines the id of the document.
The type of a field annotated with ___DocumentId___ must be ___Integer___, ___Long___ or ___String___.

```java
@DocumentId
private Long id;
```

You can specify a name to an id using ___name___ parameter

```java
@DocumentId(name = "otherIdName")
private Long id;
```

### @DocumentField

Place this annotation on fields of an entity POJO. This annotation defines a field of a document.
All fields must have a name and a type (see all types here).
Doco assumes the same name of the annotated property to the name of the document field if the name was not specified, and assumes ___TEXT___ type by default.

```java
@DocumentField
private String textField;
```
Use de ___name___ property to specify a name to the field
```java
@DocumentField(name = "otherName")
private String textField;
```
You can specify the field type using the ___type___ parameter
```java
@DocumentField(type = FieldType.NUMBER)
private Double total;
```

## Conversions:

Converting an object and putting it in a index

```java
// just convert a Foo to a Document
Doco doco = new Doco();
Document document = doco.toDocument(foo);
 				
// gets an Index and saves the Document
Index index = doco.getIndex(Foo.class);
index.put(document);
```

Converting a document to an object

```java
// gets the document from index
Doco doco = new Doco();
Index index = doco.getIndex(Foo.class);
Document document = index.get(12345L);

// simple way to convert a document to a Foo
Foo foo = doco.fromDocument(document, Foo.class);
```

See the [site] (http://www.vidolima.com/projects/doco) for more details

# Requirements
* Java 1.5+
* Google App Engine Java SDK 1.8.5+

# TODO
* Default type for: NUMBER, DATE and GEO_POINT
* Convert collections

# See Also
Search API documentation
https://developers.google.com/appengine/docs/java/search/

# License
Doco is released under the terms of the MIT license.
