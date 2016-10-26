# EurekaServiceInMVC

## 概要
Spring BootではEureka Clientをアノテーションで簡単に設定することができますが、Bootが使えない普通のSpring MVCアプリケーションでEureka Serverに接続するサンプルです。
Eureka Serverは`http://localhost:8761`で起動します。serviceUrlは`http://localhost:8761/eureka/`です。
また、サービスのクライアントはribbonを通じてロードバランシングされたURLにアクセスしてサービスを利用します。

## 起動手順
Eureka Server -> サービス -> クライアントの順に起動します。
### Eureka Serverを起動する
```
mvn spring-boot:run -f eureka-server/pom.xml
```
`http://localhost:8761/`にアクセスするとSpring Eurekaの画面が表示されます。
### Eurekaに登録するサービスを複数起動する
2つ起動できるように設定ファイルを作成しています。
```
cd service
```

#### 1つめ
`mvn jetty:run -Djetty.http.port=8092 -Deureka.client.props=eureka1`
#### 2つめ
`mvn jetty:run -Djetty.http.port=8093 -Deureka.client.props=eureka2`
#### nつめ
`service/src/main/resources`に`eureka1.properties`といった設定ファイルを作成しています。これをコピーし`eureka.instanceId`と`eureka.port`を変更してください。指定したポートでJettyを起動します。
```
mvn jetty:run -Djetty.http.port=[enrekan.propertiesで設定したポート] -Deureka.client.props=eurekan
```
### Eurekaにサービスが登録できているかを確認する
`http://localhost:8761/`にアクセスすると"Instances currently registered with Eureka"の項目にApplicationとして"EUREKASERVICE"があり、Statusに起動したサービスの数だけinstanceIdが表示されます。

### Eurekaに登録するクライアントを起動する
```
cd client
mvn jetty:run -Djetty.http.port=8096 -Deureka.client.props=eureka
```
#### Eureka、ribbonでサービスを呼び出す
`http://localhost:8096/`にアクセスすると、画面にjyukutyoと表示されます。
Eurekaを通じてサービスに/jyukutyoでアクセスします。そのため、ブラウザの画面にはjyukutyoと表示されます。
複数サービスを起動している場合ribbonがロードバランシングしているので、サーブレットコンテナのアクセスログを見るとそれぞれのコンテナにアクセスが振り分けられていることがわかります。

## 構造
サービス、クライアントともSpringの初期化後にEureka Serverに自分を登録しに行きます。
### サービス
コンテキストルート/pathにアクセスするとそのpathをレスポンスで返すだけのサービスです。
Eureka Serverには`http://example.com`というvipAddressで登録しています。
そのためEurekaを通じて`http://example.com/alice`というようにアクセスすると、aliceというレスポンスで返ります。
