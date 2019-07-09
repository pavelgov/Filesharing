# If you want to  see all controllers go to http://localhost:8080/swagger-ui.html

1.Create User  - Post("http://localhost:8080/register"), response:"status" - OK or Eror and "description" - email or description of error.
    Body Json example { "email": "1@1.ru", "password": "1", "roles":["USER"]}

2.Upload File - Post  ("http://localhost:8080/api/file"). Header param: "userId" - UUID (receive after registration).
    response:"status" and "description" - fileId.

3.Share file for other users - Post("/api/share") Params: "name" - String  ,"file" - MultipartFile ,Header name = "userId" String (UUID)
    Body Json example  {"fileId":"89e84a13-9ac4-471f-be0e-0e8226351bf2", "email":"1@1.ru"}
    response:"status" and "description" - fileId.

4. Info about owners and shared.  Get("http://localhost:8080/api/file") Header param: "userId" - UUID (receive after registration)
response arrays owners and shared

5. Download file Get("/api/file/{id}") {id}- fileId (UUID), Header param: "userId" - UUID (receive after registration)

uploads.path - set name folder for upload files