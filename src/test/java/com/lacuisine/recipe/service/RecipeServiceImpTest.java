package com.lacuisine.recipe.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.lacuisine.recipe.entity.RecipeEntity;
import com.lacuisine.recipe.model.PostIngredients;
import com.lacuisine.recipe.model.PostRecipe;
import com.lacuisine.recipe.model.Recipe;
import com.lacuisine.recipe.model.RecipeSearchCriteria;
import com.lacuisine.recipe.model.UpdateRecipe;
import com.lacuisine.recipe.repository.RecipeRepository;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImpTest {

  @InjectMocks
  private RecipeServiceImp recipeService;

  @Mock
  private RecipeRepository recipeRepository;

  @Test
  @DisplayName("RecipeService - Test Process Add Recipe Service")
  final void testProcessAddRecipe() {

    List<PostIngredients> ingredients = new ArrayList<>();
    PostIngredients ingredientFish = new PostIngredients();
    ingredientFish.setIngredient("pasta");
    PostIngredients ingredientOil = new PostIngredients();
    ingredientOil.setIngredient("oil");
    PostIngredients ingredientleaves = new PostIngredients();
    ingredientleaves.setIngredient("leaves");
    ingredients.add(ingredientFish);
    ingredients.add(ingredientOil);
    ingredients.add(ingredientleaves);


    PostRecipe recipe = new PostRecipe();
    recipe.setName("Pasta");
    recipe.setServingCapacity(4);
    recipe.setVegetarian(true);
    recipe.setIngredients(ingredients);

    when(recipeRepository.save(any())).thenReturn(any());

    recipeService.processAddRecipe(recipe);

    Mockito.verify(recipeRepository, Mockito.times(1)).save(any());
  }



  @Test
  @DisplayName("RecipeService - Test Process Delete Recipe Service")
  final void testProcessDeleteRecipe() {

    Recipe recipe = new Recipe();
    recipe.setRecipeId((long) 12);
    recipe.setName("Pasta");
    recipe.setServingCapacity(4);
    recipe.setVegetarian(true);
    recipe.setIngredients(null);

    RecipeEntity recipeEntity = new RecipeEntity();
    recipeEntity.setRecipeId((long) 12);
    recipeEntity.setName("Pasta");
    recipeEntity.setServingCapacity(4);
    recipeEntity.setVegetarian(true);

    Optional<RecipeEntity> entity = Optional.of(recipeEntity);

    UpdateRecipe updateRecipe = new UpdateRecipe();
    updateRecipe.setName("Pizza");
    updateRecipe.setServingCapacity(6);
    updateRecipe.setVegetarian(false);

    when(recipeRepository.findById(any())).thenReturn(entity);

    recipeService.processDeleteRecipe(recipe.getRecipeId());

    Mockito.verify(recipeRepository, Mockito.times(1)).deleteById(any());
  }


  @Test
  @DisplayName("RecipeService - Test Process Update Recipe Service")
  final void testProcessUpdateRecipe() {

    Recipe recipe = new Recipe();
    recipe.setRecipeId((long) 12);
    recipe.setName("Pasta");
    recipe.setServingCapacity(4);
    recipe.setVegetarian(true);



    RecipeEntity recipeEntity = new RecipeEntity();
    recipeEntity.setRecipeId((long) 12);
    recipeEntity.setName("Pasta");
    recipeEntity.setServingCapacity(4);
    recipeEntity.setVegetarian(true);


    Optional<RecipeEntity> entity = Optional.of(recipeEntity);

    UpdateRecipe updateRecipe = new UpdateRecipe();
    updateRecipe.setName("Pizza");
    updateRecipe.setServingCapacity(6);
    updateRecipe.setVegetarian(false);


    when(recipeRepository.findById(any())).thenReturn(entity);

    recipeService.processUpdateRecipe(recipe.getRecipeId(), updateRecipe);

    Mockito.verify(recipeRepository, Mockito.times(1)).save(any());
  }


  @Test
  @DisplayName("RecipeService - Test Process Get Recipe Service")
  final void testProcessGetRecipe() {

    Recipe expectedResult = new Recipe();
    expectedResult.setRecipeId((long) 12);
    expectedResult.setName("Pasta");
    expectedResult.setServingCapacity(4);
    expectedResult.setVegetarian(true);


    RecipeEntity recipeEntity = new RecipeEntity();
    recipeEntity.setRecipeId((long) 12);
    recipeEntity.setName("Pasta");
    recipeEntity.setServingCapacity(4);
    recipeEntity.setVegetarian(true);


    Optional<RecipeEntity> entity = Optional.of(recipeEntity);

    when(recipeRepository.findById(any())).thenReturn(entity);

    Recipe actualResult = recipeService.processGetRecipe(expectedResult.getRecipeId());

    assertAll("Verify actual result", () -> assertEquals("Pasta", actualResult.getName()),
        () -> assertEquals(true, actualResult.getVegetarian()),
        () -> assertEquals(4, actualResult.getServingCapacity()),
        () -> assertEquals((long) 12, actualResult.getRecipeId()));


  }


  @Test
  @DisplayName("RecipeService - Test Process Search Recipe Service")
  final void testProcessSearchRecipe() {

    RecipeSearchCriteria recipeSearchCriteria = new RecipeSearchCriteria();
    recipeSearchCriteria.setRecipeInstruction("oven");
    recipeSearchCriteria.setServingCapacity(4);
    recipeSearchCriteria.setVegetarian(true);


    Recipe expectedResult = new Recipe();
    expectedResult.setRecipeId((long) 12);
    expectedResult.setName("Pasta");
    expectedResult.setServingCapacity(4);
    expectedResult.setVegetarian(true);



    RecipeEntity recipeEntity = new RecipeEntity();
    recipeEntity.setRecipeId((long) 12);
    recipeEntity.setName("Pasta");
    recipeEntity.setServingCapacity(4);
    recipeEntity.setVegetarian(true);

    List<RecipeEntity> recipeEntityLst = new ArrayList<RecipeEntity>();
    recipeEntityLst.add(recipeEntity);


    when(recipeRepository.findByRecipeSerachCriteria(any())).thenReturn(recipeEntityLst);

    List<Recipe> actualResultList = recipeService.processSearchRecipe(recipeSearchCriteria);

    assertAll("Verify actual result", () -> assertEquals(1, actualResultList.size()));


  }

}
