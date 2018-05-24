package test.ojig.Uitility;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.MediaController;


public class ScreenMediaController extends MediaController {

    private String isFullScreen;

    public ScreenMediaController(Context context) {
        super(context);
    }

    @Override
    public void setAnchorView(View view) {

        super.setAnchorView(view);

        //image button for full screen to be added to media controller

        LayoutParams params =
                new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        params.rightMargin = 80;

    }
}