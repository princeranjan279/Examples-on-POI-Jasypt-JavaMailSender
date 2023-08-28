
						 			------SPRING CORE------    														

SPRING FRAMEWORK:
- A spring framework provides a model of configuration and comprehensive programming for the development
  of any java application.
- The major goal of spring is to handle infrastructure or configuration so that the developers
- Spring helps the developers to build applications using POJO (Plane Old Java Objects)

SPRING ARCHITECHTURE:
- Data Access / Integration (JDBC
							 ORM 
							 OXM 
							 JMS 
							 Transactions)
- Web (Web
	   Servlet
	   Portlet
	   Struts)
- AOP
- Aspects
- Instrumentation
- Core Container (Beans
				  Core
				  Context
				  Expression Language)
- Test

- The author of spring framework is Rod Jhonson.
- The Beta version of spring was released in the year 2002 (before the industry usage).
- Spring framework is combination of multiple modules like Core-Container, Data Access or Integration,
  Spring Web, AOP, Aspects, Instrumentation, Spring Test or Test.
- Using spring framework a developer can develop the web applications which follows
  stand-alone architecture or Monolithic Architecture.

DEPENDENCY INJECTION
- Dependency Injection is nothing but the process of injecting or assigning one object  into 
  another object automatically.
- In spring we consider most of the classes and interfaces as `beans`.

Inversion-Of-Control (IOC)
- The process of allocating the control of an object from one component (developer) to another
  component (spring) is known as Inversion control.
- In other words, all object information or bean information will be stored in spring.xml file, which
  will be read by BeanFactory or ApplicationContext which intern is responsible for the object creation
  automatically in the spring container.
- Here, the control over java objects and their life cycle is not handled by the developers, instead
  it is handled automatically by the spring, hence the name IOC.

CORE CONTAINER
- The core container in spring is a combination of Bean, Core, Context and SpEL modules.
- The Beans and Core modules provides fundamental features of Spring Framework. Such as, DI and IOC.
- The context module uses beans and core module in order to build a solid base for your application.
- The context module inherits the features from Beans and Core modules and also it supports features like 
  Internationlization, resource loading, handling servlets, supporting remote access etc.,
- In the context module the application context is the main focal point.

EXPRESSION LANGUAGE (SpEl)
- In spring we use SpEl for querying and manupulating an object graph at runtime.
- This language also supports the setter and getter properties from the spring IOC container.

DATA ACCESS / INTEGRATION
	JDBC
	- The JDBC module provides an abstract layer to spring in order to avoid tedious coding from the 
	  library.
	
	ORM
	- This module provides integration layers for popular ORM Tools such as Hibernate, EclipseLink,
	  TopLink, etc.,
	
	OXM
	- The OXM module provides an abstract layer that supports Object XML mapping Implementation for
	  JaxP, XML Beans, Castor, etc.,
	
	JMS
	- The java messaging service module containing features for producing and consuming messages.
	
	Transaction
	- this module supports declarative and programmatic transaction management that implements 
	  special interfaces for all the POJOs.
	
	
WEB
- The spring web module provides basic web oriented features, such as, handling Multipart file upload,
  Initialization of IOC container using servlets,
- It also contains web related features for spring remote support.
- The web servlet module spring MVC implementation for web applications.
- The spring MVC framework provides a clean seperation between web forms and domain model code.

AOP
- Aspect-Oriented-Programming implementation in spring allows the developers to define method interceptors, 
  Point cuts, etc.,

INSTRUMENTAION
- This module provides supports for micro-devices, class-loaders, server-implementation, etc.,

TEST
- The test Module supports the testing of spring components with the libraries like JUnit, Mockito,
  TestNG, etc.,
- The test module provides Mock objects that helps the developers test the application in Isolation.

