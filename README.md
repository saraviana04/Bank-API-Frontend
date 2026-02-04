## Bank API

API REST em Spring Boot para cadastrar usuários, consultar usuários e realizar transferências entre contas usando JDBC com H2 em memória.

**Stack**
- Java 17+
- Spring Boot 3.4.1
- H2 (in-memory)
- Maven 3+
- JDBC (sem JPA)

**Executar**
```bash
mvn spring-boot:run
```

**URLs**
- API: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1`
- Usuário: `sa`
- Senha: (em branco)

**Dados iniciais**
- `src/main/resources/data.sql`

**Endpoints**
1. Status
- `GET /`
```bash
curl -s http://localhost:8080/
```

2. Cadastrar usuário
- `POST /api/users`
```bash
curl -s -X POST http://localhost:8080/api/users \
  -H 'Content-Type: application/json' \
  -d '{"nome":"João Silva","idade":25,"cpf":"12345678901","saldo":1000.0}'
```

3. Buscar usuário por ID
- `GET /api/users/{id}`
```bash
curl -s http://localhost:8080/api/users/1
```

4. Listar usuários
- `GET /api/users`
```bash
curl -s http://localhost:8080/api/users
```

5. Transferência
- `POST /transacoes/realizar`
```bash
curl -s -X POST http://localhost:8080/transacoes/realizar \
  -H 'Content-Type: application/json' \
  -d '{"contaOrigem":"12345","contaDestino":"67890","valor":200.0}'
```

**Regras de negócio**
- Idade mínima 18 anos.
- CPF único.
- Transferências exigem contas existentes e saldo suficiente.

**Estrutura de pastas**
- `controller`
- `dao`
- `dto`
- `model`
- `service`
