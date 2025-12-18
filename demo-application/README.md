# Demo Shopping Application（React + Spring Boot）

ログイン機能を備えた、簡単なショッピングアプリのデモ実装です。

## 前提（ローカル実行）

- Docker Desktop（Postgres起動に使用）
- Node.js 18+（フロントエンド）
- Java 17+（バックエンド）

※ Dockerを使わない場合は、Postgresを別途用意し `SHOP_DB_URL` / `SHOP_DB_USER` / `SHOP_DB_PASSWORD` を設定してください。

## 起動手順（Quick start）

### 1) Postgresを起動

```sh
cd demo-application
docker compose up -d
```

### 2) Backend（Spring Boot）を起動

PowerShell（Windows）:

```sh
cd demo-application/backend
set SHOP_JWT_SECRET=dev-secret-change-me
.\mvnw.cmd spring-boot:run
```

### 3) Frontend（React）を起動

```sh
cd demo-application/frontend
npm install
npm run dev
```

## URL / 参照

- Frontend: `http://localhost:5173`
- Backend: `http://localhost:8080`
- API定義（OpenAPI）: `demo-application/api/openapi.yaml`
