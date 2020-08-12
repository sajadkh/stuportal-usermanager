#UserManager
This module is a user manager service written by Spring(Java).

##List of Features
* Manage users
* Manage user's role
* Manage access to resources

##Dependencies
* JDK 1.8
* Mysql
* Maven

##Installation
1.Clone project with git tool or download and extract it:
```shell script
git clone https://github.com/sajadkh/stuportal-usermanager.git
```
2.Move to project folder and install dependencies with this command:
```shell script
mvn install
```
3.For configure project open ```application.properties``` file and edit config parameters.       
These parameters are:
```text
spring.jpa.hibernate.ddl-auto -> "Database table management"
spring.datasource.url -> "The database url"
spring.datasource.username -> "The database connection username"
spring.datasource.password -> "The database connection password"
jwt.secret -> "JWT token secret"
jwt.jwtTokenValidity -> "JWT expiration time"
stuPortal.sysAdminRole -> "System admin user"
```
4.Run project with this command:
```shell script
./mvnw spring-boot:run
```

##Http Endpoints
### SignUp
```text
Url: http://{{ServerIP}}/signUp
Method: POST
Body: 
{
    "firstName": "String",
   	"lastName": "String",
   	"fatherName": "String",
   	"idNumber": "String",
   	"email": "emailFormat",
   	"phoneNumber": "phoneFormat",
   	"role": "String"
}
Response: 
{
    "status": "String",
    "timestamp": "TimeFormat",
    "password": "String",
    "userId": "String"
}
```

###Login
```text
Url: http://{{ServerIP}}/login
Method: PUT
Body: 
{
    "userId": "String",
  	"password": "String"
}
Response: 
{
    "status": "String",
    "timestamp": "TimeFormat",
    "token": "String"
}
```

###VerifyToken
```text
Url: http://{{ServerIP}}/verifyToken
Method: POST
Body: 
{
    "userId": "String"
}
Response: 
{
    "status": "String",
    "timestamp": "TimeFormat",
    "userId": "String",
    "role": "String",
    "tokenStatus": "String"
}
```

###UsersList
```text
Url: http://{{ServerIP}}/user
Method: GET
Param: 
{
    "userId": "String"//Optional
}
Headers:
{
    "Authorization": "String"
}
Response: 
{
    "status": "String",
    "timestamp": "TimeFormat",
    "userId": "String",
    "role": "String",
    "tokenStatus": "String"
}
```

###ChangePassword
```text
Url: http://{{ServerIP}}/changePassword
Method: PUT
Body: 
{
    "previousPassword": "String",
    "newPassword": "String"
}
Headers:
{
    "Authorization": "String"
}
Response: 
{
    "status": "String",
    "timestamp": "TimeFormat"
}
```

###HasAccess
```text
Url: http://{{ServerIP}}/hasAccess
Method: GET
Param: 
{
    "resourceName": "String"
}
Headers:
{
    "Authorization": "String"
}
Response: 
{
    "status": "String",
    "timestamp": "TimeFormat"
}
```

###DeleteUser
```text
Url: http://{{ServerIP}}/user
Method: DELETE
Body: 
{}
Headers:
{
    "Authorization": "String"
}
Response: 
{
    "status": "String",
    "timestamp": "TimeFormat"
}
```

###ForgetPassword
```text
Url: http://{{ServerIP}}/forgerPassword
Method: PUT
Body: 
{
    "userId": "String"
}
Response: 
{
    "status": "String",
    "timestamp": "TimeFormat"
    "password": "String"
}
```

###AddRole
```text
Url: http://{{ServerIP}}/role
Method: POST
Body: 
{
    "name": "String"
}
Response: 
{
    "status": "String",
    "timestamp": "TimeFormat"
}
```

###GetRole
```text
Url: http://{{ServerIP}}/role
Method: GET
param: 
{
    "name": "String"//Optional
}
Response: 
[
    {
        "name": "String",
        "resources": [
                        {
                            "name": "String"
                        }
                     ]
    }
]
```

###DeleteRole
```text
Url: http://{{ServerIP}}/role
Method: DELETE
Body: 
{
    "name": "String"
}
Response: 
{
    "status": "String",
    "timestamp": "TimeFormat"
}
```

###AddResource
```text
Url: http://{{ServerIP}}/resource
Method: POST
Body: 
{
    "name": "String"
}
Response: 
{
    "status": "String",
    "timestamp": "TimeFormat"
}
```

###GetResource
```text
Url: http://{{ServerIP}}/resource
Method: GET
param: 
{
    "name": "String"//Optional
}
Response: 
[
    {
        "name": "String"
    }
]
```

###DeleteResource
```text
Url: http://{{ServerIP}}/resource
Method: DELETE
Body: 
{
    "name": "String"
}
Response: 
{
    "status": "String",
    "timestamp": "TimeFormat"
}
```

###AddAccess
```text
Url: http://{{ServerIP}}/access
Method: POST
Body: 
{
    "roleName": "String",
    "resourceName": "String"
}
Response: 
{
    "status": "String",
    "timestamp": "TimeFormat"
}
```

###DeleteAccess
```text
Url: http://{{ServerIP}}/access
Method: DELETE
Body: 
{
    "roleName": "String",
    "resourceName": "String"
}
Response: 
{
    "status": "String",
    "timestamp": "TimeFormat"
}
```