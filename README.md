# stocks-demo

This demo implements classes for managing stock data.  Stock data is hard-coded in one implementation ('BasicStockService') and stored on a database in others ('DatabaseStockService' and 'DatabasePersonService').  Database is accessed via Hibernate database configuration relying on the existence of a database called 'stocks' granting all privileges to user 'monty' with password 'some_pass'. 
In progress

7/11:
Added XMLUtils class to unmarshal XML data into XML domain objects. Added DatabaseUtils method to convert XML domain objects to ORM database objects. Added new model classes (XML / ORM), updated DatabaseStockService with new method, enabling XML data to persist to database. Also updated/added new unit tests and converted time/date objects from Calendar to Joda DateTime class.

7/13:
-ServiceFactory.createStockService() now accepts enum arguments only
-DatabaseStockQuote and DatabasePersonStock, which contain fields that reference mutable objects, now defensively copy these objects before returning via getter methods
-updated unit tests for said getter methods
-replaced costly looped immutable String concat with mutable StringBuilder in XMLUtils.unmarshal()
