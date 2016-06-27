# EurekaServiceInMVC

## 概要
Spring BootではEureka Clientをアノテーションで簡単に設定することができますが、Bootが使えない普通のSpring MVCアプリケーションでEureka Serverに接続するサンプルです。
Eureka Serverは`http://localhost:8761`で起動します。serviceUrlは`http://localhost:8761/eureka/`です。jjugccc-handsonのEureka Serverをベースに作っています。

## 起動
### Eureka Serverの起動
`mvn spring-boot:run -f eureka-server/pom.xml`
### Eurekaに登録するサービスの起動
`mvn package`してWARファイルをアプリケーションサーバなどにデプロイしてください。なおVM引数に`-Deureka.client.props=eureka`をつけてください。
#### 1つのサービスにつき複数のサーバをEurekaに登録する
`service/src/main/resources/eureka.properties`の`eureka.port`を変更し、そのポートでTomcatなどサーブレットコンテナを起動してください（改善予定）。

