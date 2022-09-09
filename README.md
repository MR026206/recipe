# RECIPE-MANAGER

## Objective

Create a standalone java application which allows users to manage their favourite recipes. It should allow adding, updating, removing and fetching recipes. Additionally users should be able to filter available recipes based on one or more of the following criteria:
1. Whether or not the dish is vegetarian
2. The number of servings
3. Specific ingredients (either include or exclude)
4. Text search within the instructions.
For example, the API should be able to handle the following search requests:
• All vegetarian recipes
• Recipes that can serve 4 persons and have “potatoes” as an ingredient
• Recipes without “salmon” as an ingredient that has “oven” in the instructions.

## Database

It uses a `H2 in-memory` database sqlite database, can be changed easily in the application.yml for any other database.
data.sql has initial data.

## Requirements

For building and running the application you need:

`JDK 11`

`Maven 3`

## Running locally

`git clone git@github.com:MR026206/recipe.git`


Alternatively you can use the Spring Boot Maven plugin like so:

`mvn clean install`

`mvn spring-boot:run`

then you can open swagger-ui in your browser:

http://localhost:8081/swagger-ui.html

and explore h2-database:

http://localhost:8081/h2-ui/

## Try it out with Docker

You'll need Docker installed.

`docker compose up -d`

then you can open swagger-ui in your browser:

http://localhost:8080/swagger-ui.html

## Search Api

API Endpoint: http://localhost:8080/api/v1/recipes/search

search receipe works with combination of four value as mentioned below in the example:


Payload Example for `vegetarian = 'true' and servingCapacity = 4 `:

```
{
  "vegetarian": true,
  "servingCapacity": 4,
  "ingredientIncluded": "string",
  "recipeInstruction": "string"
}
```

Payload Example for `vegetarian = 'true' and servingCapacity = 4 and recipeInstruction='oven'`:

```
{
  "vegetarian": true,
  "servingCapacity": 4,
  "ingredientIncluded": "string",
  "recipeInstruction": "oven"
}
```

Payload Example for `vegetarian = 'true' and servingCapacity = 4 and recipeInstruction='oven' and ingredientIncluded= 'çheese'`:

```
{
  "vegetarian": true,
  "servingCapacity": 4,
  "ingredientIncluded": "cheese",
  "recipeInstruction": "oven"
}
```

## Improvement

- In future, we can include user api to manage their favourite recipe.

- No Authentication at this moment. we can improve

- search can improve with additional filter  

