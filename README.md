# com.github.micchon.maintenance-com.github.micchon.cont

[@hexx](https://github.com/hexx)さんの[ActionCont](https://github.com/hexx/action-com.github.micchon.cont)を用いた、メンテナンス状態を判定するモジュールです。

# 概要

サーバーがメンテナンス状態であるかどうかを __ファイルの存在__ で管理するとします。
メンテナンス状態では、外部からのアクセスは一切受け付けません。
このとき、このモジュールの挙動は

- ファイルがある
    - メンテナンス中
    - アクセスしても 503 が返る
- ファイルがない
    - メンテナンスではない
    - アクセスしたら 200 が返る（実際のアプリケーションの挙動ではコントローラの処理が行われる）

である必要があります。更に言うと、コントローラの処理が始まる前にこの処理が入れば理想です。

それを、ActionContを使って取り扱いやすくし、コントローラの前処理として挿入できるようにしたのがこのモジュールです。

# License

public domain
