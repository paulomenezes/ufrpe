**Login AVA (Serviços Integrados)**
----
  Chamadas necessárias para autenticar usuários utilizando a API do AVA (senha de serviços integrados)

### Passo 01
  Realizar login para conseguir o Token, esse token deve ser utilizado em todas as requisições.

* **URL**

  `http://ava.ufrpe.br/login/token.php`

* **Method:**

  `POST`
  
*  **Body Params**  
  
  Usuário e senha do AVA.  
   
   `username=[string]`  
   `password=[string]`  
   `service=moodle_mobile_app`  

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{ "token": "a0569fef4f42051225e58d9a3a7fc66c" }`
 
* **Sample Call:**

  ```javascript
    $.ajax({
      type : "POST",
      url: "http://ava.ufrpe.br/login/token.php",
      data: {
        'username': 'valid.username',
        'password': 'valid.password',
        'service': 'moodle_mobile_app'
      },
      success : function(response) {
        // ...
      }
    });
  ```

* **Sample Response:**

  ```javascript
  {
    token: "a0569fef4f42051225e58d9a3a7fc66c"
  }
 
### Passo 02
  Requisição para descobrir dados do usuário e da API.

* **URL**

  `http://ava.ufrpe.br/webservice/rest/server.php?moodlewsrestformat=json`

* **Method:**

  `POST`
  
*  **Body Params**  
  
   `wsfunction=core_webservice_get_site_info`  
   `wstoken=[string]`  // Token da requisição anterior

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
    ```javascript
	"sitename": "Ambiente Virtual de Aprendizagem",
	"username": "paulo.gmenezes",
	"firstname": "PAULO",
	"lastname": "HENRIQUE GUIMARAES DE MENEZES",
	"fullname": "PAULO HENRIQUE GUIMARAES DE MENEZES",
	"lang": "pt_br",
	"userid": 360,
	"siteurl": "http:\/\/ava.ufrpe.br",
	"userpictureurl": "http:\/\/ava.ufrpe.br\/pluginfile.php?file=%2F59755%2Fuser%2Ficon%2Ff1",
	"functions": [...] ... } 

 
* **Sample Call:**

    ```javascript
    $.ajax({
      type : "POST",
      url: "http://ava.ufrpe.br/webservice/rest/server.php?moodlewsrestformat=json",
      data: {
        'wsfunction': 'core_webservice_get_site_info',
        'wstoken': 'a0569fef4f42051225e58d9a3a7fc66c'
      },
      success : function(response) {
        // ...
      }
    });
    ```
  
### Passo 03 (Opcional)
  Requisição para descobrir todos os dados do usuário (Apenas necessário se você deseja alguma informação que não contém na requisição anterior).

* **URL**

  `http://ava.ufrpe.br/webservice/rest/server.php?moodlewsrestformat=json`

* **Method:**

  `POST`
  
*  **Body Params**  
     
   `wsfunction=core_user_get_users_by_id`  
   `wstoken=[string]`  // Token da requisição anterior  
   `userids[]=[int]`  // Array de ID de usuários (obtido na requisição anterior)

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
    ```javascript
	{
		"id": 360,
		"username": "paulo.gmenezes",
		"fullname": "PAULO HENRIQUE GUIMARAES DE MENEZES",
		"email": "paulo.hgmenezes@gmail.com",
		"department": "BACHARELADO EM CIÊNCIA DA COMPUTAÇÃO",
		"institution": "UNIVERSIDADE FEDEREAL RURAL DE PERNAMBUCO",
		"idnumber": "10271524499",
		"firstaccess": 1439947704,
		"lastaccess": 1501199725,
		"auth": "db",
		"confirmed": 1,
		"lang": "pt_br",
		"theme": "",
		"timezone": "99",
		"mailformat": 1,
		"description": "",
		"descriptionformat": 1,
		"city": "JABOATAO DOS GUARARAPES",
		"country": "BR",
		"profileimageurlsmall": "http:\/\/ava.ufrpe.br\/pluginfile.php?file=%2F59755%2Fuser%2Ficon%2Ff2",
		"profileimageurl": "http:\/\/ava.ufrpe.br\/pluginfile.php?file=%2F59755%2Fuser%2Ficon%2Ff1",
    ... }
 
* **Sample Call:**

    ```javascript
    $.ajax({
      type : "POST",
      url: "http://ava.ufrpe.br/webservice/rest/server.php?moodlewsrestformat=json",
      data: {
        'wsfunction': 'core_user_get_users_by_id',
        'wstoken': 'a0569fef4f42051225e58d9a3a7fc66c',
        'userids[0]': 360
      },
      success : function(response) {
        // ...
      }
    });
    ```
