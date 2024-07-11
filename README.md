# E-commerce: Uma Solução Robusta e Escalável com Spring Boot e Gradle

Este projeto é uma solução de e-commerce desenvolvida com foco em robustez e escalabilidade. Utilizando a arquitetura limpa, Spring Boot e Gradle, a aplicação oferece uma série de funcionalidades essenciais para um e-commerce, além de garantir a qualidade do código por meio de testes unitários e de integração.

## Configurações do Projeto

O projeto utiliza vários perfis de execução do Spring Boot, cada um com suas próprias configurações:

### 1. application.yml

Este é o perfil padrão e inclui configurações para o servidor, banco de dados e JPA. O servidor está configurado para rodar na porta 8080 e utiliza o Undertow com 64 threads de trabalho e 4 threads de I/O. A conexão com o banco de dados é feita através do HikariCP com um pool de conexões de tamanho máximo 20.

### 2. application-development.yml

Este perfil é utilizado para o ambiente de desenvolvimento. Ele roda na porta 8090 e tem uma configuração de threads Undertow reduzida. Além disso, ele habilita o Swagger UI e a documentação da API.

### 3. application-test.yml

Este perfil é utilizado para testes e usa o banco de dados em memória H2.

## Executando o Projeto

1. **Clone o repositório**
   ```
   git clone https://github.com/ashern000/desafio-3
   ```
2. **Navegue até o diretório do projeto**
   ```
   cd seu_repositorio
   ```
   
3. **Inicie o MySQL Docker**
   ```
   docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=ecommerce -d mysql
   ```
   
   **Ou use o Docker Compose**
   ```
   docker-compose up -d
   ```

4. **Construa e execute a aplicação com Gradle**
   ```
   ./gradlew bootRun
   ```
   
5. **Acesse a documentação da API**
   Abra um navegador e acesse `http://localhost:8080/swagger-ui.html`

## Baixando o Projeto

Você pode baixar o projeto diretamente do GitHub. Siga as instruções acima para clonar o repositório.

## Documentação

A documentação da API foi gerada com o OpenAPI e está disponível em `http://localhost:8090/swagger-ui.html` quando a aplicação está em execução.
