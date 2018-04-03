package fel.operatordynamic.uilib.base;

import android.graphics.Bitmap;

/**
 * Created by XSP on 2016/8/29.
 */
public interface ICenterView extends IDragView{
    boolean isEdit();//标识是否在编辑状态
    void setIsEdit(boolean isEdit);//标识是否在编辑状态
    boolean isHasBackground();//标识是否有背景
    void setHasBackground(boolean has);

    float getDefRotation();

    float[] getCenterPos();
    void setCenterPos(float[] centerPos);

    Bitmap getBitmap();
    void setBitmap(Bitmap bitmap);

//    StoryChartlet getChartlet();
//    void setChartlet(StoryChartlet chartlet);

}
