# EurekaServiceInMVC

## 概要
Spring BootではEureka Clientをアノテーションで簡単に設定することができますが、Bootが使えない普通のSpring MVCアプリケーションでEureka Serverに接続するサンプルです。
Eureka Serverは`http://localhost:8761`で起動しているものとします。serviceUrlは`http://localhost:8761/eureka/`です。Eureka Serverはこちらにあります。jjugccc-handsonのEureka Serverをベースに作っています。
https://github.com/jyukutyo/jjugccc-handson/tree/master/exercise/03-netflix/eureka-server

## 起動
`mvn package`してWARファイルをアプリケーションサーバなどにデプロイしてください。なおVM引数に`-Deureka.client.props=eureka`をつけてください。
