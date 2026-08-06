# MCP Server (Java + Spring Boot)

Estructura inicial de un **MCP Server** basada en Java 21, Spring Boot y el SDK oficial `io.modelcontextprotocol.sdk`.

## Stack técnico

- Java 21 (Temurin)
- Maven
- Spring Boot 3.5.x
- MCP Java SDK (`io.modelcontextprotocol.sdk:mcp`) con transporte **Streamable HTTP**

## Qué incluye este esqueleto

- Transporte MCP **HTTP streamable** (sin stdio)
- Autenticación simple por ******** para proteger el endpoint MCP
- Configuración por variables de entorno
- Clientes HTTP (`RestClient`) para dominios:
  - Logística
  - Donaciones
  - Incentivos
  - Donadores/Entidades
- Tools MCP iniciales con JSON Schema de input
- Manejo de errores MCP para fallas HTTP/timeout
- `Dockerfile` listo para deploy en Render

> Nota: la lógica de negocio real queda marcada con `TODO` por ahora.

## Variables de entorno requeridas

- `LOGISTICA_BASE_URL`
- `DONACIONES_BASE_URL`
- `INCENTIVOS_BASE_URL`
- `MCP_SERVER_BEARER_TOKEN`

Opcionales:

- `PORT` (default `8080`)
- `MCP_ENDPOINT` (default `/mcp`)
- `HTTP_CONNECT_TIMEOUT` (default `10s`)
- `HTTP_READ_TIMEOUT` (default `60s`)

## Ejecución local

```bash
mvn spring-boot:run
```

Ejemplo:

```bash
export LOGISTICA_BASE_URL=https://logistica.onrender.com
export DONACIONES_BASE_URL=https://donaciones.onrender.com
export INCENTIVOS_BASE_URL=https://incentivos.onrender.com
export MCP_SERVER_BEARER_TOKEN=super-secreto
mvn spring-boot:run
```

## Build

```bash
mvn clean package
```

## Docker / Render

```bash
docker build -t mcp-server .
docker run --rm -p 8080:8080 \
  -e LOGISTICA_BASE_URL=https://logistica.onrender.com \
  -e DONACIONES_BASE_URL=https://donaciones.onrender.com \
  -e INCENTIVOS_BASE_URL=https://incentivos.onrender.com \
  -e MCP_SERVER_BEARER_TOKEN=super-secreto \
  mcp-server
```

Render (Web Service) puede usar:

- Build command: `mvn clean package`
- Start command: `java -jar target/mcp-server-0.0.1-SNAPSHOT.jar`

## Endpoint MCP

- Endpoint por defecto: `/mcp`
- Transporte: **Streamable HTTP**
- Requiere header:

```http
Authorization: ******
```
