# 🚀 Desafio – Primeira Questão

![Diagrama MER](src/main/resources/static/mer-desafio-1.drawio.png)

# 🚀 Desafio – Segunda Questão

**REST API de Usuários (HTTP + CORS + Respostas)**

Este desafio consiste em implementar uma **API de leitura de usuários** seguindo boas práticas de arquitetura e padrões REST.

---

## 📋 Requisitos do Desafio

### Padrões a seguir

- **Respostas**: usar envelope com `data` e, quando aplicável, `pagination`.
- **Consultas**: suportar filtros, limites e ordenação.
- **Organização**: separar em camadas (`controllers`, `services`, `repositories`).
- **Boas práticas**: logs simples, variáveis de ambiente, documentação (`README`).
- **CORS**: habilitado apenas para origens confiáveis (ex.: `http://localhost:8080`).

---

## 📌 Endpoints

### 1. **GET /usuarios**

Lista usuários com suporte a filtros e paginação.

**Parâmetros de query:**

- `page` → página atual (default: `1`)
- `page_size` → itens por página (default: `10`, máximo: `50`)
- `q` → busca por **nome** ou **email**
- `role` → filtrar por função (opcional)
- `is_active` → filtrar por status ativo (opcional)

# back_end_jr (mock de usuários)

API simples em **Spring Boot** para **leitura de usuários** a partir de um arquivo mock (`src/main/resources/mock/mock-users.json`).  
Estrutura de pacotes: `com.jla.back_end_jr` (dtos, repositories, services, controllers).

## Requisitos

- Java 21+,
  Você pode baixar o JDK 21 (OpenJDK) diretamente no site da Oracle, que oferece instaladores para Windows, Linux e macOS:
  👉 Download JDK 21 - Oracle
  Basta escolher o pacote do seu sistema operacional, instalar e depois confirmar com java -version no terminal/cmd.

- cURL para testar,
  Você pode baixar o cURL para qualquer sistema operacional no site oficial: 👉 https://curl.se/download.html
  Escolha o instalador do seu SO (Linux, macOS ou Windows); no Linux/macOS geralmente já vem instalado por padrão.

- (Opcional) JQ, - O jq é uma ferramenta de linha de comando usada para filtrar e formatar JSON de forma simples e legível.

## 📦 Instalação do jq

- **macOS**: `brew install jq` — **Linux**: `sudo apt install jq -y` — **Windows**: `choco install jq`
- Binários oficiais disponíveis em: [https://stedolan.github.io/jq/download/](https://stedolan.github.io/jq/download/)
- Após instalar, teste com: `jq --version`

## Como rodar projeto

1. Execute a aplicação:

   - **Maven:** `./mvnw spring-boot:run`

     ou, mvn clean package para gerar JAR

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
# por id
curl "http://localhost:8080/back_end_jr/api/usuarios/1"

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
