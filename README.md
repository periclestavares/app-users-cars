# app-users-cars
Aplicação de cadastro de Usuários e Carros

### Histórias de Usuário
1. Create User API 
- Create User Entity, DTO and Repository
- Create User Service
- Create User Controller
2. Implement Spring Security with JWTToken
3. Create Sign API
- Create SignInController
- Create MeController
4. Create User API 
- Create Car Entity, DTO and Repository
- Create Car Service
- Create Car Controller
5. Create Header
- Create HeaderComponent
6. Create UserCrud
- Create ListUserComponent
- Create CreateUserComponent
- Create deleteUser
- Create UpdateUserComponent
7. Create AuthenticationValidation
- Create AuthenticationComponent
8. Create CarCrud
- Create ListCarComponent
- Create CreateCarComponent
- Create deleteCar
- Create UpdateCarComponent
9. Add Build

### Solução

Foi implementado uma aplicação com spring boot API utilizando Spring Boot (utilizando Spring security e JWT Token(para autenticação), H2 como banco de dados(tanto para aplicação como para os testes)) e com Angular 14 para a interface (utilizando componentes, rotas, serviços, intercepetadores).

### Execução e build
#### Backend
Para execução local do backend, é necessário executar a classe ApiApplication.
Para execução de teste executar o comando mvn test dentro do diretorio backend.

#### Frontend
Para execução local do front, é necessário executar o comando ng serve dentro da pasta frontend.
#### Build
Para criar um jar da aplicação, execute o comando mvn install dentro do backend, e executar o jar gerado dentro \backend\target como uma aplicação java.
(Caso queira executar o jar dentro da sua máquina, favor modificar o arquivo environments/environment.prod.ts dentro do front para apontar para a url local).



