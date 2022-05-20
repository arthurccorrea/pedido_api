# Pedido API
API para cadastro de pedidos feita para desafio sênior 2022

## Como rodar o projeto

### Banco de dados
Neste projeto foi utilizado [PostgreSQL](https://www.postgresql.org) como banco de dados 

Caso já tenha o ambiente configurado, você irá precisar apenas criar um banco de dados com o nome que desejar (Lembre do nome pois irá precisar no futuro)
Caso precise configurar o ambiente, uma das opções é utilizar um container do Docker, para iniciar o container basta digitar o seguinte comando no terminal

`docker run --name postgres-docker -d -i -t -p5432:5432 -e POSTGRES_PASSWORD=postgres postgres`
Após executar este comando o seu servidor está pronto para uso com o usuario padrão "postgres" e a senha que passou no POSTGRES_PASSWORD
Depois de ter o ambiente configurado, basta criar um banco de dados com o nome que desejar (Lembre do nome pois irá precisar no futuro)

###  Propriedades do projeto
Um projeto Spring possui um arquivo para as variáveis de configuração, este arquivo é o **application.properties**, nele é aonde definimos a String de conexão com o banco de dados, a porta aonde o projeto será executado, entre muitas outras coisas que variam de projeto para projeto.
Abaixo temos o arquivo com breves comentários sobre cada propriedade
**OBS:**  Algumas das propriedades relacionadas ao banco de dados precisarão ser alteradas para executar o projeto, pois ela varia dependendo do ambiente do banco de dados.
**Ex:** **spring.datasource.url**, **spring.datasource.username** e **spring.datasource.password**

```
#Especificando ao JPA que o banco de dados é o PostgreSQL
spring.jpa.database=POSTGRESQL
#Especificando ao Spring que o banco de dados é o PostgreSQL
spring.datasource.platform=postgres
#Especificando para o JPA que ele deve colocar no LOG todas as queries executadas no SQL
spring.jpa.show-sql=true
#Especificando que deve apenas criar se não existir e alterar caso haja mudanças
spring.jpa.hibernate.ddl-auto=update
#Especificando ao Spring qual driver será utilizado para fazer a conexão com o banco de dados
spring.database.driverClassName=org.postgresql.Driver
#Especificando a String de conexão com o banco de dados, se atente porque o ultimo nome é o nome do banco de dados que criou anteriormente
spring.datasource.url=jdbc:postgresql://localhost:5432/provadb  
#username para conectar no banco de dados
spring.datasource.username=postgres
#Senha para conectar no banco de dados
spring.datasource.password=postgres
#Porta em que este projeto será executado
server.port=8080
```
### Maven
Após estas configurações, será necessário executar o seguinte comando no terminal, na pasta do projeto
`mvn install`
Isto é necessário, pois o QueryDSL precisa criar os arquivos modelo na pasta target.

 Após a execução do comando, já será possível rodar o projeto em seu ambiente

## Requisições
Este repositório conta com um arquivo para importação no [Insomnia](https://insomnia.rest) com exemplos das requisições, o [ Insomnia - requests.json](https://github.com/arthurccorrea/pedido_api/blob/master/Insomnia%20-%20requests.json) para utiliza-lo, basta ir em um projeto do Insomnia e importa-lo.
**OBS:** Este projeto utiliza-se de variáveis de ambiente do Insomnia, então será necessário troca-las para o seu contexto atual.
