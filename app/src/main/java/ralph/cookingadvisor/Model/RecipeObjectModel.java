package ralph.cookingadvisor.Model;

/**
 * Created by rafaelchris on 10/11/17.
 */

public class RecipeObjectModel {
    String  recipe;
    String ingredient;


    public RecipeObjectModel(String ingredient, String recipe){

        this.ingredient=ingredient;
        this.recipe = recipe;

    }
    public String getIngredient()
    {
        return ingredient;
    }

    public  String getRecipe()
    {
        return recipe;
    }




}
