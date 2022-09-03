DROP TABLE IF EXISTS RECIPE cascade;
DROP TABLE IF EXISTS INGREDIENTS cascade;

CREATE TABLE RECIPE (
  RECIPE_ID BIGINT AUTO_INCREMENT  PRIMARY KEY,
  NAME VARCHAR(250) NOT NULL,
  RECIPE_INSTRUCTION VARCHAR(250) NOT NULL,
  SERVING_CAPACITY INT DEFAULT NULL,
  VEGETARIAN BOOLEAN DEFAULT NULL
);


CREATE TABLE INGREDIENTS (
  ID BIGINT AUTO_INCREMENT  PRIMARY KEY,
  INGREDIENT VARCHAR(250) NOT NULL,
  RECIPE_ID BIGINT NOT NULL,
  FOREIGN KEY (RECIPE_ID) REFERENCES RECIPE(RECIPE_ID)
);
INSERT INTO RECIPE(NAME,RECIPE_INSTRUCTION, SERVING_CAPACITY, VEGETARIAN) values('chickentandoori', 'bbq',4,false);
INSERT INTO INGREDIENTS(INGREDIENT,RECIPE_ID) values('chicken',1);
INSERT INTO INGREDIENTS(INGREDIENT,RECIPE_ID) values('oil',1);
INSERT INTO INGREDIENTS(INGREDIENT,RECIPE_ID) values('salt',1);

INSERT INTO RECIPE(NAME,RECIPE_INSTRUCTION, SERVING_CAPACITY, VEGETARIAN) values('fishfry', 'oven',4,false);
INSERT INTO INGREDIENTS(INGREDIENT,RECIPE_ID) values('fish',2);
INSERT INTO INGREDIENTS(INGREDIENT,RECIPE_ID) values('oil',2);
INSERT INTO INGREDIENTS(INGREDIENT,RECIPE_ID) values('spices',2);



INSERT INTO RECIPE(NAME,RECIPE_INSTRUCTION, SERVING_CAPACITY, VEGETARIAN) values('pasta', 'oven',4,true);
INSERT INTO INGREDIENTS(INGREDIENT,RECIPE_ID) values('pasta',3);
INSERT INTO INGREDIENTS(INGREDIENT,RECIPE_ID) values('cheese',3);
INSERT INTO INGREDIENTS(INGREDIENT,RECIPE_ID) values('olive',3);

INSERT INTO RECIPE(NAME,RECIPE_INSTRUCTION, SERVING_CAPACITY, VEGETARIAN) values('pizza', 'oven',4,false);
INSERT INTO INGREDIENTS(INGREDIENT,RECIPE_ID) values('cheese',4);
INSERT INTO INGREDIENTS(INGREDIENT,RECIPE_ID) values('bread',4);
INSERT INTO INGREDIENTS(INGREDIENT,RECIPE_ID) values('chicken',4);




