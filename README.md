# spring-boot-postgresql-example

The aim of this experiment project are management of campaigns for admin panel and calculate discounts using the campaigns.

## API Overview

This project has 5 API as below.

| Path | Method | Body Params | Request Params | Response | Description
| --- | --- | --- | --- | --- | ---
| /api/v1/campaigns | GET | | | [Campaign](https://github.com/umtbrbr/spring-boot-postgresql-example/blob/master/src/main/java/com/campaign/demo/entity/Campaign.java) | Returns all campaigns without any filter
| /api/v1/campaigns | POST | [Campaign](https://github.com/umtbrbr/spring-boot-postgresql-example/blob/master/src/main/java/com/campaign/demo/entity/Campaign.java) | | [Campaign](https://github.com/umtbrbr/spring-boot-postgresql-example/blob/master/src/main/java/com/campaign/demo/entity/Campaign.java) | Create a new campaign with given campaign details
| /api/v1/campaigns | PUT | [Campaign](https://github.com/umtbrbr/spring-boot-postgresql-example/blob/master/src/main/java/com/campaign/demo/entity/Campaign.java) | id | [Campaign](https://github.com/umtbrbr/spring-boot-postgresql-example/blob/master/src/main/java/com/campaign/demo/entity/Campaign.java) | Find campaign with given id and update that campaign 
| /api/v1/campaigns | DELETE | | id | [Campaign](https://github.com/umtbrbr/spring-boot-postgresql-example/blob/master/src/main/java/com/campaign/demo/entity/Campaign.java) | Delete campaign with given campaign id
| /api/v1/campaigns/calculateDiscounts | POST | List<[BasketItem>](https://github.com/umtbrbr/spring-boot-postgresql-example/blob/master/src/main/java/com/campaign/demo/entity/BasketItem.java) | | List<[BasketItem>](https://github.com/umtbrbr/spring-boot-postgresql-example/blob/master/src/main/java/com/campaign/demo/entity/BasketItem.java) | Calculate discounts with using campaign of category or product

## Run

### 1. Build app

Create jar file with below maven command

    mvn clean install

### 2. Run app in docker container

Build and run a docker image with below docker-compose command

    docker-compose up -d --build
    
### 3. Create tables and insert data

This experiment project is using Postgres for data model. There are two sql files under `src/main/resources` directory. Schema and tables can create with [create_table.sql](https://github.com/umtbrbr/spring-boot-postgresql-example/blob/master/src/main/resources/create_table.sql) and initialize data can insert with [insert_data.sql](https://github.com/umtbrbr/spring-boot-postgresql-example/blob/master/src/main/resources/insert_data.sql)

### 4. Test

This app runs on 8888 port.
