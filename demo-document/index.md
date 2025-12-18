# Demo Document: ショッピングアプリ（ログイン機能付き）

## 目的 / スコープ

- React + Spring Boot + Postgres のデモ用ショッピングアプリ
- ログイン（登録/ログイン/ログイン状態確認）
- 商品一覧（閲覧）
- カート（追加/数量変更/削除/合計）
- 注文（チェックアウト/注文履歴）

## 機能説明

- [認証（ログイン）](features/auth.md)
- [商品一覧](features/products.md)
- [カート](features/cart.md)
- [注文](features/orders.md)

## ユースケース

ITa-paper-builder は各 feature の `resources/ユースケース情報/*.md` から、以下のユースケース定義（md）を参照する。

- [認証](usecases/auth.md)
- [商品一覧](usecases/products.md)
- [カート](usecases/cart.md)
- [注文](usecases/orders.md)

## 実装参照

- アプリ起動: `demo-application/README.md`
- API定義: `demo-application/api/openapi.yaml`
- Backend: `demo-application/backend`
- Frontend: `demo-application/frontend`
