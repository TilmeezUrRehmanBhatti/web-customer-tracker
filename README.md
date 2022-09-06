# web-customer-tracker
Build a Database Web App - Spring MVC and Hibernate Project 


**Set Up Dev Environment**
1. Define database dataSource / connection pool
2. Setup Hibernate session factory
3. Setup Hibernate transaction manager
4. Enable configuration of transactional annotations

**Placement of Configurations**

Add the following configurations in your Spring MVC Configuration file.

For our example spring-mvc-crud-demo-servlet.xml

_Step 1:Define database dataSource / connection pool_
```XML
<bean id="myDataSource" class="com.mchange.v2.c3p0.CombopooledDataSource"
      destroy.method="close">
  
  <property name="driverClass" value="org.postgresql.Driver" />
  <property name="jdbcUrl" 
            value="jdbc:postgresql://ec2-52-212-228-71.eu-west-1.compute.amazonaws.com:5432/d5qnu5vfhts8c2" />
  
  <property name="user" value="userName" />
  <property name="password" value="password" />
  
  <!-- these are connection pool properties for c3P0 -->
  <property name="minPoolSize" value="5" />
  <property name="maxPoolSize" value="20" />
  <property name="maxIdleTime" value="30000" />
</bean>
```

* Source: <https://www.mchange.com/projects/c3p0/>

c3p0 is an easy-to-use library for making traditional JDBC drivers "enterprise-ready" by augmenting them with functionality defined by the jdbc3 spec and the optional extensions to jdbc2. As of version 0.9.5, c3p0 fully supports the jdbc4 spec.

In particular, c3p0 provides several useful services:

* A class whichs adapt traditional DriverManager-based JDBC drivers to the newer `javax.sql.DataSource` scheme for acquiring database Connections.
* Transparent pooling of Connection and PreparedStatements behind DataSources which can "wrap" around traditional drivers or arbitrary unpooled DataSources.

_Step 2:Setup Hibernate session factory_
```XML
<bean id="sessionFactory"
      class="org.springframework.orm.hbernate5.LocalSessionFactoryBean">
  
  <property name="dataSource" ref="myDataSource" />
  <property name="packageToScan" value="com.tilmeez.springdemo.entity" />
  
  <property name="hibernateProperties">
    <props>
      <prop keys="hibernate.dialect">org.hibernate.dialect.PostgreSQLDialect</prop>
      <prop key="hibernate.show_sql">true</prop>
    </props>
  </property>
  
</bean>
```

+ ` <property name="dataSource" ref="myDataSource" />` it refers to bean defined in Step 1
+ `<property name="packageToScan" value="com.tilmeez.springdemo.entity" />` Scan for Hibernate @Entity classes
+ `<property name="hibernateProperties">` we can specified the Hibernate properties here we have
    + Specified dialect to use postgresql

_Step 3:Setup Hibernate transaction manager_
```XML
<bean id="myTransactionManager"
      class="org.springframework.orm.hibernate5.HibernateTransactManager" >
  
  <property name="sessionFactory" ref="sessionFactory" />
  
</bean>
```
+ ` <property name="sessionFactory" ref="sessionFactory" />` refers to bean with id sessionFactory defined in step 2
    + Normally when we write a hibernate code we start transaction in code, spring has support so, we can eliminate code in DAO  class

_Step 4:Enable configuration of transactional annotations_
```XML
<tx:annotaion-driven transaction-manager="myTransactionManager" />
```
+ Spring provides annotation `@Transactional`.
    + This allows to minimize or eliminate some of coding for manually starting and stopping transaction.


**Customer Controller**

<img src="https://user-images.githubusercontent.com/80107049/188608263-4639b03c-9e5b-47ed-bcf2-533d51b84359.png" width= 400 />

**Sample App Features **
+ List Customers
+ Add a new Customer
+ Update a Customer
+ Delete a Customer

<img src="at last" width=600 />


**Customer Data Acess Object**

+ Responsible for interfacing with the database
+ This is a common design pattern: **Data Access Object (DAO)**

<img src="https://user-images.githubusercontent.com/80107049/188624548-c13aa356-e4e9-4088-be59-93d27632e8ab.png" width = 400 />



| Methods              |
| -------------------- |
| saveCustomer(...)    |
| getCustomer(...)     |
| getCustomers()       |
| updateCustomer(...)  |
| deleterCustomer(...) |



**List Customers**
1. Create **Customer.java**
2. Create **CustomerDAOImpl.java**
  1. and **CustomerDAOImpl.java**

3. Create **CustomerControllre.java**
4. Create JSP page: **list-customers.jsp**


**Object-to-Relational Mapping (ORM)**

<img src="https://user-images.githubusercontent.com/80107049/188624469-dae5ca47-d66b-43e9-86e3-d304d2413b7c.png" width = 500 />



_Step 1: Map class to database table_
```JAVA
@Entity
@Table(name="customer")
public class Customer {
  
  ...
}
```

_Step 2: Map fields to database columns_
```JAVA
@Entity
@Table(name="customer")
public class Customer {
  
  @ID
  @Column(name="id")
  private int id;
  
  @Column(name="first_name")
  private String firstName;
          
 ...
}
``` 
**Entity Scanning**
+ In our Soring MVC config file

