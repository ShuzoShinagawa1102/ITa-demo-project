# テスト観点作成指針

`ITa-paper-builder/controller/ITa仕様書作成指示書.md` に従い、対象featureの `resources/**` が指し示す資材（実装箇所/OpenAPIなど）を読みに行ってテスト観点を洗い出す。

## 出力ルール（シンプル）

- 1観点 = 1行
- 手順は書かない（手順はテストシナリオで作る）

## テスト観点

### 画面表示

- 初期表示/遷移後表示/未ログイン時表示/エラー表示（根拠: `feature/**/resources/画面情報/*.md`）

### 画面項目

- 表示項目、必須/任意、活性/非活性、入力制約（文字数/形式/禁止文字/空白など）/相関チェックの有無（根拠: `feature/**/resources/画面情報/*.md`, `demo-application/frontend/src/validation/schemas.ts`）

### API呼び出し

- 呼び出し条件、パス/メソッド、送受信項目、ステータス（200/400/401など）（根拠: `feature/**/resources/API情報/*.md`, `demo-application/api/openapi.yaml`）

### APIロジック

- 前提条件、分岐、状態変化（カート/注文など）、DB反映（根拠: `feature/**/resources/ユースケース情報/*.md`, `feature/**/resources/DB情報/*.md`）
