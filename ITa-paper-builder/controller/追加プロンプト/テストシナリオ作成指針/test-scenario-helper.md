# テストシナリオ作成指針

`ITa-paper-builder/controller/ITa仕様書作成指示書.md` に従い、テスト観点（各feature配下の `output/テスト観点/test-perspective.md`）をもとにテストシナリオを作成する。

## 入力（読みに行く情報）

- テスト観点: `ITa-paper-builder/feature/<機能名>/output/テスト観点/test-perspective.md`
- 対象featureの `resources/**` が指し示す資材（実装箇所/OpenAPIなど）

## 出力ルール（シンプル）

- 1シナリオ = 1ケース（番号を振る）
- 手順は最小限（結合試験で必要な操作/呼び出しだけ）
- 期待結果は「画面」「API」「DB/状態」のどれを確認するかが分かるように書く
- 各シナリオに、根拠（参照先パス）を1つ以上付ける
- 目的（またはタイトル）の先頭にカテゴリを付ける（例: `[画面表示]`）

## シナリオ雛形

- `No`: 連番
- `目的`: 何を確認するか（先頭に `[画面表示]` / `[画面項目]` / `[API呼び出し]` / `[APIロジック]` を付与）
- `前提`: ログイン状態/初期データ/画面遷移など
- `手順`: 操作 or API呼び出し（最小限）
- `期待結果`: 画面表示/画面項目/APIレスポンス/状態変化
- `根拠`: `feature/**/resources/**` など参照先

## シナリオ観点（テスト観点との対応）

### 画面表示

- 初期表示、遷移後表示、未ログイン時表示、エラー表示が期待どおりか（根拠: `feature/**/resources/画面情報/*.md`）

### 画面項目

- 表示項目、活性/非活性、入力制約（文字数/形式/禁止文字/空白/相関チェック）の確認（根拠: `feature/**/resources/画面情報/*.md`, `demo-application/frontend/src/validation/schemas.ts`）

### API呼び出し

- 呼び出し条件、パス/メソッド、送受信項目、ステータス（200/400/401など）の確認（根拠: `feature/**/resources/API情報/*.md`, `demo-application/api/openapi.yaml`）

### APIロジック

- 前提条件、分岐、状態変化（カート/注文など）、DB反映の確認（根拠: `feature/**/resources/ユースケース情報/*.md`, `feature/**/resources/DB情報/*.md`）