```XML
<bean id="sessionFactory"
      class="org.springframwork.orm.hibernate5.LocalSessionFactoryBean">
  
  <property name="dataSource" ref="myDataSource" />
  <property name="packagesToScan" value="com.tilmeez.springdemo.entity" />
  
  ....
</bean>
```
+ `property name="packagesToScan"` Scan the package(s) and look for annotated @Entity classes
  + For multiple packages, Give a comma-delimited list

**List Customers - Developing Hibernate DAO**
+ For Hibernate , our DAO meeds a Hibernate SessionFactory

<img src="https://user-images.githubusercontent.com/80107049/188639627-59792a33-f060-49dd-9dd1-f9efa5330eaa.png" width=500 />

**Hibernate Session Factory**
+ Our Hibernate Session Factory needs a Data Source
  + The data source defines database connection info

<img src="https://user-images.githubusercontent.com/80107049/188639489-6dd27da3-4127-4110-a329-d25d476adf88.png" width=500 />

> **Dependencies**
> > These are all dependencies!
> >
>> We will wire them together with Dependency Injection (ID)
>>

**Data Source**
```XML
 <bean id="myDataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource"
          destroy-method="close">
        <property name="driverClass" value="org.postgresql.Driver"/>
        <property name="jdbcUrl"
                  value="jdbc:postgresql://ec2-52-212-228-71.eu-west-1.compute.amazonaws.com:5432/d5qnu5vfhts8c2&amp;serverTimezone=UTC"/>
        ... user id, password etc ...
</bean>
```

**Session Factory**
```XML
<bean id="sessionFactory"
      class="org.springframwork.orm.hibernate5.LocalSessionFactoryBean">
  
  <proerty name="dataSource" ref="myDataSource" />
  
  ...
</bean>
```
+ ` ref="myDataSource"` is the bean id that is define in data source

**Customer DAO**
1. Define DAO interface
2. Define DAO implementation
  + Inject the session factory

_Step 1:Define DAO interface_
```Java
public interface CustomerDAO {
  public List<Customer> getCustomer();
}
```

_Step 2:Define DAO implementation_
```JAVA
publiv class CustomerDAOImpl implements CustomerDAO {
  
  @Autowired
  private SessionFactory sessionFactory;
  
  public List<Customer> getCustomers() {
   ... 
  }
}
```
+ @Autowired for Dependency injection
  + Spring will look in the Config for the bean id "sessionFactory"

**Spring @Transactional**
+ Spring provides an **@Transactional** annotation
+ **Automatically** begin and end a transaction for Hibernate code
  + No need for to explicitly do this in code

- Standalone Hibernate code
```Java
// start a transaction 
session.beginTransaction();

// DO HIBERNATE STUFF
// ...

// commit transaction
session.getTransaction().commit();
```
- Spring @transactional
```JAVA
@Transactional
public List<Customer> getCustomer() {
  
  // get the current hibernate session
  Session currentSession = sessionFactory.getCurrentSession();
  
  // create a query
  Query<Customer> theQuery =
    currentSession.createQuery("from Customer", Customer.class);
  
  // get the result list
  List<Customer> customers = theQuy.getResultList();
  
  return customers;
}
```

**Specialized Annotation for DAOs**
+ Spring provides the **@Repository** annotation

<img src="https://user-images.githubusercontent.com/80107049/188639395-473eb7b3-fe30-4cad-9401-d3c1f1c15023.png" width=500 />


+ Applied to DAO implementations
+ Speing will automatically register the DAO implementation
  + thanks to component-scanning

+ Spring also provides translation of any JDBC related exception

**Updates for the DAO implementation**
```JAVA
@Repository
public class CustomerDAOImpl implements CustomerDAO {
  
  @Autowired
  private SessionFactory sessionFactory;
  
  @Transactional
  public List<Customer> getCustomers() {
   ... 
  }
}
```

**Adding CSS to Spring MVC App**

*First Version - Plain*

<img src="https://user-images.githubusercontent.com/80107049/188720703-e9b277e3-f16c-4443-add1-1e02f3c8d57d.png" width = 500 />


**Development Process**
1. Place CSS in a `resources` directory
  + Directory name is configurable
2. Configure Spring to serve up `resources`
3. Reference CSS in your JSP

_Step 2:Configure Spring to serve up "resources" directory_

File: spring-mvc-crud-demo-servlet.xml
```XML
<mvc:resources loaction="/resouces/" mapping="/resources/*" />
```

+ `loaction="/resouces/"` physical directory name
+ `mapping="/resources/*" ` url mapping, * to recurse subdirectories

_Step 3:Reference CSS in your JSP_

File:list-customers.jsp
```JSP
<head>
  <title>List Customers</title>
  
  <link type="text/css"
        rel="stylesheet"
        href="${pageContext.request.contextPath}/resources/css/style.css">
  
</head>
```

+ In href `${pageContext.request.contextPath}`use proper app name , `/resources/css/style.css` is our style sheet

**Alternate Directory Structure**

File:spring-mvc-crud-demo-servlet.xml
```XML
<mvc:resources loaction="/resouces/" mapping="/resources/*" />
<mvc:resources loaction="/images/" mapping="/images/*" />
<mvc:resources loaction="/js/" mapping="/js/*" />
<mvc:resources loaction="/pdf/" mapping="/pdf/*" />
```
