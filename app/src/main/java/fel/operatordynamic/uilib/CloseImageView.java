package fel.operatordynamic.uilib;

import android.content.Context;
import android.util.AttributeSet;

import fel.operatordynamic.uilib.base.BaseImageView;

/**
 * Created by XSP on 2016/8/26.
 */
public class CloseImageView extends BaseImageView {


    public CloseImageView(Context context) {
       super(context);
    }

    public CloseImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    public int getFlag() {
    	return TAG_CLOSE;
    }
   
}
