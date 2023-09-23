#1 Spring Boot `spring.jpa.hibernate.ddl-auto` Property

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
