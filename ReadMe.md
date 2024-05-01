Rest Api

Get users by date range: 
    method - GET
    url - http://localhost:8080/users
    parameters - from(yyyy-mm-dd), to(yyyy-mm-dd)

Create user:
    method - POST
    url - http://localhost:8080/users
    body - {
        "id": long,
        "name": string,
        "lastName": string,
        "birthday": date(yyyy-mm-dd),
        "address": string,
        "phoneNumber": string
        }

Update user:
    method - PUT
    url - http://localhost:8080/users
    body - {
        "id": long,
        "name": string,
        "lastName": string,
        "birthday": date(yyyy-mm-dd),
        "address": string,
        "phoneNumber": string
        }

Delete user:
    method - DELETE
    url - http://localhost:8080/users/{userId}