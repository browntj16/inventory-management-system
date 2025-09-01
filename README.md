# Inventory Management System

Created a local Oracle database on my PC and this was the first program I wrote with it. If anything, setting up and familiarizing myself with the database was the hardest part of this. 

So what does this program do exactly? Basically, it lets you add and remove items from an inventory. The inventory table has 3 columns: a UPC code, a name, and a quantity. The UPC code is the primary key to this table. UPC codes are those numbers that are on barcodes if you weren't familiar. Anyways, how does adding items to the table work exactly? Well, if there's no entry with the same UPC, then you can just add the item to the table no questions asked. However, if we find that we already have that item in the table then we instead need to add that amount to the quantity. That is, we need to retrieve the quantity of the item already in the table, add the amount we're adding to that number, then replace that quantity in the table. It also supports other basic operations like removing all or a quantity of an item from the table. 

My implementation of this concept uses primarily two classes: databaseConnection and invManager. databaseConnection just simplifies JDBC operations into functions to make it simpler for me. invManager takes that databaseConnection object and uses it to perform operations like adding and subtracting from the inventory table. The main class just executes these and gives a console based interface to work with. 

As one last note, the .jar file in the lib folder is the Oracle JDBC driver.
