# Bank API & Frontend

Aplicação completa de banco digital com Backend (Spring Boot) e Frontend (React/Vite). Permite cadastrar usuários, realizar transferências e visualizar saldo em tempo real com uma interface moderna.

## 🚀 Tecnologias

**Backend**
- Java 17+
- Spring Boot 3.4.1
- H2 Database (em memória)
- Maven
- JDBC

**Frontend**
- React
- Vite
- Axios
- CSS Modules (estilo Premium Dark Mode)

---

## ⚙️ Como Executar

Para rodar o projeto completo, você precisará de dois terminais abertos.

### 1. Terminal 1: Backend
Inicie o servidor Java. Ele rodará na porta `8080`.
```bash
./mvnw spring-boot:run
```

### 2. Terminal 2: Frontend
Inicie a interface gráfica. Ela rodará na porta `5173`.
```bash
cd src/main/javascript
npm install  # Apenas na primeira vez
npm run dev
```

Abra no navegador: [http://localhost:5173](http://localhost:5173)

---

## 🔌 Endpoints da API (Backend)
Se quiser testar apenas a API (sem o frontend):

| Método | Endpoint | Descrição | Exemplo JSON |
|--------|----------|-----------|--------------|
| GET    | `/` | Status da API | - |
| GET    | `/api/users` | Listar usuários | - |
| POST   | `/api/users` | Criar usuário | `{"nome":"Ana", "idade":25, "cpf":"12345678901", "saldo":1000.0}` |
| POST   | `/transacoes/realizar` | Transferir | `{"contaOrigem":"123", "contaDestino":"456", "valor":100.0}` |

**Dados Iniciais:** O banco já veem com alguns usuários de teste (definidos em `src/main/resources/data.sql`).

---

## 📂 Estrutura do Projeto

```
/
├── src/main/java         # Código Backend (Spring Boot)
├── src/main/javascript   # Código Frontend (React/Vite)
├── src/main/resources    # Configurações e SQL
└── pom.xml               # Dependências do Java
```

