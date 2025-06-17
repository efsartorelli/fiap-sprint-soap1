# 📘 CashControl API

***Microserviço RESTful em Java 17 / Spring Boot 3 focado em reduzir danos de apostas compulsivas por meio de gamificação, ciência de dados e design comportamental.***

---

## 🎯 Objetivos do Projeto

1. Prover um **MVP** de aplicação financeira simulada com foco na prevenção de comportamentos de risco.
2. Implementar **API RESTful** modular, versionada e documentada.
3. Integrar **gamificação** (XP, badges) para incentivar hábitos saudáveis.
4. Fornecer **simulador** comparativo de apostar vs investir.
5. Demonstrar práticas de **segurança**, **tratamento de erros**, **CI/CD**, **containerização** e **performance**.

---

## 🛠 Tech Stack & Ferramentas

| Camada             | Tecnologia / Ferramenta       |
| ------------------ | ----------------------------- |
| Linguagem          | Java 17                       |
| Framework          | Spring Boot 3.5               |
| Banco de Dados     | H2 (dev), PostgreSQL (prod)   |
| Segurança          | Spring Security, UUID Tokens  |
| Documentação       | springdoc-openapi (Swagger)   |
| Containerização    | Docker, Docker Compose        |
| CI/CD              | GitHub Actions, Docker Hub    |
| Testes Unitários   | JUnit 5, Mockito              |
| Testes Performance | Apache JMeter                 |
| Versionamento      | Git, GitHub                   |
| Governança         | Branch protection, CODEOWNERS |

---

## 🚀 Setup Local

### Pré-requisitos

* Java 17 (JDK)
* Maven ou uso de Maven Wrapper
* Docker & Docker Compose (opcional para DB externo)
* Git

### Clonar e Build

```bash
git clone https://github.com/<seu-usuario>/cashcontrol-api.git
cd cashcontrol-api
./mvnw clean package -DskipTests
```


### 🚀 Execução da Aplicação

* Após o **build** com Maven, execute:

  ```bash
  java -jar target/cashcontrol-api-0.0.1-SNAPSHOT.jar
  ```

---

## 📑 API & Documentação

### Base URL v1

```
http://localhost:8080/api/v1
```

### Autenticação

* **Registrar** (sem autenticação): `POST /users/register`
* **Login**: `POST /users/login` → retorna `{ token }`
* **Usar token** para endpoints protegidos: header `Authorization: Bearer <token>`

### Endpoints Principais

See [Swagger UI](http://localhost:8080/swagger-ui.html) for full details.

| Método | Endpoint                       | Auth | Descrição                     |
| ------ | ------------------------------ | ---- | ----------------------------- |
| POST   | `/users/register`              | ❌    | Registrar usuário             |
| POST   | `/users/login`                 | ❌    | Autenticar e obter token      |
| GET    | `/users/{id}`                  | ✔️   | Consultar perfil              |
| POST   | `/transacoes`                  | ✔️   | Criar transação               |
| GET    | `/transacoes`                  | ❌    | Listar todas transações       |
| GET    | `/transacoes/usuario/{userId}` | ✔️   | Transações do usuário         |
| GET    | `/transacoes/{id}`             | ✔️   | Buscar transação por ID       |
| GET    | `/badges`                      | ❌    | Listar todas badges           |
| GET    | `/badges/usuario/{userId}`     | ✔️   | Badges de um usuário          |
| GET    | `/badges/{id}`                 | ❌    | Buscar badge por ID           |
| GET    | `/dashboard/{userId}`          | ✔️   | Dashboard personalizado       |
| POST   | `/simulador/usuario/{userId}`  | ✔️   | Simulação apostar vs investir |

> ✔️ requer header `Authorization`

---

## 🚨 Tratamento de Erros

Centralizado em `GlobalExceptionHandler`:

* **400 Bad Request**: erros de validação (`@Valid`)
* **404 Not Found**: recursos não encontrados (`UsuarioNaoEncontradoException`)
* **500 Internal Server Error**: erros não tratados

Exemplo de payload de erro:

```json
{ "campo": "mensagem de erro" }
```

---

## ⚡ Segurança

* **Spring Security** + HTTP Basic para endpoints públicos
* **Token UUID** gerado no login e validado em cada request
* **Configuração** em `SecurityConfig` permite `/users/register` e `/users/login` sem autenticação
* **Próximas melhorias**: JWT, HTTPS, roles e `@PreAuthorize`

---

---

## 🛠 CI/CD & Governança

### GitHub Actions

Pipeline define:

1. **build** (Maven package)
2. **test** (JUnit)
3. **upload artefato**
4. **docker build & push** ao Docker Hub

### Branch Protection & CODEOWNERS

* **main** protegido: PR review, CI green
* **CODEOWNERS** atribuem responsabilidades automaticamente

### Versionamento Semantic

* `pom.xml` usa versions `1.0.0`, `1.1.0` etc.
* URLs versionadas: `/api/v1/...`, pronto para `/api/v2` (breaking changes)

---

## 🔮 Roadmap

* **v2**: JWT, OAuth2, HTTPS
* **v3**: SOAP WebService + WSDL/XSD
* **v4**: Front-end responsivo (React Native / Flutter)
* **v5**: Integração com bancos reais, analytics avançado

---

## 🤝 Contribuição

1. Fork o repositório e clone localmente
2. Crie branch feature/x
3. Commit e PR para `main` após testes e reviews
4. Siga as [Guia de Contribuição](CONTRIBUTING.md)

---

## 📞 Contato

* **Desenvolvedor**: Seu Nome ([seu-email@dominio.com](mailto:seu-email@dominio.com))
* **GitHub**: [https://github.com/](https://github.com/)<seu-usuario>/cashcontrol-api
* **LinkedIn**: [https://linkedin.com/in/](https://linkedin.com/in/)<seu-usuario>
* **Issues & Support**: Abra tickets em GitHub Issues

---

© 2025 CashControl. Todos os direitos reservados.
