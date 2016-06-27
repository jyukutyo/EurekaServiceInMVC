# EurekaServiceInMVC

## 概要
Spring BootではEureka Clientをアノテーションで簡単に設定することができますが、Bootが使えない普通のSpring MVCアプリケーションでEureka Serverに接続するサンプルです。
Eureka Serverは`http://localhost:8761`で起動します。serviceUrlは`http://localhost:8761/eureka/`です。
また、サービスのクライアントはribbonを通じてロードバランシングされたURLにアクセスしてサービスを利用します。

## 起動手順
Eureka Server -> サービス -> クライアントの順に起動します。
### Eureka Serverを起動する
`mvn spring-boot:run -f eureka-server/pom.xml`
### Eurekaに登録するサービスを起動する
`mvn package`してWARファイルをアプリケーションサーバなどにデプロイしてください。なおVM引数に`-Deureka.client.props=eureka`をつけてください。
#### 1つのサービスにつき複数のサーバをEurekaに登録する
`service/src/main/resources/eureka.properties`の`eureka.port`を変更し、そのポートでTomcatなどサーブレットコンテナを起動してください（改善予定）。
### Eurekaに登録するクライアントを起動する
`mvn package`してWARファイルをアプリケーションサーバなどにデプロイしてください。なおVM引数に`-Deureka.client.props=eureka`をつけてください。

## 構造
サービス、クライアントともSpringの初期化後にEureka Serverに自分を登録しに行きます。
### サービス
コンテキストルート/pathにアクセスするとそのpathをレスポンスで返すだけのサービスです。
Eureka Serverにはhttp://example.comというvipAddressで登録しています。
そのためEurekaを通じてhttp://example.com/aliceというようにアクセスすると、aliceというレスポンスで返ります。
### クライアント
コンテキストルート/にアクセスすると、Eurekaを通じてサービスに/jyukutyoでアクセスします。そのため、ブラウザの画面にはjyukutyoと表示されます。
複数サービスを起動している場合、サーブレットコンテナのアクセスログを見るとそれぞれのコンテナにアクセスが振り分けられていることがわかります。