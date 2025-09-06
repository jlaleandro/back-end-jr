# back_end_jr (mock de usuários)

API simples em **Spring Boot** para **leitura de usuários** a partir de um arquivo mock (`src/main/resources/mock/mock-users.json`).  
Estrutura de pacotes: `com.jla.back_end_jr` (dtos, repositories, services, controllers).

## Requisitos

- Java 21+
- (Opcional) cURL para testar

## Como rodar

1. Garanta o profile `mock` ativo (já configurado):
   ```yaml
   # src/main/resources/application.yml
   spring:
     profiles:
       active: mock
   server:
     port: 8080
     servlet:
       context-path: /back_end_jr
   ```
2. Execute a aplicação:
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
curl "http://localhost:8080/back_end_jr/api/usuarios?q=bruno"

# Intervalo de datas (created_at)
curl "http://localhost:8080/back_end_jr/api/usuarios?createdFrom=2024-05-01T00:00:00&createdTo=2024-06-01T00:00:00"

# Paginação
curl "http://localhost:8080/back_end_jr/api/usuarios?page=1&size=10"

# Combinado
curl "http://localhost:8080/back_end_jr/api/usuarios?isActive=true&role=manager&q=nogueira&page=0&size=5"
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

## Observações

- `UserDto.createdAt` é `LocalDateTime`. O JSON usa formato ISO com `Z`; a desserialização é feita via `JavaTimeModule`.
- O repositório mock ordena por `createdAt` **desc** e filtra por `isActive`, `role`, `q`, `createdFrom`, `createdTo`.
- Para trocar para JPA depois, crie uma implementação `@Profile("jpa")` que implemente `UsuarioRepository`.
