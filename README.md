# restapiexample
Simple RESTful api  
Model: customer, partnerMapping. Realtion one customer to many partnerMappings  
Database: embedded H2  
Security: customer can works only with own data, auth token should be added to request. Format: Authorization header: Beare <customerId>. There is role admin with full access: Bearer admin  

api:  

GET /restapiexample/customers/ - all customers  

GET /restapiexample/customers/{id} - customer id  

GET /restapiexample/customers/me - current logged ing customer  

GET /restapiexample/customers/{customerId}/partnerMappings - all partnerMappings for customerId 

GET /restapiexample/customers/{customerId}/partnerMappings/{id} - retrieve partner mapping for customer  

POST /restapiexample/customers/{customerId}/partnerMappings - create partner mapping for customer  
the new partner mapping in Request Body  
example of RequestBody:  
{
            "partnerId": 1,
            "accountId": 6,
            "name": "Ivan",
            "surname": "Petrovich",
            "patronymic": "Ivanovich",
            "avatar": null
        }

PUT /restapiexample/customers/{customerId}/partnerMappings/{id} - update partner mapping for customer  
the new partner mapping in Request Body  
example of RequestBody:  
{
            "partnerId": 1,
            "accountId": 6,
            "name": "Ivan",
            "surname": "Petrovich",
            "patronymic": "Ivanovich",
            "avatar": null
        }  

DELETE /restapiexample/customers/{customerId}/partnerMappings/{id} - delete partner mapping for customer  




