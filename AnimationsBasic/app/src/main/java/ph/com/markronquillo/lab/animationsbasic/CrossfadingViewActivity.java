package ph.com.markronquillo.lab.animationsbasic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class CrossfadingViewActivity extends Activity {
    private View mContentView;
    private View mLoadingView;
    private int mShortAnimationDuration;

    public CrossfadingViewActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crossfading_view);

        // reference the views
        mContentView = findViewById(R.id.content);
        mLoadingView = findViewById(R.id.loading_spinner);

        // hide the content view
        mContentView.setVisibility(View.GONE);

        // cache the value of short animation time
        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crossfading_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            crossFade();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void crossFade() {

        View showView;
        final View hideView;

        if (mContentView.getVisibility() == View.GONE) {
            showView = mContentView;
            hideView = mLoadingView;
        } else {
            hideView = mContentView;
            showView = mLoadingView;
        }

        // Set the content view to 0% opacity but visible, so that it is visible
        // but fully transparent during the animation
        showView.setAlpha(0f);
        showView.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listerner set on the view
        showView.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(null);

        hideView.animate()
                .alpha(0f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        hideView.setVisibility(View.GONE);
                    }
                });
    }
}
