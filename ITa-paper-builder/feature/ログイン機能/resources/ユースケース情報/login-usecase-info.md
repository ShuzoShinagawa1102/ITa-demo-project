# ログイン機能（認証）- ユースケース情報

ユースケース定義（md）:

- `demo-document/usecases/auth.md`

## UC-AUTH-01: ユーザー登録

- フロント（画面）: `demo-application/frontend/src/pages/RegisterPage.tsx`
- フロント（入力スキーマ）: `demo-application/frontend/src/validation/schemas.ts`
- バックエンド（ユースケース）: `demo-application/backend/src/main/java/com/demo/shop/application/auth/AuthService.java`
- バックエンド（API）: `demo-application/backend/src/main/java/com/demo/shop/presentation/rest/AuthController.java`

## UC-AUTH-02: ログイン

- フロント（画面）: `demo-application/frontend/src/pages/LoginPage.tsx`
- フロント（入力スキーマ）: `demo-application/frontend/src/validation/schemas.ts`
- バックエンド（ユースケース）: `demo-application/backend/src/main/java/com/demo/shop/application/auth/AuthService.java`

## UC-AUTH-03: ログイン状態確認

- フロント（起動時確認）: `demo-application/frontend/src/state/auth.tsx`
- バックエンド（API）: `demo-application/backend/src/main/java/com/demo/shop/presentation/rest/AuthController.java`

