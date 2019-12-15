**main contain: Spring boot2.2 + MyBatis + MySQL + graphql + WebSocket.**

Open application.properties, you can change the database infos.

Open tutorial.graphqls, you can change the query and the mutation infos.

1. Create the table.

CREATE TABLE users (
  id varchar(45) NOT NULL,
  fullname varchar(10) DEFAULT NULL,
  account varchar(20) NOT NULL,
  password varchar(45) DEFAULT NULL,
  mobile varchar(11) NOT NULL,
  email varchar(45) NOT NULL,
  gender int(1) DEFAULT '0',
  description varchar(200) DEFAULT NULL,
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  update_time datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY account_UNIQUE (account),
  UNIQUE KEY mobile_UNIQUE (mobile),
  UNIQUE KEY email_UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


2. Initialize data.

insert into users(id,fullname,account,password,mobile,email,gender) values(uuid(),'Administrator','admin',md5('12345678'), '13211111111','admin@penguin.org',1),(uuid(),'Tester 1','tester1',md5('12345678'), '13211111112','tester1@penguin.org',0)

3. test query and mutation functions using iql

http://localhost:8080/graphiql

a. get all users.

{
  listUsers {
    id
    fullname
    account
    gender
    createTime
  }
}

b. query user by unique account

{
  getUser(account:"admin") {
    id
    fullname
    gender
    createTime
  }
}

c. Create a new user.

mutation {
  createUser(input: {fullname: "Employee 10", account: "employee10", email: "employee10@penguin.org", mobile: "13211111114",}) {
    id
    fullname
  }
}

If you don't test them using iql, please call hql API.

post http://localhost:8080/graphql

{"query":"query {getUser(account:\"admin\") {id fullname gender createTime}}","variables":null}

{"query":"mutation {createUser(fullname: \"Employee 11\", account: \"employee11\", email: \"employee11@penguin.org\", mobile: \"13211111115\") {id fullname}}","variables":null}

After clone the codes, and modify the database root's password, then you can run it. 

cd graphqlServer

mvn spring-boot:run

**graphqlServer contain: Graphql server Demo Using Spring boot2.2 + MyBatis + MySQL.**

Open application.properties, you can change the database infos.

Open tutorial.graphqls, you can change the query and the mutation infos.

1. Create the table.

CREATE TABLE users (
  id varchar(45) NOT NULL,
  fullname varchar(10) DEFAULT NULL,
  account varchar(20) NOT NULL,
  password varchar(45) DEFAULT NULL,
  mobile varchar(11) NOT NULL,
  email varchar(45) NOT NULL,
  gender int(1) DEFAULT '0',
  description varchar(200) DEFAULT NULL,
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  update_time datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY account_UNIQUE (account),
  UNIQUE KEY mobile_UNIQUE (mobile),
  UNIQUE KEY email_UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


2. Initialize data.

insert into users(id,fullname,account,password,mobile,email,gender) values(uuid(),'Administrator','admin',md5('12345678'), '13211111111','admin@penguin.org',1),(uuid(),'Tester 1','tester1',md5('12345678'), '13211111112','tester1@penguin.org',0)

3. test query and mutation functions using iql

http://localhost:8080/graphiql

a. get all users.

{
  listUsers {
    id
    fullname
    account
    gender
    createTime
  }
}

b. query user by unique account

{
  getUser(account:"admin") {
    id
    fullname
    gender
    createTime
  }
}

c. Create a new user.

mutation {
  createUser(input: {fullname: "Employee 10", account: "employee10", email: "employee10@penguin.org", mobile: "13211111114",}) {
    id
    fullname
  }
}

If you don't test them using iql, please call hql API.

post http://localhost:8080/graphql

{"query":"query {getUser(account:\"admin\") {id fullname gender createTime}}","variables":null}

{"query":"mutation {createUser(fullname: \"Employee 11\", account: \"employee11\", email: \"employee11@penguin.org\", mobile: \"13211111115\") {id fullname}}","variables":null}

After clone the codes, and modify the database root's password, then you can run it. 

cd graphqlServer

mvn spring-boot:run



**Websocket relative terms**

**STOMP** stands for Simple Text Oriented Messaging Protocol. It is a messaging protocol that defines the format and rules for data exchange.
Why do we need STOMP? Well, WebSocket is just a communication protocol. It doesn’t define things like - How to send a message only to users who are subscribed to a particular topic, or how to send a message to a particular user. We need STOMP for these functionalities.

**SockJS** is a browser JavaScript library that provides a WebSocket-like object. SockJS gives you a coherent, cross-browser, Javascript API which creates a low latency, full duplex, cross-domain communication channel between the browser and the web server.
Under the hood, SockJS tries to use native WebSockets first. If that fails it can use a variety of browser-specific transport protocols and presents them through WebSocket-like abstractions.

**Spring WebSocket** is the Spring module that enables WebSocket-style messaging support. As Spring WebSocket’s documentation states, the WebSocket protocol defines an important new capability for web applications: full-duplex, two-way communication between client and server.

**Notification**
https://jira.spring.io/browse/DATAREST-933. It can't run with spring web(webmvc).

**Redis**
 brew services start redis
 redis-server /usr/local/etc/redis.conf
/usr/local/Cellar/redis/5.0.7

# set access password
redis-cli

CONFIG SET requirepass "redissuperman"

Setting up the Redis Cache on your machine
Writing a Spring Boot Application
Use Spring’s Integrated @Cacheable Annotation to cache results of method invocations using Spring Data Redis
Gain more fine granular control by using the other available Annotations
Create dynamic CacheKeys: Cache depending on the input parameters of our methods
Define after what time (TTL=time-to-live) our cached Entries are not valid anymore
Define different TTLs
