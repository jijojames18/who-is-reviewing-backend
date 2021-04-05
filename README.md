# who-is-reviewing-backend
Microservice to store details of users who are reviewing a pull request  

## Description  
This microservice holds the list of users who are currently reviewing a pull request. The application frontend, which is a JavaScript browser extension, makes REST api calls to this microservice. The microservice stores and retrieves the data from a redis instance. The service exposes three REST endpoints, i) to fetch the list of users reviewing a pull request, ii) to add/remove a user from the reviewer list, and iii) to remove the pull request details once it is either merged/closed.

## Redis
The service uses Redis for data persistence. Usernames of users who are currently reviewing a given pull request are stored as a list in a redis key with format
`<project_name>/<pull_request_id>`.

## Environment Variables
The service requires the redis login credentials to be present as an environment variable.

Variable | Description |
------|-------------|
REDIS_URL | Redis url of format `redis://<username>:<password>@<host>:<port>` |

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

Add/remove a user as a reviewer

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
  curl --location --request POST 'localhost:8080/<project_id>/<pull_request_id>' --header 'Content-Type: application/json' --data-raw '{
    "userId": jijojames18,
    "status": "STARTED",
  }'
  ```

### Delete pull request details

Deletes the list of reviewers of pull request.  

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
  curl --location --request DELETE 'localhost:8080/who-is-reviewing/1'
  ```

### Tech stack
* Java
* Spring Boot
* Redis


### License
[MIT](https://github.com/jijojames18/who-is-reviewing-backend/blob/master/LICENSE)
