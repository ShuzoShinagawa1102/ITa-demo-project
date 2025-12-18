# Demo Shopping Backend

## Prerequisites

- Java 17+
- Postgres (recommended: `../docker-compose.yml`)

## Run

1) Start DB

```sh
cd ../
docker compose up -d
```

2) Start backend

```sh
cd backend
set SHOP_JWT_SECRET=dev-secret-change-me
.\mvnw.cmd spring-boot:run
```

Backend: `http://localhost:8080`
