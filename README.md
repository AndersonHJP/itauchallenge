ITAÚ CHALLENGE


Descrição
Este projeto é um CRUD (Create, Read, Update, Delete) desenvolvido em Java com Spring Boot. Ele permite gerenciar usuários, salvando, listando, atualizando e deletando registros no banco de dados. Cada usuário possui os seguintes campos:

firstname (nome)

lastname (sobrenome)

participation (participação)

O objetivo principal é demonstrar a implementação de operações básicas de banco de dados em uma aplicação Spring Boot, utilizando boas práticas de desenvolvimento.

Tecnologias
Java 21

Spring Boot

Maven (gerenciador de dependências)

MySQL (banco de dados principal)

H2 Database (banco de dados em memória para testes unitários)

Docker (para containerização)

Swagger (documentação da API)

Postman ou similar (para testar as requisições)

Pré-requisitos
Para rodar o projeto, você precisará dos seguintes itens:

Java 21 instalado.

Docker e Docker Compose instalados (opcional, para rodar o projeto em containers).

MySQL Workbench ou DBeaver para visualizar as tabelas no banco de dados.

Postman ou qualquer outra ferramenta para testar as requisições HTTP.

Como Configurar o Projeto
Sem Docker (Localmente)
Clone o repositório:

![image](https://github.com/user-attachments/assets/1eced52b-039c-42d2-938e-4a4647a4de62)

Navegue até o diretório do projeto:

![image](https://github.com/user-attachments/assets/d44f1fc0-d92a-4eb8-a566-6ff91274e1ba)

Configure o banco de dados MySQL:

Crie um banco de dados chamado clients.

Configure as credenciais no arquivo src/main/resources/application.properties:
![image](https://github.com/user-attachments/assets/35ed8630-fb0d-4e02-b23f-d4211ee8e2c6)

Acessando a Aplicação
Swagger UI: Documentação interativa da API em http://localhost:8080/swagger-ui/index.html.

Listar usuários: Visualize todos os usuários cadastrados em http://localhost:8080/clients/listAll.

Testes
O projeto inclui testes unitários e de integração para os repositórios (repository) e serviços (service), validando tanto os casos de sucesso quanto os casos de erro dos endpoints. Para executar os testes, utilize o seguinte comando:
![image](https://github.com/user-attachments/assets/1a087229-3b06-4fcd-936f-eb168282d1e8)

Os testes utilizam o banco de dados em memória H2 para garantir a integridade do código.

Exemplos de Casos Testados:
Criação de usuário: Verifica se um usuário é salvo corretamente no banco de dados.

Atualização de usuário: Valida se os dados do usuário são atualizados conforme esperado.

Exclusão de usuário: Confirma se um usuário é removido do banco de dados.

Listagem de usuários: Verifica se a listagem retorna todos os usuários cadastrados.

Casos de erro: Testa cenários como tentativa de salvar um usuário com dados inválidos ou buscar um usuário que não existe.

API Documentation
A documentação da API está disponível via Swagger. Acesse:
http://localhost:8080/swagger-ui/index.html#/

Aqui você encontrará detalhes sobre todos os endpoints disponíveis, como:

Criar usuário (POST /clients)

Listar usuários (GET /clients/listAll)

Atualizar usuário (PUT /clients/{id})

Deletar usuário (DELETE /clients/{id})
