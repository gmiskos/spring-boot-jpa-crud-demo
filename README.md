#java version
java corretto 11.0.13

# DATABASE CONNECTION H2 STEPS
    1. Comment out maven depenendency for flyway in pom file.
    2. activate h2 profile by selecting h2 in application.properties file   
        - spring.profiles.active=h2 
    4. flyway.enabled=false
    5. set your prefered port server.port=8080
    
# DATABASE FOR CONNECTION FOR MARIA DB
    1. Uncomment maven dependency for flyway in pom file.
    2. select mariadb profile in application.properties file
        - spring.profiles.active=mariadb
    3. flyway.enabled=true
    4. create mariadb schema with name: sdguiweb
    5. set your prefered port server.port=8080

#Requirements

1A) GET http://localhost:8080/api/beers?page=0&size=10&sorted=true

1B) POST http://localhost:8080/api/beers

body:
{
    "name":"amstel",
    "type":"lager",
    "rate":3
}

1C) DELETE http://localhost:8080/api/beers/1

1D) PUT http://localhost:8080/api/beers/1/5

1E) POST http://localhost:8080/api/beers/search

body:

every property is optional

{
    "name":"heineken", 
    "type":"pilsner",  
    "rateFrom":1,
    "rateTo":5,
    "page":0,
    "size":10
}
