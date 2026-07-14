<div align="center">

# HelpDesk Pro

**Sistema de Gerenciamento de Chamados Técnicos**

[![Java](https://img.shields.io/badge/Java-17-007396?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.1-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Angular-17-DD0031?style=flat-square&logo=angular&logoColor=white)](https://angular.io/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-4169E1?style=flat-square&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=flat-square&logo=docker&logoColor=white)](https://docs.docker.com/compose/)

</div>

---

## Visão Geral

O **HelpDesk Pro** é uma aplicação web full-stack para gerenciamento de chamados técnicos (tickets), voltada para equipes de suporte de TI. A plataforma oferece controle completo do ciclo de vida dos chamados, gestão de usuários com controle de acesso por papéis, cálculo automático de SLA por prioridade e roteamento inteligente de chamados para técnicos disponíveis.

## Funcionalidades

- **Gestão de Chamados** — Abertura, acompanhamento e encerramento de tickets com fluxo de estados controlado
- **Controle de SLA** — Prazos calculados automaticamente com base na prioridade do chamado
- **Roteamento Inteligente** — Atribuição automática de chamados a técnicos disponíveis
- **Dashboard Analítico** — Gráficos de distribuição por status e prioridade em tempo real
- **Autenticação JWT** — Login seguro com tokens de acesso e refresh tokens
- **Controle de Acesso por Papéis** — Permissões granulares para ADMIN, TECNICO e USUARIO

## Tecnologias

### Backend
| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 17 | Linguagem principal |
| Spring Boot | 3.4.1 | Framework de aplicação |
| Spring Security | — | Autenticação e autorização |
| Spring Data JPA | — | Acesso a dados (ORM) |
| PostgreSQL | 15 | Banco de dados relacional |
| Flyway | — | Migrations do banco de dados |
| JWT (JJWT) | 0.11.5 | Tokens de autenticação |
| MapStruct | 1.5.5 | Mapeamento DTO ↔ Entidade |
| Lombok | — | Redução de boilerplate |
| Maven | — | Gerenciamento de dependências |

### Frontend
| Tecnologia | Versão | Uso |
|---|---|---|
| Angular | 17.3.0 | Framework principal (Standalone Components) |
| Angular Material | 17.3.10 | Biblioteca de componentes UI |
| TypeScript | 5.4.2 | Linguagem tipada |
| Chart.js / ng2-charts | 4.5.1 / 5.0.4 | Gráficos e visualizações |
| RxJS | 7.8 | Programação reativa |

### Infraestrutura
| Serviço | Uso |
|---|---|
| Docker Compose | Orquestração local dos serviços |
| PgAdmin 4 | Interface de administração do PostgreSQL |
| Mailhog | Servidor de e-mail para testes |

## Arquitetura

```
HelpDesk Pro
├── backend/                     # API REST — Spring Boot
│   └── src/main/java/com/helpdesk/helpdesk/
│       ├── config/              # Configurações (Security, CORS, JPA)
│       ├── controller/          # Endpoints REST
│       ├── service/             # Lógica de negócio
│       ├── entity/              # Entidades JPA
│       ├── dto/                 # Data Transfer Objects
│       ├── repository/          # Camada de acesso a dados
│       ├── security/            # Filtro JWT e configurações
│       ├── state/               # Padrão State (ciclo de vida do chamado)
│       ├── strategy/            # Padrão Strategy (cálculo de SLA)
│       ├── routing/             # Roteamento de chamados
│       ├── factory/             # Padrão Factory (criação de chamados)
│       └── mapper/              # Mapeadores MapStruct
└── frontend/                    # SPA — Angular 17
    └── src/app/
        ├── core/                # Serviços, guards, interceptors, modelos
        ├── features/            # Módulos de funcionalidades
        │   ├── auth/            # Login e registro
        │   ├── chamados/        # Gestão de chamados
        │   ├── usuarios/        # Gestão de usuários
        │   └── dashboard/       # Painel analítico
        └── layout/              # Componente de layout principal
```

### Padrões de Design Aplicados

- **State Pattern** — Gerencia transições válidas no ciclo de vida dos chamados
- **Strategy Pattern** — Cálculo de SLA plugável por prioridade
- **Factory Pattern** — Criação centralizada de chamados por categoria
- **Repository Pattern** — Abstração da camada de dados via Spring Data JPA

## Fluxo de Status dos Chamados

```
ABERTO ──► EM_ATENDIMENTO ──► RESOLVIDO ──► FECHADO
              │                    │
              ▼                    ▼
       AGUARDANDO_CLIENTE      REABERTO
```

## SLA por Prioridade

| Prioridade | Prazo |
|---|---|
| CRITICA | 4 horas |
| ALTA | 1 dia útil |
| MEDIA | 2 dias úteis |
| BAIXA | 3 dias úteis |

## Pré-requisitos

- [Docker](https://www.docker.com/) e [Docker Compose](https://docs.docker.com/compose/) instalados
- [Java 17+](https://openjdk.org/) (para execução do backend sem Docker)
- [Node.js 18+](https://nodejs.org/) e [Angular CLI](https://angular.io/cli) (para o frontend)

## Executando o Projeto

### 1. Subir os serviços de infraestrutura

```bash
docker-compose up -d
```

Isso inicializa o PostgreSQL, PgAdmin e Mailhog.

### 2. Executar o backend

```bash
cd backend
./mvnw spring-boot:run
```

O servidor sobe em `http://localhost:8081`.

### 3. Executar o frontend

```bash
cd frontend
npm install
ng serve
```

A aplicação estará disponível em `http://localhost:4200`.

## API — Principais Endpoints

**Base URL:** `http://localhost:8081/api/v1`

### Autenticação

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/auth/register` | Cadastro de novo usuário |
| `POST` | `/auth/login` | Autenticação e obtenção de token JWT |

### Chamados

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/chamados` | Listar chamados (paginado) |
| `POST` | `/chamados` | Criar novo chamado |
| `GET` | `/chamados/{id}` | Detalhes do chamado |
| `PUT` | `/chamados/{id}/atender` | Iniciar atendimento |
| `PUT` | `/chamados/{id}/resolver` | Marcar como resolvido |
| `PUT` | `/chamados/{id}/fechar` | Fechar chamado |
| `PUT` | `/chamados/{id}/reabrir` | Reabrir chamado |
| `PUT` | `/chamados/{id}/aguardar-cliente` | Aguardando retorno do cliente |
| `PUT` | `/chamados/{id}/atribuir-automaticamente` | Atribuir a técnico disponível |

### Dashboard

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/dashboard/estatisticas` | Estatísticas gerais dos chamados |

## Serviços Docker

| Serviço | Porta | Descrição |
|---|---|---|
| PostgreSQL | `5432` | Banco de dados principal |
| PgAdmin | `5050` | Interface web de administração do banco |
| Mailhog (SMTP) | `1025` | Servidor de e-mail para testes |
| Mailhog (Web) | `8025` | Interface web do Mailhog |

## Papéis e Permissões

| Papel | Descrição |
|---|---|
| `ADMIN` | Acesso total: gerencia usuários, chamados e relatórios |
| `TECNICO` | Atende, resolve e fecha chamados atribuídos |
| `USUARIO` | Abre chamados e acompanha o status |

## Banco de Dados

As migrations são gerenciadas pelo **Flyway** e executadas automaticamente na inicialização:

- `V1` — Criação da tabela `users`
- `V2` — Criação da tabela `chamados`

**Conexão padrão (desenvolvimento):**
```
Host:     localhost:5432
Database: helpdesk_db
User:     helpdesk
Password: helpdesk_password
```

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).
