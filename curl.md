##### Autenticação via app:

```sh
curl -v -k -X POST https://localhost:9000/oauth/token -d "grant_type=client_credentials&scope=read%20write&client_id=mobile-73hZFgSJ2Gdc48BJBkuWhe0nPw8TdZYdP8HeCv2bUR0y8l2Wy6&client_secret=cCTemqOcYFUy2NOqb265vS9l8RRiFjtAkKiqoUkqeARWBPjm"
```

##### Autenticação via app com o login do usuário:

```sh
curl -v -k -X POST https://localhost:9000/oauth/token -d "grant_type=password&scope=read%20write&username=<usuario>&password=<senha_do_usuario>&client_id=mobile-73hZFgSJ2Gdc48BJBkuWhe0nPw8TdZYdP8HeCv2bUR0y8l2Wy6&client_secret=cCTemqOcYFUy2NOqb265vS9l8RRiFjtAkKiqoUkqeARWBPjm"
```

##### Verificar se token é válido:

```sh
curl -v -k -X POST https://localhost:9000/oauth/check_token -d "token=<access_token>&client_id=mobile-73hZFgSJ2Gdc48BJBkuWhe0nPw8TdZYdP8HeCv2bUR0y8l2Wy6&client_secret=cCTemqOcYFUy2NOqb265vS9l8RRiFjtAkKiqoUkqeARWBPjm"
```

##### Renovação de autenticação com token refresh:

```sh
curl -v -k -X POST https://localhost:9000/oauth/token -d "grant_type=refresh_token&refresh_token=<refresh_token>&client_id=mobile-73hZFgSJ2Gdc48BJBkuWhe0nPw8TdZYdP8HeCv2bUR0y8l2Wy6&client_secret=cCTemqOcYFUy2NOqb265vS9l8RRiFjtAkKiqoUkqeARWBPjm"
```

##### Adiciona um novo taxista:

```sh
curl -vk https://localhost:9000/api/v1/drivers -d '{"name": "waldinar", "carPlate": "ccc-1000"}' -H "Content-Type: application/json" -H "Authorization: Bearer <access_token>"
```

##### Busca status do taxista:

```sh
curl -vk -X GET https://localhost:9000/api/v1/drivers/4/status -H "Content-Type: application/json" -H "Authorization: Bearer <access_token>"
```

##### Atualiza status:

```sh
curl -vk -X POST https://localhost:9000/api/v1/drivers/3/status -d '{"latitude":-23.60810717,"longitude":-46.67500346,"driverId":1,"driverAvailable":true}' -H "Content-Type: application/json" -H "Authorization: Bearer <access_token>"
```

##### Busca por taxistas na area:

```sh
curl -vk -X GET "https://localhost:9000/api/v1/drivers/inArea?sw=-23.612474,-46.702746&ne=-23.589548,-46.673392" -H "Content-Type: application/json" -H "Authorization: Bearer <access_token>"
```
