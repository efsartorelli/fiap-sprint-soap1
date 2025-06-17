
===============================
💻 CASHCONTROL — API RESTful
===============================

📌 Descrição:
CashControl API é um microserviço desenvolvido em Java 17 com Spring Boot 3, focado em apoiar usuários na redução dos danos causados por apostas compulsivas. Através de endpoints bem estruturados, fornece recursos de cadastro, controle financeiro, sistema de badges e simulador de apostas vs investimentos. É a base backend que sustenta as funcionalidades do app mobile, com foco em segurança, escalabilidade e boas práticas de desenvolvimento.

---------------------------------------
🛠️ Tecnologias Utilizadas
---------------------------------------
- Java 17
- Spring Boot 3.5
- Spring Security
- Spring Data JPA
- H2 Database (Desenvolvimento)
- PostgreSQL (Produção)
- Swagger (springdoc-openapi)
- Maven
- JUnit 5 + Mockito (Testes)
- Git + GitHub (Versionamento)
- Docker e Docker Compose (Containerização)
- GitHub Actions (CI/CD)

---------------------------------------
🔑 Funcionalidades da API
---------------------------------------

👤 Autenticação
- Registro de novos usuários
- Login com geração de token UUID
- Validação de token em endpoints protegidos

💰 Transações
- Criar entrada ou saída financeira
- Listagem completa de transações
- Listagem de transações por usuário
- Consulta detalhada de uma transação

🏆 Gamificação (Badges)
- Consulta de todos os badges
- Consulta de badges de um usuário específico

📊 Dashboard
- Dados resumidos do usuário
- Visão de saldo, XP, número de transações, badges e evolução

📈 Simulador — Apostei vs Investi
- Gera uma análise comparativa entre apostar e investir baseado no comportamento do usuário

---------------------------------------
📁 Estrutura do Projeto
---------------------------------------

cashcontrol-api/
├── src/
│   ├── main/
│   │   ├── java/com/cashcontrol/ → código fonte da API
│   │   └── resources/            → configs, application.yml
│   └── test/                     → testes unitários
├── Dockerfile                    → containerização
├── docker-compose.yml            → banco PostgreSQL (opcional)
├── mvnw / mvnw.cmd               → Maven Wrapper
├── pom.xml                       → gerenciamento de dependências
└── README.md                     → documentação do projeto

---------------------------------------
▶️ Como Executar o Projeto
---------------------------------------

1️⃣ Clone o repositório:
git clone https://github.com/efsartorelli/fiap-sprint-soap2

2️⃣ Acesse a pasta:
cd cashcontrol-api

3️⃣ Compile o projeto:
./mvnw clean package -DskipTests

4️⃣ Execute o projeto:
java -jar target/cashcontrol-api-0.0.1-SNAPSHOT.jar

✅ A API estará disponível em:
http://localhost:8080

---------------------------------------
🌐 Documentação Swagger
---------------------------------------

Acesse:
http://localhost:8080/swagger-ui.html

Consulte, teste e visualize todos os endpoints disponíveis.

---------------------------------------
🔗 Endpoints Principais
---------------------------------------

| Método | Endpoint                         | Auth | Descrição                             |
| ------ | --------------------------------- | ---- | -------------------------------------- |
| POST   | /api/v1/users/register            | ❌   | Registrar novo usuário                |
| POST   | /api/v1/users/login               | ❌   | Login e gerar token                   |
| GET    | /api/v1/users/{id}                | ✔️   | Consultar dados do usuário            |
| POST   | /api/v1/transacoes                | ✔️   | Criar uma transação                   |
| GET    | /api/v1/transacoes                | ❌   | Listar todas as transações            |
| GET    | /api/v1/transacoes/usuario/{id}   | ✔️   | Listar transações por usuário         |
| GET    | /api/v1/transacoes/{id}           | ✔️   | Consultar transação específica        |
| GET    | /api/v1/badges                    | ❌   | Listar todas as badges                |
| GET    | /api/v1/badges/usuario/{id}       | ✔️   | Listar badges do usuário              |
| GET    | /api/v1/badges/{id}               | ❌   | Consultar badge específico            |
| GET    | /api/v1/dashboard/{id}            | ✔️   | Obter dados resumidos do dashboard    |
| POST   | /api/v1/simulador/usuario/{id}    | ✔️   | Simulação Apostei vs Investi          |

⚠️ Endpoints marcados com ✔️ necessitam do header:
Authorization: Bearer <token>

---------------------------------------
🚨 Tratamento de Erros
---------------------------------------

✔️ 400 — Bad Request: Erros de validação (campos obrigatórios, formatos inválidos)
✔️ 404 — Not Found: Recurso não encontrado (usuário, badge, transação)
✔️ 500 — Internal Server Error: Erros inesperados no servidor

Todos os erros seguem o seguinte formato:
{
  "campo": "mensagem de erro"
}

---------------------------------------
🔒 Segurança Implementada
---------------------------------------

- Spring Security com autenticação baseada em Token UUID
- Permissão pública apenas para /register e /login
- Configurações centralizadas em SecurityConfig
- Próximos passos: Implementar JWT, HTTPS, roles e RBAC

---------------------------------------
⚙️ CI/CD & Governança
---------------------------------------

- GitHub Actions:
  - Build Maven
  - Execução de testes unitários
  - Build da imagem Docker
  - Deploy para Docker Hub

- Branch Protegida:
  - PR obrigatório para a branch main
  - CI rodando com sucesso

- Governança:
  - Controle de versionamento semântico
  - CODEOWNERS para distribuição de responsabilidades

---------------------------------------
🗺️ Roadmap
---------------------------------------

- 🔐 v2 → Implementar JWT + OAuth2 + HTTPS
- 🔄 v3 → SOAP Service (WSDL + XSD)
- 📱 v4 → Front-end Web/Responsivo (React/Next ou Flutter)
- 🏦 v5 → Integrações com bancos reais, IA para recomendação financeira

---------------------------------------
👨‍💻 Autores
---------------------------------------
- Eduardo de Oliveira Nistal — RM94524
- Enzo Vazquez Sartorelli — RM94618
- Kaue Pastori — RM98501
- Nicolas Nogueira Boni — RM551965
- Rodrigo Viana — RM551057

---------------------------------------
📚 Observações Finais
---------------------------------------
- Projeto acadêmico, não utilizar em produção real sem adaptações.
- O banco H2 é usado apenas para desenvolvimento e testes locais.
- A versão produtiva utiliza PostgreSQL e Docker para deploy.
- API projetada para integração direta com o app mobile Cash Control.
