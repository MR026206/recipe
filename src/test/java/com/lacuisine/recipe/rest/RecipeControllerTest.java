package com.lacuisine.recipe.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lacuisine.recipe.model.PostRecipe;
import com.lacuisine.recipe.model.Recipe;
import com.lacuisine.recipe.model.RecipeSearchCriteria;
import com.lacuisine.recipe.model.UpdateRecipe;
import com.lacuisine.recipe.service.RecipeServiceImp;


@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {


  @Autowired
  private MockMvc mockMvc;

  @InjectMocks
  private RecipeController recipeController;

  @Mock
  private RecipeServiceImp recipeService;

  Recipe recipe = null;

  @BeforeEach
  void beforeEach() {
    this.recipe = new Recipe();
    recipe.setRecipeId((long) 12);
    recipe.setName("Pasta");
    recipe.setServingCapacity(4);
    recipe.setVegetarian(true);
    recipe.setIngredients(null);
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

  }

  @Test
  @DisplayName("RecipeController - Test Add Recipe")
  final void testAddRecipe() {

    PostRecipe recipe = new PostRecipe();
    recipe.setName("Pasta");
    recipe.setServingCapacity(4);
    recipe.setVegetarian(true);
    recipe.setIngredients(null);

    doNothing().when(recipeService).processAddRecipe(any());

    recipeController.addRecipe(recipe);

    Mockito.verify(recipeService, Mockito.times(1)).processAddRecipe(any());
  }


  @Test
  @DisplayName("RecipeController - Test Delete Recipe")
  final void testDeleteRecipe() {

    doNothing().when(recipeService).processDeleteRecipe(anyLong());

    recipeController.deleteRecipe(recipe.getRecipeId());

    Mockito.verify(recipeService, Mockito.times(1)).processDeleteRecipe(anyLong());
  }

  private long anyLong() {
    return 12;
  }

  @Test
  @DisplayName("RecipeController - Test Get Recipe")
  final void testGetRecipe() {

    when(recipeService.processGetRecipe(anyLong())).thenReturn(any());

    recipeController.getRecipe(recipe.getRecipeId());

    Mockito.verify(recipeService, Mockito.times(1)).processGetRecipe(anyLong());
  }


  @Test
  @DisplayName("RecipeController - Test Update Recipe")
  final void testUpdateRecipe() {

    UpdateRecipe updateRecipe = new UpdateRecipe();
    updateRecipe.setName("Pasta");
    updateRecipe.setServingCapacity(4);
    updateRecipe.setVegetarian(true);
    updateRecipe.setIngredients(null);

    doNothing().when(recipeService).processUpdateRecipe(any(), any());

    recipeController.updateRecipe(recipe.getRecipeId(), updateRecipe);

    Mockito.verify(recipeService, Mockito.times(1)).processUpdateRecipe(any(), any());
  }

  @Test
  @DisplayName("RecipeController - Test Search Recipe")
  final void testSearchRecipe() {

    RecipeSearchCriteria recipeSearchCriteria = new RecipeSearchCriteria();
    recipeSearchCriteria.setIngredientIncluded("cheese");
    recipeSearchCriteria.setVegetarian(true);

    when(recipeService.processSearchRecipe(any())).thenReturn(any());

    recipeController.serachRecipe(recipeSearchCriteria);

    Mockito.verify(recipeService, Mockito.times(1)).processSearchRecipe(any());
  }


  @Test
  @DisplayName("RecipeController - Test Delete Recipe EndPoint - Result Ok")
  public void testDeleteRecipeEndpointReturnOk() throws Exception {

    mockMvc.perform(delete("/api/v1/recipes/12").contentType(MediaType.APPLICATION_JSON)
        .accept("application/json")).andExpect(status().isOk());

  }

  @Test
  @DisplayName("RecipeController - Test Get Recipe EndPoint - Result Ok")
  public void testGetRecipeEndpointReturnOk() throws Exception {

    mockMvc.perform(get("/api/v1/recipes/12").contentType(MediaType.APPLICATION_JSON)
        .accept("application/json")).andExpect(status().isOk());

  }

  @Test
  @DisplayName("RecipeController - Test Update Recipe EndPoint - Result Ok")
  public void testUpdateRecipeEndpointReturnOk() throws Exception {

    UpdateRecipe updateRecipe = new UpdateRecipe();
    updateRecipe.setName("Pasta");
    updateRecipe.setServingCapacity(4);
    updateRecipe.setVegetarian(true);
    updateRecipe.setIngredients(null);


    mockMvc
        .perform(put("/api/v1/recipes/12").content(asJsonString(updateRecipe))
            .contentType(MediaType.APPLICATION_JSON).accept("application/json"))
        .andExpect(status().isOk());

  }

  @Test
  @DisplayName("RecipeController - Test Add Recipe EndPoint - Result Ok")
  public void testAddRecipeEndpointReturnOk() throws Exception {

    mockMvc.perform(
        post("/api/v1/recipes").contentType(MediaType.APPLICATION_JSON).accept("application/json"))
        .andExpect(status().isCreated());

  }


  @Test
  @DisplayName("RecipeController - Test Search Recipe EndPoint - Result Ok")
  public void testSearchRecipeEndpointReturnOk() throws Exception {

    RecipeSearchCriteria recipeSearchCriteria = new RecipeSearchCriteria();
    recipeSearchCriteria.setIngredientIncluded("cheese");
    recipeSearchCriteria.setVegetarian(true);

    mockMvc
        .perform(post("/api/v1/recipes/search").content(asJsonString(recipeSearchCriteria))
            .contentType(MediaType.APPLICATION_JSON).accept("application/json"))
        .andExpect(status().isOk());

  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


}
