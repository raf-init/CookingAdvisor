package ralph.cookingadvisor.Adapters;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ralph.cookingadvisor.Model.RecipeObjectModel;
import ralph.cookingadvisor.R;

/**
 * Created by rafaelchris on 10/11/17.
 */

public class DatabaseAdapter extends RecyclerView.Adapter<DatabaseAdapter.MyViewHolder> {

    private ArrayList<RecipeObjectModel> dataSet;
    Boolean check=false;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ingredient,recipe;
        Button bt;

        RelativeLayout expandable;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.expandable= (RelativeLayout)itemView.findViewById(R.id.expandableLayout);
            this.ingredient= (TextView)itemView.findViewById(R.id.ingredient_text);
            this.recipe = (TextView) itemView.findViewById(R.id.recipe_text);
            this.bt = (Button) itemView.findViewById(R.id.share_button);

        }
    }

    public DatabaseAdapter(ArrayList<RecipeObjectModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_roll, parent, false);

        final MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!check)
                {
                    myViewHolder.expandable.animate()
                            .alpha(0.0f)
                            .setDuration(1000);


                    myViewHolder.expandable.setVisibility(View.GONE);
                    check=true;
                    //  myViewHolder.schedule.setVisibility(View.VISIBLE);

                }
                else {
                    myViewHolder.expandable.setVisibility(View.VISIBLE);
                    myViewHolder.expandable.animate()
                            .alpha(1.0f)
                            .setDuration(1000);

                    check=false;

                }
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final DatabaseAdapter.MyViewHolder holder, int position) {
        final TextView ingredient1= holder.ingredient;
        final TextView recipe1 = holder.recipe;


        ingredient1.setText(dataSet.get(position).getIngredient());
        recipe1.setText(dataSet.get(position).getRecipe());

        holder.bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String text;
                String title;


                ClipData myClip2 = ClipData.newPlainText("text", recipe1.getText().toString());
                ClipData myClip = ClipData.newPlainText("text", ingredient1.getText().toString());


                title = myClip.getItemAt(0).getText().toString();
                text = myClip2.getItemAt(0).getText().toString();


                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareSub = title;
                String shareBody = "Recipe: " + title + "\n" + "Ingredients/Steps: " +  text + "\n" + "via CookingAdvisor app";
                intent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                v.getContext().startActivity(Intent.createChooser(intent,"Share using"));

            }
        });


    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
