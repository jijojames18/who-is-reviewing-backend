# Who is reviewing
Microservice to store details of users who are reviewing a pull request  

## Description  
This microservice holds the list of users who are currently reviewing a pull request. The application frontend, which is a JavaScript browser extension, makes REST api calls to this microservice. The microservice updates and retrieves the data stored in a redis instance. The service exposes three REST endpoints, i) to fetch the list of users reviewing a given pull request, ii) add/remove a user from the reviewer list, and iii) remove the pull request details once it is either merged/closed.

## Redis
The service uses Redis for data persistence. Usernames of users who are currently reviewing a given pull request are stored as a set in a redis key with format
`<project_name>/<pull_request_id>`.

## Environments  
The application can be run in 4 environments, namely `dev`, `docker.local`, and `docker.prod`.  
1. `dev`- Local development environment.  
2. `docker.local` - Docker environment for development. Docker file [`who-is-reviewing-dev`](https://hub.docker.com/r/jijojames18/who-is-reviewing-dev) can be used for development. A sample [docker compose](https://github.com/jijojames18/who-is-reviewing-backend/blob/main/docker-compose.yml) file is also present in the repository.
3. `docker.prod` - Docker for production. Docker file [`who-is-reviewing`](https://hub.docker.com/r/jijojames18/who-is-reviewing), available at Docker Hub can be used for production deployment. A sample [docker compose](https://github.com/jijojames18/who-is-reviewing-backend/blob/main/docker-compose.prod.yml) file is also present in the repository.  

The properties file for each environment is located inside the `resources` folder.  
*For every successful push to `main` branch, the dev and prod docker images will be updated with the new changes.*   

## Environment Variables  

Variable | Description |
------|-------------|
REDIS_URL | Redis url of format `redis://<username>:<password>@<host>:<port>` |

## Docker Secrets
While deploying docker prod images, make sure the redis password is mounted as a secret inside the container. The filename for the password can be configured inside the properties file for `docker.prod`.  

## API
### Get list of users

Retrieve the list of users who are currently reviewing the pull request.  

* **URL**

  /<project_name>/<pull_request_id>

* **Method:**

  `GET`
  
* **URL Params**

  **Required:**
 
   `project_name=[string]`  
   `pull_request_id=[string]`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** List of users

* **Sample Call:**

  ```
  curl --location --request GET 'localhost:8080/who-is-reviewing/1'
  ```

### Set/unset user as reviewer

Add/remove a user as a reviewer for a pull request.  

* **URL**

  /<project_name>/<pull_request_id>

* **Method:**

  `POST`

* **URL Params**

  **Required:**
 
   `project_name=[string]`  
   `pull_request_id=[string]`

* **Data Params**

  **Required:**
 
   `userId=[string]`  
   `status=[enum] STARTED|STOPPED`  

* **Success Response:**

  * **Code:** 200 <br />
 
* **Error Response:**
  * **Code:** 400 Bad Request <br />
    **Content:** `{ error-code: 400, "error-message" : "Invalid format exception" }`

* **Sample Call:**

  ```
  curl --location --request POST 'localhost:8080/who-is-reviewing/1' --header 'Content-Type: application/json' --data-raw '{
    "userId": jijojames18,
    "status": "STARTED",
  }'
  ```

### Delete pull request details

Deletes all details of pull request.    

* **URL**

  /<project_name>/<pull_request_id>

* **Method:**

  `GET`
  
* **URL Params**

  **Required:**
 
   `project_name=[string]`  
   `pull_request_id=[string]`

* **Success Response:**

  * **Code:** 200 <br />

* **Sample Call:**

  ```
  curl --location --request DELETE 'localhost:8080/who-is-reviewing/1'
  ```

### Tech stack
* Java
* Spring Boot
* Redis

### Frontend
Repository : [who-is-reviewing-frontend](https://github.com/jijojames18/who-is-reviewing-frontend)

### Docker Images
[Dev](https://hub.docker.com/r/jijojames18/who-is-reviewing-dev)  
[Prod](https://hub.docker.com/r/jijojames18/who-is-reviewing)

### License
[MIT](https://github.com/jijojames18/who-is-reviewing-backend/blob/master/LICENSE)
