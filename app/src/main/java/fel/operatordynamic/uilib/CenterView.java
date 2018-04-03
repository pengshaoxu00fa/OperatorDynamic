package fel.operatordynamic.uilib;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import fel.operatordynamic.uilib.base.ICenterView;


/**
 * Created by XSP on 2016/8/26.
 */
public class CenterView extends RelativeLayout implements ICenterView {
    private float[] oriPos;
    private String index;
    private boolean isInit;
    private float[] size;
    private float[] centerPos;
    private boolean isEdit = true;
    private boolean isHasBackground = true;
    private Bitmap bitmap;
//    private StoryChartlet chartlet;

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

//    @Override
//    public StoryChartlet getChartlet() {
//        return chartlet;
//    }
//
//    @Override
//    public void setChartlet(StoryChartlet chartlet) {
//        this.chartlet = chartlet;
//    }

    public boolean isHasBackground() {
        return isHasBackground;
    }
    public void setHasBackground(boolean has) {
        this.isHasBackground = has;
    }
    @Override
    public boolean isEdit() {
        return isEdit;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public CenterView(Context context) {
        super(context);
    }

    public CenterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }




    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        int mCount = getChildCount();
        for (int i = 0; i < mCount; i++) {
            View child = getChildAt(i);
            float size[] = getSize();
            int pl = getPaddingLeft();
            int pt= getPaddingTop();
            int pr = getPaddingRight();
            int pb = getPaddingBottom();
            int padding = Math.max(pb, Math.max(pr, Math.max(pl, pt)));//只能设置固定padding, 不然不好计算角度
            setPadding(padding,padding,padding,padding);
            if (size != null) {
                child.layout(0 + padding,0 + padding,(int)(size[0]) - padding,(int)(size[1]) - padding);
            }
        }

    }
//    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    
    public int getFlag() {
    	return TAG_CENTRE;
    }
    
   

    
    public float[] getCenterPos() {
		return centerPos;
	}
    public void setCenterPos(float[] centerPos) {
		this.centerPos = centerPos;
	}
    
    private float defRotation = -1;
    public float getDefRotation() {
    	if (defRotation <= 0) {
        	float x = getWidth();
        	float y = getHeight();
        	double l = Math.sqrt(x*x + y*y);
    		defRotation = (float)Math.toDegrees(Math.acos(x / l));
    	}
    	return defRotation;
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
