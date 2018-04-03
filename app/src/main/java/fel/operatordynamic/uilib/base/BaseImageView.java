package fel.operatordynamic.uilib.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.widget.ImageViewCompat;
import android.util.AttributeSet;
import android.widget.ImageView;


public abstract class BaseImageView extends ImageView implements IDragView {
    private float[] oriPos;
    private String index;
    private boolean isInit;
    private float[] size;

    public BaseImageView(Context context) {
        super(context);
    }

    public BaseImageView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public abstract int getFlag();

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
        return oriPos;
    }

    @Override
    public void setPos(float[] pos) {
        this.oriPos = pos;
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
}
