# PuppiesAPI
 REST service implemented using Spring Boot and MongoDB

This Puppies API example project shows the use-case of a simple Spring Boot web service REST application with MongoDB backend.

The web service exposes seven endpoints:

1.	/createUser with POST request parameters: user (User object), this endpoint adds a new user to the database based on the User object passed and returns a success message.

2.	/authUser with POST request parameters: user (User object), this endpoint adds a new user to the database based on the User object passed and returns a message stating the authentication status.

3.	/fetchUserDetails with GET request parameters: userId, this endpoint fetch User details from the database and returns User object.

4.	/createNewPost with PATCH request parameters: userId and post (Post object) searches for a User by the specified userId, new Post will be updated for the same user.

5.	/fetchPost with GET request parameters: postId, this endpoint fetches individual post details from the database based on the parameter passed and returns a Post Object.

6.	/fetchPosts with GET request parameters: userId, searches for all posts posted by the specified user from database and returns us a list of Post objects.

7.	/likePost with POST request parameters: user Object and postId, this endpoint updates the existing Post object with an attribute of UserId stating that the user likes the Post in the database based on the parameter values specified.

In the background, the endpoints use embedded MongoDB to store the data. All of the data is stored through a MongoRepository, which allows specification of custom queries. Spring creates the specific queries in the background (specifically for MongoDB).

The easiest way to run the service is by packaging a jar file using maven and invoking java -jar Puppies-0.0.1-SNAPSHOT.jar this will run a new Spring Boot application which uses an embedded server to execute the service and expose the endpoints to port 8080 on the localhost address (127.0.0.1).

The way to invoke the endpoints would be to use POSTMAN tool and explore the service



Instructions for project setup:

Pre- requisites:
???	Java JDK needs to be installed
???	Mongo DB server needs to be installed and should be up and running.
???	Mongo DB Compass can be used as UI interface to see the results.
???	IntelliJ is required for Java coding and jUnit testing.
???	Postman tool is used to explore the service Puppies API developed.

Highlights:

	Used SpringBoot with mongoDB for easy data handling, highly scalable and efficient code.

Limitations:

	There is a limitation to work on manipulation of values inside nested embedded documents and write custom queries in it.

	There is also a small issue to run the integration testing due to failure in loading Application context.





