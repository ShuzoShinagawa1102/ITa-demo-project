# ITa-demo-project

AIを使った ITa（内部結合試験）仕様書の自動作成デモプロジェクトです。

## プロジェクト構成

- `demo-application/`
  - React + Spring Boot + Postgres のショッピングアプリ（ログイン機能付き）デモ実装
  - 起動手順: `demo-application/README.md`
- `demo-document/`
  - ドキュメント（機能説明・ユースケース定義）
  - 起点: `demo-document/index.md`
- `ITa-paper-builder/`
  - feature/resources に記載した参照先をもとに、AIでテスト観点→テストシナリオ→提出版（表）を生成するための土台
  - 実行手順: `ITa-paper-builder/README.md`

