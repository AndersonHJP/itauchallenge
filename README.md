## Índice
1. [Descrição](#descrição)
2. [Tecnologias](#tecnologias)
3. [Pré-requisitos](#pré-requisitos)
4. [Como Configurar o Projeto](#como-configurar-o-projeto)
5. [Acessando a Aplicação](#acessando-a-aplicação)
6. [Autenticação e Autorização](#autenticação-e-autorização)
7. [Migrações de Banco com Flyway](#migrações-de-banco-com-flyway)
8. [Testes](#testes)
9. [API Documentation](#api-documentation)

## Descrição
Este projeto é um CRUD (Create, Read, Update, Delete) desenvolvido em Java com Spring Boot. Ele permite gerenciar usuários, salvando, listando, atualizando e deletando registros no banco de dados com controle de acesso baseado em roles. Cada usuário possui os seguintes campos:

- *firstname* (nome)
- *lastname* (sobrenome)
- *participation* (participação)

O sistema implementa autenticação JWT e define dois perfis de acesso:
- **ADMIN**: Acesso completo a todas operações (CRUD)
- **USER**: Apenas permissão para listar usuários (operação READ)

## Tecnologias
- *Java 21*
- *Spring Boot*
- *Spring Security*
- *JWT (JSON Web Tokens)*
- *Flyway* (para migrações de banco de dados)
- *Maven* (gerenciador de dependências)
- *MySQL* (banco de dados principal)
- *H2 Database* (banco de dados em memória para testes unitários)
- *Docker* (para containerização)
- *Swagger* (documentação da API)
- *Postman ou similar* (para testar as requisições)

## Pré-requisitos
Para rodar o projeto, você precisará dos seguintes itens:
- Java 21 instalado
- Docker e Docker Compose instalados (opcional, para rodar o projeto em containers)
- MySQL Workbench ou DBeaver para visualizar as tabelas no banco de dados
- Postman ou qualquer outra ferramenta para testar as requisições HTTP

## Como Configurar o Projeto
### Sem Docker (Localmente)
Clone o repositório:

![image](https://github.com/user-attachments/assets/1eced52b-039c-42d2-938e-4a4647a4de62)

Navegue até o diretório do projeto:

![image](https://github.com/user-attachments/assets/d44f1fc0-d92a-4eb8-a566-6ff91274e1ba)

Configure o banco de dados MySQL:

Crie um banco de dados chamado clients.

Configure as credenciais no arquivo `src/main/resources/application.properties`:
![image](https://github.com/user-attachments/assets/c03f9192-ad54-43cb-b701-5d11a7851ca0)
)

O Flyway irá automaticamente criar as tabelas necessárias e popular os dados iniciais (incluindo usuários admin/user) ao iniciar a aplicação.

## Acessando a Aplicação
- *Swagger UI*: Documentação interativa da API em [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- *Listar usuários*: Visualize todos os usuários cadastrados em [http://localhost:8080/clients/listAll](http://localhost:8080/clients/listAll) (requer autenticação)

## Autenticação e Autorização
O sistema utiliza JWT para autenticação. Para acessar os endpoints, primeiro obtenha um token:

1. **Login** (POST /auth/login):
   ```json
   {
     "username": "admin@example.com",
     "password": "admin123"
   }
   ```
   Ou para usuário comum:
   ```json
   {
     "username": "user@example.com",
     "password": "user123"
   }
   ```

2. Use o token retornado no header das requisições subsequentes:
   ```
   Authorization: Bearer <seu-token-jwt>
   ```

**Contas padrão criadas pelo Flyway:**
- ADMIN: admin@example.com / admin123
- USER: user@example.com / user123

## Migrações de Banco com Flyway
O projeto utiliza Flyway para gerenciar migrações de banco de dados. Os scripts SQL estão localizados em `src/main/resources/db/migration`.

- `V1__Initial_schema.sql`: Cria as tabelas básicas
- `V2__Insert_roles.sql`: Insere as roles (ADMIN, USER)
- `V3__Insert_default_users.sql`: Insere usuários padrão com senhas criptografadas

Ao iniciar a aplicação, o Flyway verificará e aplicará automaticamente quaisquer migrações pendentes.

## Testes
### Exemplos de Casos Testados:
- *Autenticação*: Testes de login válido/inválido
- *Autorização*: Verificação de acesso a endpoints baseado em roles
- *Criação de usuário*: Verifica se um usuário é salvo corretamente no banco de dados
- *Atualização de usuário*: Valida se os dados do usuário são atualizados conforme esperado
- *Exclusão de usuário*: Confirma se um usuário é removido do banco de dados
- *Listagem de usuários*: Verifica se a listagem retorna todos os usuários cadastrados
- *Casos de erro*: Testa cenários como tentativa de salvar um usuário com dados inválidos ou buscar um usuário que não existe

## API Documentation
A documentação da API está disponível via Swagger. Acesse:
[http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

**Endpoints protegidos por autenticação:**
- *Autenticação*:
  - POST /auth/login - Gera token JWT

- *Usuários* (requer token JWT):
  - POST /clients - Cria usuário (apenas ADMIN)
  - GET /clients/listAll - Lista todos usuários (ADMIN e USER)
  - PUT /clients/{id} - Atualiza usuário (apenas ADMIN)
  - DELETE /clients/{id} - Deleta usuário (apenas ADMIN)


## Autenticação e Autorização
O sistema utiliza JWT para autenticação. Para acessar os endpoints, primeiro obtenha um token:

1. **Registro** (POST /auth/register - Aberto sem autenticação):
   ```json
   {
     "login": "seu.usuario",
     "password": "suaSenha123",
     "role": "ADMIN" ou "USER"
   }
   ```
   Exemplo:
   ```json
   {
     "login": "anderson.pires",
     "password": "123456789",
     "role": "ADMIN"
   }
   ```

2. **Login** (POST /auth/login):
   ```json
   {
     "login": "anderson.pires",
     "password": "123456789"
   }
   ```

3. Use o token retornado no header das requisições subsequentes:
   ```
   Authorization: Bearer <seu-token-jwt>
   ```

**Contas padrão criadas pelo Flyway:**
- ADMIN: admin@example.com / admin123
- USER: user@example.com / user123

**Hierarquia de Roles:**
- `ADMIN`: Pode registrar novos usuários, criar, ler, atualizar e deletar clients
- `USER`: Apenas leitura (listAll)

## API Documentation
**Endpoints de Autenticação:**
- POST /auth/register - Registra novo usuário (aberto)
  ```json
  {
    "login": "string",
    "password": "string",
    "role": "ADMIN" ou "USER"
  }
  ```

- POST /auth/login - Gera token JWT
  ```json
  {
    "login": "string",
    "password": "string"
  }
  ```

**Exemplo completo de fluxo:**

1. Registrar novo ADMIN:
```http
POST /auth/register HTTP/1.1
Content-Type: application/json

{
  "login": "novo.admin",
  "password": "senhaForte123",
  "role": "ADMIN"
}
```

2. Login:
```http
POST /auth/login HTTP/1.1
Content-Type: application/json

{
  "login": "novo.admin",
  "password": "senhaForte123"
}
```

3. Acessar endpoint protegido:
```http
GET /clients/listAll HTTP/1.1
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...


**Validações:**
- Password deve ter pelo menos 8 caracteres
- Role deve ser exatamente "ADMIN" ou "USER"
- Login deve ser único
```
