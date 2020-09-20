package com.example.yummlyteam.app.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummlyteam.app.Util;
import com.example.yummlyteam.app.model.Match;
import com.example.yummlyteam.yummly_project.R;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeVievHolder> {

  private List<Match> recipeList;


  public RecipeListAdapter(List<Match> recipeList) {
    this.recipeList = recipeList;
  }

  @NonNull
  @Override
  public RecipeVievHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).
            inflate(R.layout.recipe_row, parent, false);
    return new RecipeVievHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull RecipeVievHolder recipeVievHolder, int position) {
    Match recipe = recipeList.get(position);
    recipeVievHolder.recipeName.setText(Util.recipeNameFormatter(recipe.getRecipeName()));
    recipeVievHolder.totalTime.setText(Util.timeFormatter(recipe.getTotalTimeInSeconds()));
    recipeVievHolder.totalCalories.setText("--");

    Picasso.with(recipeVievHolder.itemView.getContext())
            .load(recipe.getSmallImageUrls().get(0))
            .networkPolicy(
                    Util.isNetworkConnectionAvailable(recipeVievHolder.itemView.getContext()) ?
                            NetworkPolicy.NO_CACHE : NetworkPolicy.OFFLINE)
            .into(recipeVievHolder.recipeImageView);
  }

  @Override
  public int getItemCount() {
    return recipeList.size();
  }

  public boolean addItems(List<Match> newItems) {
    if (recipeList != null) {
      int preSize = recipeList.size();
      recipeList.addAll(newItems);
      notifyDataSetChanged();
      return true;
    }
    return false;
  }

  public void clearList() {
    if (recipeList != null) {
      recipeList.clear();
    }
    notifyDataSetChanged();
  }

  public static class RecipeVievHolder extends RecyclerView.ViewHolder {

    TextView ingredients, recipeName, totalCalories, totalTime, recipeBitternessIndicator;
    ImageView recipeImageView;


    public RecipeVievHolder(@NonNull View itemView) {
      super(itemView);
      recipeName = itemView.findViewById(R.id.recipeName);
      ingredients = itemView.findViewById(R.id.ingredients);
      totalCalories = itemView.findViewById(R.id.totalCalories);
      totalTime = itemView.findViewById(R.id.totalTime);
      recipeImageView = itemView.findViewById(R.id.recipeImageView);
      recipeBitternessIndicator = itemView.findViewById(R.id.bitter_label);
    }
  }
}
