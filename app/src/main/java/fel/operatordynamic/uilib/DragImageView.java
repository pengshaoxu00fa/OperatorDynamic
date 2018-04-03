package fel.operatordynamic.uilib;

import android.content.Context;
import android.util.AttributeSet;

import fel.operatordynamic.uilib.base.BaseImageView;
import fel.operatordynamic.uilib.base.IDragView;


/**
 * Created by XSP on 2016/8/26.
 */
public class DragImageView extends BaseImageView implements IDragView {

    public DragImageView(Context context) {
       super(context);
    }

    public DragImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public int getFlag() {
    	return TAG_DRAG;
    }
    
 

}
