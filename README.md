# web-customer-tracker

[Click here to see App]( https://customer-service-crm.herokuapp.com//)


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

<img src="https://user-images.githubusercontent.com/80107049/188763720-d1ec38d0-c497-4b03-8661-ead3073be3e1.png" width=600 />


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

**Refactor: @GetMapping and @PostMapping - Overview**

HTTP Request / Response

<img src="https://user-images.githubusercontent.com/80107049/188727403-2df56185-590d-4cf4-8fed-26c600fd3172.png" width = 300 />

**Most Commonly Used HTTP Methods**

| Method   | Description                            |
| -------- | -------------------------------------- |
| **GET**  | Requests data from from given resource |
| **POST** | Submits data to given resource         |
| others   | ...                                    |


**Sending Data with Get method**

```XML
<form action="processForm" method="GET" ...>
  ...
</form>
```
+ Form data is added to end of URL as name/value pairs
  + **theUrl**?**field1=value1**&**field2=value2...**

**Handling Form Submission**

```JAVA
@RequestMapping("/processForm")
public String processForm(...) {
  ...
}
```
+ This mapping handles **ALLHTTP** methods
  + GET,POST, etc...

**Constrain the Request Mapping . GET**

```JAVA
@RequestMapping(path="/processForm", method=RequestMethod.Get)
public String processForm(...) {
 ... 
}
```
+ This mapping **ONLY** handles **GET** method
+ Any other HTTP REQUEST method will get rejected

_Annotation Short-Cut_

```JAVA 
@GetMapping("/processForm")
public String processForm(...) {
 ... 
}
```
+ New annotation Added in Spring 4.4: **@GetMapping**
+ This mapping **Only** handles **GET** method
+ Any other HTTP REQUEST method will get rejected

**Sending Data with POST method**
```JSP
<form action="processForm" method="POST" ...>
  ...
</form>
```

+ Form data is passed in the body of HTTP request message

<img src="https://user-images.githubusercontent.com/80107049/188727328-9f7f7afd-56f0-4fdf-a46c-25f038740ce6.png" width=400 />

**Constrain the Request Mapping - POST**

```JAVA
@RequestMapping(path="/processForm", method=RequestMethod.POST)
public String processForm(...) {
 ... 
}
```
+ This mapping **ONLY** handles **POST** method
+ Any other HTTP REQUEST method will get rejected

_Annotation Short-cut_

```JAVA 
@PostMapping("/processForm")
public String processForm(...) {
 ... 
}
```
+ New annotation added in Spring 4.3: **@PostMapping**
+ This mapping **ONLY** handles **POST** method
+ Any other HTTP REQUEST method will get rejected


Which one ??

| **GET**                    | **POST**                      |
| -------------------------- | ----------------------------- |
| Good for debugging         | Can't bookmark or email URL   |
| Bookmark or email URL      | No limitations on data length |
| Limitations on data length | Can also send binary data     |


**Refactor: Add a Service Layer - Overview**

<img src="inkdrop://file:g4RmnO3q4" width=400/>

**Purpose of Service Layer**
+ **Service Facade** design pattern
+ Intermediate layer for custom business logic
+ Integrate data form multiple sources (DAO/repositories)

**Integrate Multiple Data Sources**

<img src="https://user-images.githubusercontent.com/80107049/188752643-a776d07a-b4ca-4b86-b26f-96df0393876f.png" width = 500 />

**Specialized Annotation for Services**
+ Spring provides the **@Service** annotation

<img src="https://user-images.githubusercontent.com/80107049/188752579-84c8f063-6016-408a-9b28-eda4d30d3640.png" wdth = 500 />

+ **@Service** applied to Service implementations
+ Spring will automatically register the Service implementation
  + thanks to component-scanning

**Customer Service**
1. Definer Service interface
2. Define Service implementation
   + Inject the CustomDAO

_Step 1: Define Service interface_
```JAVA
public interface CustomerService {
  public List<Customer> getCustomers();
}
```

_Step 2:Define Service implementation_

```JAVA
@Service
public class CustomerServiceImpl implements CustomerService {
  
  @Autowired
  private CustomerDAO customerDAO;
  
  @Transactional
  public List<Customer> getCustomer() {
   ... 
  }
}
```

**Update for the DAO implementation**

```JAVA
@Repository
public class CustomerDAOImpl implements CustomerDAO {
  
  @Autowired
  private SessionFactory sessionFactory;
  
                          // Remove @Transactional 
  public List<Customer> getCustomers() {
   ... 
  }
}
```
+ **@Transactional** now defined at service layer

**Question**

Why do we have to create Service layer what has the same functions as DAO layer? Is it necessary to create all this layers?


**Answer**

Agreed, there are a lot of layers. However this is the architecture that you will see on real world, complex Spring projects.

In our example, it is fairly simple. We simply delegate the calls to the DAO. So I agree, you could remove the service layer in this simple example and have controller call dao directly.

However, we added the service layer to leverage the [**Service Layer design pattern**](https://en.wikipedia.org/wiki/Service_layer_pattern). On a much more complex project, we could use the service layer to integrate multiple data sources (daos) and perform transaction management between the two. So, for a simple project that we have here ... this probably overkill.

And here is a another scenario where you would like to perform transaction management at the service layer.

You can use @Transactional at the service layer if you want DAO methods to run in the same transaction.

Say for example we have

BankDAO

\- deposit(...)

\- withdraw(...)

If we are transferring funds, we want that to run in the same transaction. By making use of @Transactional at service layer, then we can have this transactional support and both methods will run in the same transaction. This would call deposit() and withdraw(). If either of those methods failed then we'd want to roll the transaction back.

However, if we had @Transactional at DAO level instead of service level, then the methods deposit() and withdraw() would run in separate transactions. If one of them failed, then we would not be able to rollback the other method ... because it is in a separate transaction.

So that's one real-time project use case for applying @Transactional at the Service layer.

\===

Of course, in your personal project, there is no strict requirement to use layers. In fact, there is no requirement to use DAO. You could add all of your code to one controller class. But from an architectural point of view, that would result in a poor design.



**Add Customer - Overview**

1. **Update list-customer.jsp**
   1. New "Add Customer" button
2. **Create HTML form for new customer**
3. **Process Form Data**
   1. Controller -> Service -> DAO


**Update Customer**

1. **Update list-customer.jsp**
  1. New "Update"link
2. **Create customer-form.jsp**
  1. Prepopulate the form
3. **Process form data**
  1. **Controller -> Service -> DAO**


**Add Search support - Overview of Development Process**
1. Create the HTML form
2. Add mapping to the controller
3. Add methods in the service layer to delegate to DAO
4. Add method in the DAO to perfom search         


**1. Create the HTML form**

You need to add a search form to read the user input and submit it to your Spring controller mapping

a. Edit the file: list-customers.jsp

b. We'll need to use Spring FORM tags, so at the  top of the file, add the following taglib reference

```JAVA
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>```


c. Now add a search form right after the Add Customer button
```JSP
<!--  add a search box -->
<form:form action="search" method="GET">
  Search customer: <input type="text" name="theSearchName" />
  <input type="submit" value="Search" class="add-button" />
</form:form>
```

**2. Add mapping to the controller**

You need to add a mapping to handle the search form submission

a. Edit the file: CustomerController.java

b. Add the new mapping and method

```JAVA
  @GetMapping("/search")
    public String searchCustomers(@RequestParam("theSearchName") String theSearchName,
                                    Model theModel) {
        // search customers from the service
        List<Customer> theCustomers = customerService.searchCustomers(theSearchName);
                
        // add the customers to the model
        theModel.addAttribute("customers", theCustomers);
        return "list-customers";        
    }
```
c. You may have syntax errors on the customerService, but we'll resolve that in the next section.

\---

**3. Add methods in the service layer to delegate to DAO**

You need to add methods in the service layer to delegate calls to the DAO

a. Edit the file: CustomerService.java

b. Add the method declaration
```JAVA
public List<Customer> searchCustomers(String theSearchName);
```


c. Edit the file: CustomerServiceImpl.java

d. Add the method:

```JAVA
  @Override
    @Transactional
    public List<Customer> searchCustomers(String theSearchName) {
        return customerDAO.searchCustomers(theSearchName);
    }
```

e. You may have syntax errors on the customerDAO, but we'll resolve that in the next section.

---

**4. Add method in the DAO to perfom search**

Now, we'll add methods in the DAO layer to search for a customer by first name or last name

a. Edit the file: CustomerDAO.java

b. Add the method declaration
``` JAVA
public List<Customer> searchCustomers(String theSearchName);
```

c. Edit the file: CustomerDAOImpl.java

d. Add the method:
```JAVA
   @Override
    public List<Customer> searchCustomers(String theSearchName) {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();
        
        Query theQuery = null;
        
        //
        // only search by name if theSearchName is not empty
        //
        if (theSearchName != null && theSearchName.trim().length() > 0) {
            // search for firstName or lastName ... case insensitive
            theQuery =currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
        }
        else {
            // theSearchName is empty ... so just get all customers
            theQuery =currentSession.createQuery("from Customer", Customer.class);            
        }
        
        // execute query and get result list
        List<Customer> customers = theQuery.getResultList();
                
        // return the results        
        return customers;
        
    }
```
In this method, we need to check "theSearchName", this is the user input. We need to make sure it is not empty. If it is not empty then we will use it in the search query.  If it is empty, then we'll just ignore it and simply return all of the customers.

For the condition when "theSearchName" is not empty, then we use it to compare against the first name or last name. We also make use of the "like" clause and the "%" wildcard characters. This will allow us to search for substrings. For example, if we have customers with last name of "Patel", "Patterson" ... then we can search for "Pat" and it will match on those names.

Also, notice the query uses the lower case version of the values to make a case insensitive search. If you'd like to make a case sensitive search, then simply remove the lower references.

You can read more on the HQL "like" clause here:
http://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#hql-like-predicate



**Add Sorting support - Overview of Development Process**

1. Create a Utility class for sort constants

2. In JSP page, add sort links for column headers

3. Update controller to read sort field

4. Update method in the service layer to delegate to DAO

5. Update method in the DAO to get customers sorted by given field


DETAILED STEPS

1. Create a Utility class for sort constants

This utility class will hold constant values for the sort fields. The values can be anything, as long as you stay consistent in the app.

File:SortUtils.java

```JAVA
 
public interface SortUtils {
	
	public static final int FIRST_NAME = 1;
	public static final int LAST_NAME = 2;
	public static final int EMAIL = 3;
 
}
```

2. In JSP page, add sort links for column headers

In this page, the user can click on the "First Name" column header and it will sort the data accordingly. The links will have an embedded a sort key.

The code below defines a link for the first name. Note the use of SortUtils.FIRST_NAME.

File:list-customers.jsp
```JSP
<%@ page import="com.luv2code.springdemo.util.SortUtils" %>
...
				<!-- construct a sort link for first name -->
				<c:url var="sortLinkFirstName" value="/customer/list">
					<c:param name="sort" value="<%= Integer.toString(SortUtils.FIRST_NAME) %>" />
				</c:url>
        ```
We can do a similar thing for last name and email.
```JSP
				<!-- construct a sort link for last name -->
				<c:url var="sortLinkLastName" value="/customer/list">
					<c:param name="sort" value="<%= Integer.toString(SortUtils.LAST_NAME) %>" />
				</c:url>					
 
				<!-- construct a sort link for email -->
				<c:url var="sortLinkEmail" value="/customer/list">
					<c:param name="sort" value="<%= Integer.toString(SortUtils.EMAIL) %>" />
				</c:url>					
```

Then for the column headings, we set up the `<a href>` using the the appropriate link.

```JSP
				<tr>
					<th><a href="${sortLinkFirstName}">First Name</a></th>
					<th><a href="${sortLinkLastName}">Last Name</a></th>
					<th><a href="${sortLinkEmail}">Email</a></th>
					<th>Action</th>
				</tr>
```

3. Update controller to read sort field

In the CustomerController, we need to update the method to read the sort field. If not sort field is provided, then we just default to SortUtils.LAST_NAME.

File:CustomerController.java

```JAVA
	@GetMapping("/list")
	public String listCustomers(Model theModel, @RequestParam(required=false) String sort) {
		
		// get customers from the service
		List<Customer> theCustomers = null;
		
		// check for sort field
		if (sort != null) {
			int theSortField = Integer.parseInt(sort);
			theCustomers = customerService.getCustomers(theSortField);			
		}
		else {
			// no sort field provided ... default to sorting by last name
			theCustomers = customerService.getCustomers(SortUtils.LAST_NAME);
		}
		
		// add the customers to the model
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}
```


4. Update method in the service layer to delegate to DAO

Now, we update the getCustomers(int theSortField) method to accept an int parameter. This is for the service interface and service implementation.

File:CustomService.java
```JAVA
public interface CustomerService {
 
	public List<Customer> getCustomers(int theSortField);
 
	public void saveCustomer(Customer theCustomer);
 
	public Customer getCustomer(int theId);
 
	public void deleteCustomer(int theId);
	
}
```

File:CustomerServiceImpl.java
```JAVA
@Service
public class CustomerServiceImpl implements CustomerService {
 
	// need to inject customer dao
	@Autowired
	private CustomerDAO customerDAO;
	
	@Override
	@Transactional
	public List<Customer> getCustomers(int theSortField) {
		return customerDAO.getCustomers(theSortField);
	}
 
	...
}


5. Update method in the DAO to get customers sorted by given field

In DAO interface, update the method to accept integer

File: CustomerDAO.java

package com.luv2code.springdemo.dao;
 
import java.util.List;
 
import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.util.SortUtils;
 
public interface CustomerDAO {
 
	public List<Customer> getCustomers(int theSortField);
	...
}
```

In the CustomerDAOImpl.java, the getCustomers(...) method has theSortField parameter. It will determine the sort field name based on the parameter.

File:CustomerDAOImpl.java

```JAVA
@Repository
public class CustomerDAOImpl implements CustomerDAO {
 
	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
			
	@Override
	public List<Customer> getCustomers(int theSortField) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		// determine sort field
		String theFieldName = null;
		
		switch (theSortField) {
			case SortUtils.FIRST_NAME: 
				theFieldName = "firstName";
				break;
			case SortUtils.LAST_NAME:
				theFieldName = "lastName";
				break;
			case SortUtils.EMAIL:
				theFieldName = "email";
				break;
			default:
				// if nothing matches the default to sort by lastName
				theFieldName = "lastName";
		}
		
		// create a query  
		String queryString = "from Customer order by " + theFieldName;
		Query<Customer> theQuery = 
				currentSession.createQuery(queryString, Customer.class);
		
		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();
				
		// return the results		
		return customers;
	}
 
	...
}
```

As you can see, there is a switch statement for theSortField. Based on the value, then it will use field name of "firstName", "lastName" etc. If the values don't match, then we default to sorting by lastName.
