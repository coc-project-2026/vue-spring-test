# ⚙️ 機能詳細設計書 (API & Logic Specification)

---

## 1. エンドポイント一覧 (Endpoint List)
| Method | Path | Description | 備考 |
|:---|:---|:---|:---|
| POST | `/api/auth/login` | ログイン認証 | JWTトークン発行 |
<!-- | GET | `/api/me` | ログインユーザー情報取得 | 認証必要 | -->
| GET | `/api/words` | 単語一覧取得 | ログインユーザー基準 |
| POST | `/api/words` | 単語登録 | 新規単語追加 |
| PUT | `/api/words/{id}` | 単語修正 | ID基準修正 |
| DELETE | `/api/words/{id}` | 単語削除 | ID基準削除 |

---

## 2. インターフェース定義 (Interface Definition)

### 2.1. ログインAPI
- **Endpoint**: `POST /api/auth/login`

#### A. リクエスト構造 (Request Body)

| 項目名 (Field) | 型 (Type) | 必須 | 説明 (Description) |
|:---|:---|:---:|:---|
| `email` | String | O | ユーザーのメールアドレス |
| `password` | String | O | ユーザーのパスワード |

#### B. レスポンス構造 (Response Body)

| 項目名 (Field) | 型 (Type) | 必須 | 説明 (Description) |
|:---|:---|:---:|:---|
| `accessToken` | String | O | JWTアクセストークン |

---

### 2.2. [単語登録API]
- **Endpoint**: `POST /api/words`

#### A. リクエスト構造 (Request Body)
| 項目名 (Field) | 型 (Type) | 必須 | 説明 (Description) |
|:---|:---|:---:|:---|
| `word` | String | O | 登録する単語 |
| `meaning` | String | O | 単語の意味 |

#### B. レスポンス構造 (Response Body)
| 項目名 (Field) | 型 (Type) | 必須 | 説明 (Description) |
|:---|:---|:---:|:---|
| `id` | Long | O | 登録された単語ID |
| `word` | String | O | 登録した単語 |
| `meaning` | String | O | 単語の意味 |

---

## 3. ビジネスロジック (Business Logic)
※ 実装が必要な主要ロジックや計算式を記載してください。

- **ログイン認証処理**:
  - 入力されたメールアドレスとパスワードを確認し、
    ログイン認証を行う。

- **単語登録処理**:
  - 入力された単語および意味データをDBに保存する。

- **単語一覧取得処理**:
  - ログインユーザーに対応する単語一覧を取得する。

- **単語修正処理**:
  - 指定したIDの単語データを修正する。

- **単語削除処理**:
  - 指定したIDの単語データを削除する。

- **ページネーション処理**:
  - 単語一覧データを10件単位で分割して表示する。

---

## 4. データ定義 (Data Definition)
※ プロジェクト全体で共通して使用するDTOの構造を定義します。

### 4.1. LoginRequest
| 項目名 (Field) | 型 (Type) | 必須 | 説明 (Description) |
|:---|:---|:---:|:---|
| `email` | String | O | メールアドレス |
| `password` | String | O | パスワード |

### 4.2. LoginResponse
| 項目名 (Field) | 型 (Type) | 必須 | 説明 (Description) |
|:---|:---|:---:|:---|
| `accessToken` | String | O | 認証用トークン |

### 4.3. Word
| 項目名 (Field) | 型 (Type) | 必須 | 説明 (Description) |
|:---|:---|:---:|:---|
| `id` | Long | O | 単語ID |
| `word` | String | O | 単語 |
| `meaning` | String | O | 単語の意味 |

---