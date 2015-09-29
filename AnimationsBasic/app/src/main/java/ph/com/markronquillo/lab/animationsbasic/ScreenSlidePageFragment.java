package ph.com.markronquillo.lab.animationsbasic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by markronquillo on 24/09/15.
 */
public class ScreenSlidePageFragment extends Fragment {

    private View layoutContainer;

    private int bgColor = Color.GRAY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_colored_slide_page, container, false);
        layoutContainer= rootView.findViewById(R.id.container);
        layoutContainer.setBackgroundColor(bgColor);
        return rootView;
    }

    public void setBgColor(int bgColor)
    {
        this.bgColor = bgColor;
    }

}
