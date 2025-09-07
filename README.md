# back_end_jr (mock de usuários)

API simples em **Spring Boot** para **leitura de usuários** a partir de um arquivo mock (`src/main/resources/mock/mock-users.json`).  
Estrutura de pacotes: `com.jla.back_end_jr` (dtos, repositories, services, controllers).

## Requisitos

- Java 21+
  Você pode baixar o JDK 21 (OpenJDK) diretamente no site da Oracle, que oferece instaladores para Windows, Linux e macOS:
  👉 Download JDK 21 - Oracle
  Basta escolher o pacote do seu sistema operacional, instalar e depois confirmar com java -version no terminal/cmd.

- cURL para testar
  Você pode baixar o cURL para qualquer sistema operacional no site oficial: 👉 https://curl.se/download.html
  Escolha o instalador do seu SO (Linux, macOS ou Windows); no Linux/macOS geralmente já vem instalado por padrão.

## Como rodar

1. Execute a aplicação:
   - **Maven:** `./mvnw spring-boot:run`
     ou gerar o jar
     mvn clean package
   - **Jar:** `java -jar target/back-end-jr-1.0.0.jar`

> A API ficará em: `http://localhost:8080/back_end_jr`

## Endpoints

### Listar usuários (com filtros e paginação)

`GET /api/usuarios`

**Parâmetros opcionais**

- `isActive` (boolean): `true` ou `false`
- `role` (string): filtra pela role (case-insensitive)
- `q` (string): busca textual em `name` e `email`
- `createdFrom` (ISO local datetime): ex. `2024-05-01T00:00:00`
- `createdTo` (ISO local datetime): ex. `2024-06-01T00:00:00`
- `page` (int, default 0)
- `size` (int, default 20)

**Exemplos**

```bash
# Todos
curl "http://localhost:8080/back_end_jr/api/usuarios"

# Apenas ativos
curl "http://localhost:8080/back_end_jr/api/usuarios?isActive=true"

# Por role
curl "http://localhost:8080/back_end_jr/api/usuarios?role=manager"

# Busca textual (name/email)
# Padrão conhecido: muita gente que consome APIs já espera um q para “full-text search”.
curl "http://localhost:8080/back_end_jr/api/usuarios?q=bruno"
curl "http://localhost:8080/back_end_jr/api/usuarios?q=fferraz2@contoso.dev"


# Intervalo de datas (created_at)
curl "http://localhost:8080/back_end_jr/api/usuarios?createdFrom=2024-05-01T00:00:00&createdTo=2024-06-01T00:00:00"

# Paginação com formatar JSON
curl "http://localhost:8080/back_end_jr/api/usuarios?page=1&size=10" | jq

# Combinado
curl "http://localhost:8080/back_end_jr/api/usuarios?isActive=true&role=manager&q=nogueira&page=0&size=5"

# Buscar por ID inexistente (404)
curl -i "http://localhost:8080/api/usuarios/99999"


```

### Buscar por ID

`GET /api/usuarios/{id}`

```bash
curl "http://localhost:8080/back_end_jr/api/usuarios/1"
```

## Estrutura de pastas (principal)

```
src/main/java/com/jla/back_end_jr/dtos/UserDto.java
src/main/java/com/jla/back_end_jr/repositories/UsuarioRepository.java
src/main/java/com/jla/back_end_jr/repositories/UsuarioRepositoryMock.java
src/main/java/com/jla/back_end_jr/services/UsuarioService.java
src/main/java/com/jla/back_end_jr/controllers/UsuarioController.java
src/main/resources/application.yml
src/main/resources/mock/mock-users.json
```
