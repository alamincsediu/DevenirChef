package antitelegram.devenirchef;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class CookActivity extends FragmentActivity {


    private int numSteps;
    private ViewPager allStepsViewPager;
    private PagerAdapter pagerAdapter;
    private Intent fromIntent;
    private Recipe recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);


        setUpViewPager();
        setUpRecipe();

        // to update count info
        setNewDatasetSize();

    }

    private void setNewDatasetSize() {
        numSteps = recipe.getCookingSteps().size();
        // adding last "finish" screen
        numSteps += 1;
        pagerAdapter.notifyDataSetChanged();
    }

    private void setUpRecipe() {
        fromIntent = getIntent();
        recipe = getRecipe();

    }

    private void setUpViewPager() {
        allStepsViewPager = findViewById(R.id.view_pager);
        pagerAdapter = new CookingPagerAdapter(getSupportFragmentManager());
        allStepsViewPager.setAdapter(pagerAdapter);
    }


    private Recipe getRecipe() {
        return fromIntent.getExtras().getParcelable("recipe");
    }


    @Override
    public void onBackPressed() {
        if (allStepsViewPager.getCurrentItem() == 0)
            super.onBackPressed();
        else {
            allStepsViewPager.setCurrentItem(allStepsViewPager.getCurrentItem() - 1);
        }
    }


    private class CookingPagerAdapter extends FragmentStatePagerAdapter {

        private static final String TAG = "daywint";

        public CookingPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            if (position == numSteps - 1) {
                return new FinishRecipeStepFragment();
            }

            return createCookingStepFragment(position);
        }

        @NonNull
        private Fragment createCookingStepFragment(int position) {
            RecipeCookingStepFragment cookingStep = new RecipeCookingStepFragment();
            RecipeStep recipeStepData = recipe.getCookingSteps().get(position);
            cookingStep.setRecipeStep(recipeStepData);

            return cookingStep;
        }


        @Override
        public int getCount() {
            return numSteps;
        }


    }

}
