# ITa-paper-builder

featureごとの `resources/**`（参照先）をもとに、AIで ITa 仕様書作成を進めるためのディレクトリです。

## 使い方（概要）

1. LLMのコンテキストに指示書を読み込ませる  
   `ITa-paper-builder/controller/ITa仕様書作成指示書.md`
2. LLMに「この指示書を実行してください」などのメッセージで依頼する
3. LLMが次に「どの機能（feature）を対象にするか」を確認するので、対象機能を選ぶ  
   例: `カート機能`
4. AIは `resources/**` に書かれた参照先（ソース/ドキュメント/OpenAPIなど）を読み、成果物を作成する

## 成果物（出力先）

各feature配下の `output/` に出力します。

- テスト観点: `ITa-paper-builder/feature/<機能名>/output/テスト観点/test-perspective.md`
- テストシナリオ: `ITa-paper-builder/feature/<機能名>/output/テストシナリオ/test-scenario.md`
- 提出版仕様書: `ITa-paper-builder/feature/<機能名>/output/提出版仕様書/test-final-output.md`

## 補足

- `demo-application/` は動作可能な状態ですが、仕様書作成そのものにアプリ起動は必須ではありません（参照先のソース/ドキュメントが読めれば進められます）。
