## RELEASE NOTES


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
