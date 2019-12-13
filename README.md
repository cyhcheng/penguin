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

b. query user by account
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
