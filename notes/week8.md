If we want redirecting to login page, we should add 'formlogin' to security config, otherwise, get method is forbidden and controller returns an empty page.  

Debugging commands:
* Testing login: `curl -X POST localhost:6790/authenticate/login -H "Content-Type: application/json" -d '{"id": <user_id>, "password": <password>}'`
* Testing different authorities of users: `curl -X GET localhost:6790/products/help -H "Authorization: Bearer <token>"`
* Testing different authorities of users: `curl -X POST localhost:6790/products -H "Authorization: Bearer <token>" -H "Content-Type: application/json" -d '{"id": 1, "name": "add"}'`
* Testing different authorities of users: `curl -X DELETE localhost:6790/products/1 -H "Authorization: Bearer <token>"`
