## RELEASE NOTES

## Version 2.1.0 - Mar 6, 2017
**New**
- Added feature where you can specify the an index for the subclass instead of forcing simpleName

**Fixes**
- ReflectionUtil getAnnotatedFields() method has an over counting issue.

## Version 2.0.0 - Mar 17, 2015
**New**
- Introducing dependency on Objectify.
- Doco can now translate Ref<?> fields using @DocumentRef annotation.
- Supporting embedded fields using @DocumentEmbed annotation. This is useful when an entity contains other classes which has fields needing indexing.

## Version 1.2.0 - Sep 28, 2014
**Updates**
- Doco should not be 'final' so that it can be mocked using Mockito
- Replacing cast to String by String.valueOf(object).
- Adding doco formatter to avoid formatting issues in code review.


## Version 1.1.0 - Sep 22, 2014
**New**
- Allowing DocumentId to be specified explicitly. Useful for users define id of document (e.g. 'Key' of a datastore entity).


## Version 1.0.0 - Sep 19, 2014

**New**
- Entities can have both long and Long as field type (same for int, double, float).
- Null GeoPt and Date fields are ignored instead of throwing exception.
- Polymorphic types are handled using @DocumentIndexSubClass annotation (added test for same).

**Updates**
- Updated app-engine version to be 1.9.5 and java compiler to 1.7 (this should probably be left for cnosumer to decide).


### Version 0.3.2 - Mar 3, 2014

**Fixes**
- DocumentParser.getSearchNumberField(): NullPointerException
- ObjectParser.getDocumentFieldValue(): java.lang.IllegalArgumentException - if the document does not have exactly one field with the name


### Version 0.3.0 - Mar 1, 2014

**New**
- To permit more types (Double, Integer, Long, Float and primitive numbers) for NUMBER field

**Updates**
- Refactoring of the class ObjectParser


### Version 0.2.0 - Feb 19, 2014

**New**
- Improvement to permit types String, Long or Integer for DocumentId fields


### Version 0.1.0 - Feb 11, 2014

**New**
- Added annotation @DocumentIndex
- Added exception AnnotationNotFoundException

**Fixes**
- Java 1.5 compatibility. Changed "String.isEmpty()" to "String.trim().length() < 0"

**Updates**
- Updated license
