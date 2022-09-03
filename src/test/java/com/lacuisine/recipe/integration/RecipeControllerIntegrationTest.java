package com.lacuisine.recipe.integration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.lacuisine.recipe.RecipeApplication;
import com.lacuisine.recipe.exceptions.RecipeException;
import com.lacuisine.recipe.model.Ingredients;
import com.lacuisine.recipe.model.PostIngredients;
import com.lacuisine.recipe.model.PostRecipe;
import com.lacuisine.recipe.model.Recipe;
import com.lacuisine.recipe.model.RecipeSearchCriteria;
import com.lacuisine.recipe.model.UpdateRecipe;
import com.lacuisine.recipe.rest.RecipeController;



@SpringBootTest(classes = RecipeApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class RecipeControllerIntegrationTest {

  @Autowired
  private RecipeController recipeController;



  @Test
  @Order(1)
  @DisplayName("RecipeControllerIntegrationTest - Test Add Recipe")
  void testAddRecipe() {

    List<PostIngredients> ingredients = new ArrayList<>();
    PostIngredients ingredientFish = new PostIngredients();
    ingredientFish.setIngredient("fish");
    PostIngredients ingredientOil = new PostIngredients();
    ingredientOil.setIngredient("oil");
    PostIngredients ingredientleaves = new PostIngredients();
    ingredientleaves.setIngredient("leaves");
    ingredients.add(ingredientFish);
    ingredients.add(ingredientOil);
    ingredients.add(ingredientleaves);


    PostRecipe recipe = new PostRecipe();
    recipe.setName("FishFry");
    recipe.setServingCapacity(6);
    recipe.setVegetarian(false);
    recipe.setIngredients(ingredients);
    recipe.setRecipeInstruction("oven");

    ResponseEntity<Void> responseEntity = recipeController.addRecipe(recipe);


    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
  }

  @Test
  @Order(2)
  @DisplayName("RecipeControllerIntegrationTest - Test Add One More Recipe")
  void testAddOneMoreRecipe() {

    List<PostIngredients> ingredients = new ArrayList<>();
    PostIngredients ingredientFish = new PostIngredients();
    ingredientFish.setIngredient("bread");
    PostIngredients ingredientOil = new PostIngredients();
    ingredientOil.setIngredient("cheese");
    PostIngredients ingredientleaves = new PostIngredients();
    ingredientleaves.setIngredient("olive");
    ingredients.add(ingredientFish);
    ingredients.add(ingredientOil);
    ingredients.add(ingredientleaves);

    PostRecipe recipe = new PostRecipe();
    recipe.setName("Pizza");
    recipe.setServingCapacity(4);
    recipe.setVegetarian(true);
    recipe.setRecipeInstruction("Oven");
    recipe.setIngredients(ingredients);

    ResponseEntity<Void> responseEntity = recipeController.addRecipe(recipe);


    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
  }

  @Test
  @Order(3)
  @DisplayName("RecipeControllerIntegrationTest - Test Get Recipe")
  void testGetRecipe() {

    ResponseEntity<List<Recipe>> responseEntity = recipeController.getAllRecipe();
    List<Recipe> recipeList = responseEntity.getBody();

    ResponseEntity<Recipe> getResponseEntity =
        recipeController.getRecipe(recipeList.get(0).getRecipeId());
    Recipe recipeActualResult = getResponseEntity.getBody();


    assertAll("Verify actual result",
        () -> assertEquals(recipeList.get(0).getName(), recipeActualResult.getName()),
        () -> assertEquals(recipeList.get(0).getVegetarian(), recipeActualResult.getVegetarian()),
        () -> assertEquals(recipeList.get(0).getRecipeId(), recipeActualResult.getRecipeId()));


  }


  @Test
  @Order(4)
  @DisplayName("RecipeControllerIntegrationTest - Test Update Recipe")
  void testUpdateRecipe() {


    ResponseEntity<List<Recipe>> recipeList = recipeController.getAllRecipe();
    Recipe recipeMatched = null;
    for (Recipe recipe : recipeList.getBody()) {

      if ("FishFry".equalsIgnoreCase(recipe.getName())) {
        recipeMatched = recipe;

      }

    }

    List<Ingredients> ingredients = new ArrayList<>();
    Ingredients ingredientIndianMasala = new Ingredients();
    ingredientIndianMasala.setIngredient("Indian Masala");
    ingredients.add(ingredientIndianMasala);

    UpdateRecipe updateRecipe = new UpdateRecipe();
    updateRecipe.setName("FishTava");
    updateRecipe.setServingCapacity(6);
    updateRecipe.setRecipeInstruction("fish tava");
    updateRecipe.setVegetarian(false);
    updateRecipe.setIngredients(ingredients);

    ResponseEntity<Void> responseEntity =
        recipeController.updateRecipe(recipeMatched.getRecipeId(), updateRecipe);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

  }


  @Test
  @Order(5)
  @DisplayName("RecipeControllerIntegrationTest - Test Get All Recipe")
  void testGetAllRecipe() {

    ResponseEntity<List<Recipe>> responseEntity = recipeController.getAllRecipe();

    List<Recipe> listRecipe = responseEntity.getBody();
    assertTrue(listRecipe.size() > 1);
  }


  @Test
  @Order(6)
  @DisplayName("RecipeControllerIntegrationTest - Test Serach Recipe with combination - capacity , vegeterian and instrcution")
  void testSerachRecipeVeg() {

    RecipeSearchCriteria recipeSearchCriteria = new RecipeSearchCriteria();
    recipeSearchCriteria.setServingCapacity(4);
    recipeSearchCriteria.setVegetarian(true);
    recipeSearchCriteria.setRecipeInstruction("Oven");


    ResponseEntity<List<Recipe>> responseEntity =
        recipeController.serachRecipe(recipeSearchCriteria);

    List<Recipe> searchedRecipeList = responseEntity.getBody();

    assertEquals(1, searchedRecipeList.size());

  }


  @Test
  @Order(7)
  @DisplayName("RecipeControllerIntegrationTest - Test Serach Recipe with combination - capacity , vegeterian")
  void testSerachRecipeNonVeg() {

    RecipeSearchCriteria recipeSearchCriteria = new RecipeSearchCriteria();
    recipeSearchCriteria.setServingCapacity(6);
    recipeSearchCriteria.setVegetarian(false);



    ResponseEntity<List<Recipe>> responseEntity =
        recipeController.serachRecipe(recipeSearchCriteria);

    List<Recipe> searchedRecipeList = responseEntity.getBody();

    assertEquals(1, searchedRecipeList.size());

  }


  @Test
  @Order(8)
  @DisplayName("RecipeControllerIntegrationTest - Test Serach Recipe with combination - capacity , vegeterian and Ingredeient")
  void testSerachRecipeNonVegWithIngredeient() {

    RecipeSearchCriteria recipeSearchCriteria = new RecipeSearchCriteria();
    recipeSearchCriteria.setServingCapacity(6);
    recipeSearchCriteria.setVegetarian(false);
    recipeSearchCriteria.ingredientIncluded("fish");



    ResponseEntity<List<Recipe>> responseEntity =
        recipeController.serachRecipe(recipeSearchCriteria);

    List<Recipe> searchedRecipeList = responseEntity.getBody();

    assertEquals(1, searchedRecipeList.size());

  }


  @Test
  @Order(9)
  @DisplayName("RecipeControllerIntegrationTest - Test Get Recipe with Error ")
  void testGetRecipe_DoesNotExist() {

    List<String> ingredients = new ArrayList<String>();
    ingredients.add("fish");
    ingredients.add("oil");
    ingredients.add("curry leaves");

    Recipe recipeExpected = new Recipe();
    recipeExpected.setRecipeId((long) 11);
    recipeExpected.setName("Test");
    recipeExpected.setServingCapacity(4);
    recipeExpected.setVegetarian(false);

    String expectedResult = "Recipe Id does not exist";
    String actualResult = "";
    try {
      ResponseEntity<Recipe> responseEntity =
          recipeController.getRecipe(recipeExpected.getRecipeId());
    } catch (RecipeException e) {
      actualResult = e.getMessage();
    }

    assertEquals(expectedResult, actualResult);

  }



  @Test
  @Order(10)
  @DisplayName("RecipeControllerIntegrationTest - Test Delete Recipe with Error ")
  void testDelete_DoesNotExist() {

    List<String> ingredients = new ArrayList<String>();
    ingredients.add("fish");
    ingredients.add("oil");
    ingredients.add("curry leaves");

    Recipe recipeExpected = new Recipe();
    recipeExpected.setRecipeId((long) 11);
    recipeExpected.setName("Test");
    recipeExpected.setServingCapacity(4);
    recipeExpected.setVegetarian(false);

    String expectedResult = "Recipe Id does not exist";
    String actualResult = "";
    try {
      ResponseEntity<Void> responseEntity =
          recipeController.deleteRecipe(recipeExpected.getRecipeId());
    } catch (RecipeException e) {
      actualResult = e.getMessage();
    }

    assertEquals(expectedResult, actualResult);

  }



}
