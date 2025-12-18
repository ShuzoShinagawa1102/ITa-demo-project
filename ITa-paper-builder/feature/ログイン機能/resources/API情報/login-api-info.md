# ログイン機能（認証）- API情報

認証APIの仕様は OpenAPI を参照する（仕様本文はここに重複記載しない）。

## 参照先

- OpenAPI: `demo-application/api/openapi.yaml`
  - `paths./api/auth/register.post` を参照
  - `paths./api/auth/login.post` を参照
  - `paths./api/auth/me.get` を参照
- 実装（Controller）: `demo-application/backend/src/main/java/com/demo/shop/presentation/rest/AuthController.java`
- 実装（JWT/セキュリティ）:
  - `demo-application/backend/src/main/java/com/demo/shop/infrastructure/security/SecurityConfig.java`
  - `demo-application/backend/src/main/java/com/demo/shop/infrastructure/security/JwtService.java`

