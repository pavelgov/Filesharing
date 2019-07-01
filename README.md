# If you want to  see all controllers go to http://localhost:8080/swagger-ui.html

1.Post ("http://localhost:8080/api/file")

2.Share file for other users @Post("/api/share") Params: "name" - String  ,"file" - MultipartFile ,Header name = "userId" String (UUID)

3.Create User Post("http://localhost:8080/register"), response:status and description - userId.
 Body Json example {  "email": "asm@ail.ru", "password": "qwerty", "roles":[{  "role" :"ADMIN" }]}

4. Info about owners and shared.  Get("http://localhost:8080/api/file") response arrays owners and shared

5. Download file Get("/api/file/{id}") {id}- fileId (UUID)

uploads.path - set name folder for upload files