package fel.operatordynamic.uilib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fel.operatordynamic.uilib.base.IDragView;

/**
 * Created by XSP on 2016/9/7.
 */
public class DragCharView extends RelativeLayout implements IDragView{
    private float[] pos;
    private String index;
    private boolean isInit;
    private float[] size;

    public DragCharView(Context context) {
        super(context);
    }

    public DragCharView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getFlag() {
        return TAG_TEXT;
    }

    @Override
    public String getIndex() {
        // TODO Auto-generated method stub
        return index;
    }

    @Override
    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public float[] getPos() {
        return pos;
    }

    @Override
    public void setPos(float[] pos) {
        this.pos = pos;
    }


    @Override
    public boolean isInit() {
        // TODO Auto-generated method stub
        return isInit;
    }

    @Override
    public void setInit(boolean isInit) {
        this.isInit = isInit;
    }

    @Override
    public float[] getSize() {
        // TODO Auto-generated method stub
        return size;
    }

    @Override
    public void setSize(float[] size) {
        this.size = size;
    }

    public CharSequence getText() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof TextView) {
                return ((TextView) child).getText();
            }
        }
        return null;
    }

    public void setText(CharSequence text) {
        if (text == null) {
            return;
        }
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof TextView) {
                ((TextView) child).setText(text);
            }
        }
    }

//    public Bitmap buildBitMap() {
//        TextView invisableTextView = (TextView)findViewById(R.id.invisable_textView);
//        invisableTextView.buildDrawingCache();
//        return invisableTextView.getDrawingCache();
//    }
//
//    public void initInvisableText(int parentHeight) {
//        if (parentHeight <= 0) {
//            return;
//        }
//        TextView invisableTextView = (TextView)findViewById(R.id.invisable_textView);
//        ViewGroup.LayoutParams params = invisableTextView.getLayoutParams();
//        double zoom = RecordConstant.VIDEO_HEIGHT * 1.00 / parentHeight;
//        if (params != null) {
//            params.width = RecordConstant.VIDEO_WIDTH;
//            params.height = (int)(getResources().getDimensionPixelSize(R.dimen.ksing_drag_text_height) * zoom);
//        }
//        invisableTextView.setLayoutParams(params);
//        invisableTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX , (float)(getResources().getDimensionPixelSize(R.dimen.ksing_drag_text_size) * zoom));
//    }

}
