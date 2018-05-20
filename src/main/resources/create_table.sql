DROP SCHEMA IF EXISTS demo CASCADE;
CREATE SCHEMA demo AUTHORIZATION postgres;

DROP TABLE IF EXISTS demo.category;

CREATE TABLE demo.category
(
  id   SERIAL            NOT NULL,
  name CHARACTER VARYING NOT NULL,
  PRIMARY KEY (id)
)
WITH (
OIDS = FALSE
);

ALTER TABLE demo.category
  OWNER TO postgres;

DROP TABLE IF EXISTS demo.product;

CREATE TABLE demo.product
(
  id         SERIAL            NOT NULL,
  name       CHARACTER VARYING NOT NULL,
  price      NUMERIC           NOT NULL,
  categoryId SERIAL            NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT categoryId_fk FOREIGN KEY (categoryId)
  REFERENCES demo.category (id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
);

ALTER TABLE demo.product
  OWNER TO postgres;

DROP TABLE IF EXISTS demo.campaign;

CREATE TABLE demo.campaign
(
  id                 SERIAL            NOT NULL,
  name               CHARACTER VARYING NOT NULL,
  campaign_type      CHARACTER VARYING NOT NULL,
  campaign_type_id   INTEGER           NOT NULL,
  campaign_type_name CHARACTER VARYING NOT NULL,
  discount_type      CHARACTER VARYING NOT NULL,
  discount           INTEGER           NOT NULL,
  max_discount       INTEGER,
  PRIMARY KEY (id)
)
WITH (
OIDS = FALSE
);

ALTER TABLE demo.campaign
  OWNER TO postgres;