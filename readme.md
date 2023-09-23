# Trouble shooting some common errors
### Database errors
- [1. Server is running on the host and accepting TCP/IP connections at the port, and that no firewall is blocking TCP connections to the port](#db1)

### App errors

### Notices
When you want to update the database schema arcording to the model change (like removing a property and you want to remove the field of the table in database), set ddl-auto: create-drop (this will drop current database and then create new database schema) and run code.
After that, set ddl-auto: update again.
(resources/application.yml)

Why?
- [1. Spring Boot `spring.jpa.hibernate.ddl-auto` Property](#n1)

<div id='n1'></div>
nav

## 1. Spring Boot `spring.jpa.hibernate.ddl-auto` Property

In a Spring Boot application, the `spring.jpa.hibernate.ddl-auto` property is used to control the behavior of Hibernate (the JPA implementation) regarding database schema generation and management during application startup. This property specifies how Hibernate should handle the database schema in relation to your JPA entities. There are several options for this property:

- **none:**
   - Hibernate does not perform any database schema management.
   - You are responsible for creating and maintaining the database schema manually.

- **create:**
   - Hibernate creates a new database schema when the application starts.
   - If the database schema already exists, it drops the existing schema and creates a new one.
   - This option is typically used for development or testing purposes.

- **create-drop:**
   - Hibernate creates a new database schema when the application starts.
   - When the application shuts down, it drops the database schema.
   - This is useful for development and testing, where you want to start with a clean database for each run of the application.

- **update:** (WE USE THIS)
   - Hibernate checks the database schema at startup and updates it to match the current state of the JPA entity classes.
   - It adds new tables, columns, and constraints and updates existing ones if necessary.
   - This option is often used in development and testing to automatically evolve the database schema as the entity classes change.

- **validate:**
   - Hibernate only validates the database schema against the entity classes.
   - It does not make any changes to the schema.
   - If there are discrepancies between the schema and the entity classes, it will throw an exception.
   - This option is typically used in production to ensure that the database schema matches the expected state.

The choice of `spring.jpa.hibernate.ddl-auto` option depends on your development and deployment scenarios:

- For production environments, it's common to use `validate` to prevent automatic schema changes and ensure that the database schema is already in sync with the application.

- During development and testing, you might use `update` or `create-drop` to simplify the process of evolving the database schema as your entity classes change.

- In situations where you want full control over the database schema, you can use `none` and manage schema changes manually.

**Note:** Automatic schema generation and modification should be used with caution in production environments, as they can lead to data loss or unexpected schema changes. Always make sure to have proper backups and migration strategies when working with databases in production.

<div id='db1'></div>
nav

## 2. Server is running on the host and accepting TCP/IP connections at the port, and that no firewall is blocking TCP connections to the port

	Full error: com.microsoft.sqlserver.jdbc.SQLServerException: The TCP/IP connection to the host localhost, port 1433 has failed. Error: “Connection refused: connect. Verify the connection properties, check that an instance of SQL Server is running on the host and accepting TCP/IP connections at the port, and that no firewall is blocking TCP connections to the port.”.
	
**Solution**
1. From the **Start menu**, open **SQL Server 2022 Configuration Manager**.
2. Click Protocol for **SQLSERVEREXPRESS** (or SQLSERVERDEVELOPER,... the name of your currenly running instance) under **SQL Server Network Configuration** on the left pane. On the right pane, right- click **TCP/IP**, and select **Properties**.
3. On the **TCP/IP Properties** dialog box that appears, click the **IP Addresses** tab.
4. Scroll down to locate the **IPALL** node. Remove any value, if present for **TCP Dynamic Ports** and specify **1433** for **TCP Port**.


