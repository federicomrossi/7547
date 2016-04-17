package fiuba.ordertracker.helpers;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ezequiel on 09/04/16.
 */
public final class Fonts {

    public static void changeSearchViewTextColorBlack(View view) {
        if (view != null) {
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(Constants.COLOR_TEXT_FILTER);
                ((TextView) view).setHintTextColor(Constants.COLOR_HINT_FILTER);
                ((TextView) view).setCursorVisible(true);
                return;
            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    changeSearchViewTextColorBlack(viewGroup.getChildAt(i));
                }
            }
        }
    }

}
