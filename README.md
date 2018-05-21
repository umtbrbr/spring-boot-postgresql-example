# spring-boot-postgresql-example

The aim of this experiment project are management of campaigns for admin panel and calculate discounts using the campaigns.

## API Overview

This project has 5 API as below.

| Path | Method | Body Params | Request Params | Response | Description
| --- | --- | --- | --- | --- | ---
| /demo/campaigns | GET | | | [Campaign](https://github.com/umtbrbr/spring-boot-postgresql-example/blob/master/src/main/java/com/campaign/demo/entity/Campaign.java) | Returns all campaigns without any filter
| /demo/campaign/create | POST | [Campaign](https://github.com/umtbrbr/spring-boot-postgresql-example/blob/master/src/main/java/com/campaign/demo/entity/Campaign.java) | | [Campaign](https://github.com/umtbrbr/spring-boot-postgresql-example/blob/master/src/main/java/com/campaign/demo/entity/Campaign.java) | Create a new campaign with given campaign details
| /demo/campaign/update | PUT | [Campaign](https://github.com/umtbrbr/spring-boot-postgresql-example/blob/master/src/main/java/com/campaign/demo/entity/Campaign.java) | id | [Campaign](https://github.com/umtbrbr/spring-boot-postgresql-example/blob/master/src/main/java/com/campaign/demo/entity/Campaign.java) | Find campaign with given id and update that campaign 
| /demo/campaign/delete | DELETE | | id | [Campaign](https://github.com/umtbrbr/spring-boot-postgresql-example/blob/master/src/main/java/com/campaign/demo/entity/Campaign.java) | Delete campaign with given campaign id
| /demo/campaign/calculateDiscounts | POST | List<[BasketItem>](https://github.com/umtbrbr/spring-boot-postgresql-example/blob/master/src/main/java/com/campaign/demo/entity/BasketItem.java) | | List<[BasketItem>](https://github.com/umtbrbr/spring-boot-postgresql-example/blob/master/src/main/java/com/campaign/demo/entity/BasketItem.java) | Calculate discounts with using campaign of category or product

## Run

### 1. Build app

Create jar file with below maven command

    mvn clean install

### 2. Run app in docker container

Execute.sh file. This .sh file provides pull postgres image and connect this app to postgres by using --link flag

    ./docker.sh
    
### 3. Create tables and insert data

This experiment project is using Postgres for data model. There are two sql files under `src/main/resources` directory. Schema and tables can create with `create_table.sql` and initialize data can insert with `insert_data.sql` where the Docker is installed.

### 4. Test

This app runs on 8888 port. The host address must be ip address of Docker where it is installed.


_NOTE:_ I have commented [CampaignControllerTest](https://github.com/umtbrbr/spring-boot-postgresql-example/blob/master/src/test/java/com/campaign/demo/controller/CampaignContollerTest.java) out class. I'm getting unknown host exception when I build app with mvn clean install. The reason is that I set container name of postgres to application.properties as below. I could not find a solution so I had to commented it out for now.

    #spring.datasource.url=jdbc:postgresql://10.166.22.127:5432/postgres
    spring.datasource.url=jdbc:postgresql://postgres:5432/postgres
