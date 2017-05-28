# README #

Este README traz as informações necessárias para os usuários baixarem, instalarem e rodarem o projeto Finance.

### Projeto Finance ###

O projeto Finance é uma solução digital que visa realizar o controle financeiro de uma organização.
O Finance foi construído utilizando tecnologia AngularJs para o front-end e Java 8 (Springboot) para back-side. As informações são armazenadas em banco de dados relacional Mysql.
O projeto conta hoje com as seguintes funcionalidades:

* Criação/Edição de registros de contas (à pagar e a receber).


### Instalação ###

A instalação do Finance requer inicialmente que a maquina tenha instalados o [Java] (https://www.java.com/pt_BR/download/), o [Maven](https://maven.apache.org/download.cgi) e o [Mysql] (https://www.mysql.com/downloads/). Posteriormente é  necessário executar o seguinte script no banco de dados:

```
CREATE USER `jiva_test`@`localhost` IDENTIFIED BY 'super-senha';
CREATE DATABASE `finance_test`;
GRANT ALL PRIVILEGES ON *.* TO `jiva_test`@`localhost`;


CREATE USER `jiva`@`localhost` IDENTIFIED BY '##jiv4@2017##';
CREATE DATABASE `finance`;
GRANT ALL PRIVILEGES ON *.* TO `jiva`@`localhost`;
```
O próximo passo é a instalação e disponibilização dos serviços do projeto back-side Java. Para realizar essa tarefa é necessário executar os seguintes script à partir da raiz do projeto finance:

```
$ mvn clean install
$ cd finance-web
$ mvn spring-boot:run
```
Por fim, basta instalar e disponibilizar o projeto front-end Anjular. Para isso é necessrio...
