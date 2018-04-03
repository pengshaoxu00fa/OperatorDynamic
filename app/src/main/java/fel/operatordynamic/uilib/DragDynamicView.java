package fel.operatordynamic.uilib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fel.operatordynamic.R;
import fel.operatordynamic.uilib.base.ICenterView;
import fel.operatordynamic.uilib.base.IDragView;
import fel.operatordynamic.util.RecordUtil;


/**
 * Created by XSP on 2016/8/26.
 */
public class DragDynamicView extends RelativeLayout {
	public interface OnOutSideClickListener {
		void onClick(View view);
	}

    private HashMap<String, IDragView> mDragView =new HashMap<String,IDragView>();
    private HashMap<String, IDragView> mCloseView = new HashMap<String,IDragView>();
    private HashMap<String, ICenterView> mCenterView = new HashMap<String, ICenterView>();
    private List<IDragView> mAllView = new ArrayList<IDragView>();
	private IDragView mDragText;

	private int mTouchSlop;
    private float[] POSITION = new float[2];//按下的位置0>x  1>y
    private IDragView curPutView;
	private OnOutSideClickListener onOutSideClickListener;
	public int LEVELS = 1;

	public List<IDragView> getAllViews() {
		return mAllView;
	}

	public ICenterView getCenterViewByIndex(String index) {
		if (TextUtils.isEmpty(index)) {
			return null;
		}
		return mCenterView.get(index);
	}

	public void setOnOutSideClickListener(OnOutSideClickListener onOutSideClickListener) {
		this.onOutSideClickListener = onOutSideClickListener;
	}

	public DragDynamicView(Context context) {
		super(context);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}
    public DragDynamicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
		this.setOnClickListener(null);
    	switch(event.getAction()) {
    	case MotionEvent.ACTION_DOWN:
			if (computeIntercept()) {
				return true;
			} else {
				return super.onTouchEvent(event);
			}
    	case MotionEvent.ACTION_MOVE:
			if (computeMove(event)) {
				return true;
			} else {
				return super.onTouchEvent(event);
			}
    	case MotionEvent.ACTION_UP:
    	case MotionEvent.ACTION_CANCEL:
			if (!computeClick(event)) {
				if(computeMove(event)) {
					curPutView = null;
					return true;
				} else {
					curPutView = null;
					return super.onTouchEvent(event);
				}
			}

		default:
			return super.onTouchEvent(event);
    	}
    	
	}



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
    	switch(ev.getAction()) {
	    	case MotionEvent.ACTION_DOWN:
				POSITION[0] = ev.getX();
				POSITION[1] = ev.getY();
	    		return computeIntercept();
	    	case MotionEvent.ACTION_MOVE:
				if (curPutView != null
						&& (curPutView.getFlag() == IDragView.TAG_CENTRE || curPutView.getFlag() == IDragView.TAG_TEXT)
						&& (Math.abs(ev.getX() - POSITION[0]) >= mTouchSlop || Math.abs(ev.getY() - POSITION[1]) >= mTouchSlop)) {
					return true;
				} else {
					return false;
				}
	    	case MotionEvent.ACTION_UP:
	    	case MotionEvent.ACTION_CANCEL:
	    		break;

    	}
    	return super.onInterceptTouchEvent(ev);
    }

	private boolean isVisiable(IDragView view) {
		if (view == null) {
			return false;
		}
		View child = (View)view;
		if (child.getVisibility() == View.VISIBLE) {
			return true;
		} else {
			return false;
		}
	}
    
    //获取此位置的view
    private boolean computeIntercept() {
    	IDragView view = getViewByLocation();
    	if (view == null) {
			curPutView = null;
        	return true;
    	} else {
    		curPutView = view;
    		switch(view.getFlag()) {
			case IDragView.TAG_TEXT:
    		case IDragView.TAG_CENTRE:
				return false;
    		case IDragView.TAG_DRAG:
    			return true;
    		case IDragView.TAG_CLOSE:
    			return false;
    		default :
    			return false;
    		}
    	}
    }

	private boolean computeClick(MotionEvent ev) {
		if (curPutView == null && (Math.abs(ev.getX() - POSITION[0]) <= mTouchSlop && Math.abs(ev.getY() - POSITION[1]) <= mTouchSlop)) {
			//认为点击了
			if (onOutSideClickListener != null) {
				onOutSideClickListener.onClick(this);
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
    //计算 move
    private boolean computeMove(MotionEvent ev) {
    	if (curPutView == null) {
        	return false;
    	} else {
    		switch(curPutView.getFlag()) {
			case IDragView.TAG_TEXT:
				//更新UI
				computeTextPos(ev.getY());
				POSITION[0] = ev.getX();
				POSITION[1] = ev.getY();
				return true;
    		case IDragView.TAG_CENTRE:
				//更新UI
				computeMoveControlPos(ev.getX(), ev.getY());
				POSITION[0] = ev.getX();
				POSITION[1] = ev.getY();
    			return true;
    		case IDragView.TAG_DRAG:
				// 更新UI
				computeSizeAndRetation(ev.getX(), ev.getY());
				POSITION[0] = ev.getX();
				POSITION[1] = ev.getY();
    			return true;
    		case IDragView.TAG_CLOSE:
    			return false;
    		default :
    			return false;
    		}
    	}
    	
    }
    
    private void computeTextPos(float posY) {
		if (curPutView == mDragText && mDragText != null) {
			float[] pos = mDragText.getPos();
			pos[1] = pos[1] + (posY - POSITION[1]);
			pos[3] = pos[3] + (posY - POSITION[1]);
			mDragText.setPos(pos);
			((View)mDragText).layout((int) pos[0], (int) pos[1], (int) pos[2], (int) pos[3]);
		}
	}
    
    //计算中心View的 大小和旋转角度
    private void computeSizeAndRetation(float px, float py) {
    	ICenterView centerView = mCenterView.get(curPutView.getIndex());
		if (centerView == null) {
			return;
		}
    	float[] centerPointPos = centerView.getCenterPos();
    	double retation = 0;
    	float x = Math.abs(px - centerPointPos[0]);
    	float y = Math.abs(py - centerPointPos[1]);
    	double l = Math.sqrt(x*x + y*y);
    	if (l == 0) {
    		return;
    	}
    	double cos = x / l;
    	if (cos > 1.0) {
    		cos = 1.00;
    	}
    	if (cos < 0.0) {
    		cos = 0.00;
    	}
    	if(px == centerPointPos[0] && py > centerPointPos[1]) {
    		retation = 90;
    	}
    	if(px == centerPointPos[0] && py < centerPointPos[1]) {
    		retation = 270;
    	}
    	if (px > centerPointPos[0] && py == centerPointPos[1]) {
    		retation = 0;
    	}
    	if (px <= centerPointPos[0] && py == centerPointPos[1]) {
    		retation = 180;
    	}
    	if(px > centerPointPos[0] && py > centerPointPos[1]) {//第一象限
    		retation = Math.toDegrees(Math.acos(cos));
    	}
    	if(px < centerPointPos[0] && py > centerPointPos[1]) {//第二象限
    		retation = 180 - Math.toDegrees(Math.acos(cos));
    	}
    	if(px < centerPointPos[0] && py < centerPointPos[1]) {//第三象限
    		retation = 180 + Math.toDegrees(Math.acos(cos));
    	}
    	if(px > centerPointPos[0] && py < centerPointPos[1]) {//第四象限
    		retation = 360 - Math.toDegrees(Math.acos(cos));
    	}
    	float[] pos = centerView.getPos();
    	double orgx = (pos[2] - pos[0]) / 2;
    	double orgy = (pos[3] - pos[1]) / 2;
    	double orgl = (double)Math.sqrt(orgx*orgx + orgy*orgy);
    	double width = (pos[2] - pos[0]) * l / orgl;
    	double height = (pos[3] - pos[1]) * l / orgl;
    	pos[0] = (float)(centerPointPos[0] - width/2);
    	pos[1] = (float)(centerPointPos[1] - height/2);
    	pos[2] = (float)(centerPointPos[0] + width/2);
    	pos[3] = (float)(centerPointPos[1] + height/2);
    	centerView.setPos(pos);
		float[] size = centerView.getSize();
		size[0] = (float)width;
		size[1] = (float)height;
		centerView.setSize(size);
		((View)centerView).setRotation((float) (retation - centerView.getDefRotation()));
		((View)centerView).layout((int) (pos[0]), (int) (pos[1]), (int) (pos[2]), (int) (pos[3]));

		adjustControlPos(centerView.getDefRotation(), ((View) centerView).getRotation(), width, height, centerPointPos);
    	
    }

	//校验点击点是否还在旋转后的View内
	private boolean checkTouchPointInCenter(ICenterView centerView) {
		float[] centerPos = centerView.getCenterPos();
		float[] size = centerView.getSize();
		float vr = ((View)centerView).getRotation();

		float offsetX = Math.abs(POSITION[0] - centerPos[0]);
		float offsetY = Math.abs(POSITION[1] - centerPos[1]);
		double defRotation = 0;
		double len = Math.sqrt(offsetX * offsetX + offsetY * offsetY);
		if (POSITION[0] > centerPos[0] && POSITION[1] >= centerPos[1]) {//1
			defRotation = Math.toDegrees(Math.acos(offsetX / len));
		} else if (POSITION[0] <= centerPos[0] && POSITION[1] >= centerPos[1]) {//2
			defRotation = 180 - Math.toDegrees(Math.acos(offsetX / len));
		} else if (POSITION[0] < centerPos[0] && POSITION[1] < centerPos[1]) { //3
			defRotation = 180 + Math.toDegrees(Math.acos(offsetX / len));
		} else {//4
			defRotation = 360 - Math.toDegrees(Math.acos(offsetX / len));
		}
		double rotation = defRotation - vr;
		rotation = rotation % 360;
		if (rotation < 0) {
			rotation = 360 + rotation;
		}
		double x;
		double y;
		if (rotation > 0 && rotation <= 90) {
			x = centerPos[0] + len * Math.cos(RecordUtil.getAngleByDegrees(rotation));
			y = centerPos[1] + len * Math.sin(RecordUtil.getAngleByDegrees(rotation));
		} else if (rotation > 90 && rotation <= 180) {
			x = centerPos[0] - len * Math.cos(RecordUtil.getAngleByDegrees(180 - rotation));
			y = centerPos[1] + len * Math.sin(RecordUtil.getAngleByDegrees(180 - rotation));
		} else if (rotation > 180 && rotation <= 270) {
			x = centerPos[0] - len * Math.cos(RecordUtil.getAngleByDegrees(rotation - 180));
			y = centerPos[1] - len * Math.sin(RecordUtil.getAngleByDegrees(rotation - 180));
		} else {
			x = centerPos[0] + len * Math.cos(RecordUtil.getAngleByDegrees(360 - rotation));
			y = centerPos[1] - len * Math.sin(RecordUtil.getAngleByDegrees(360 - rotation));
		}

		double widthHalf = size[0] / 2.00;
		double heightHalf = size[1] / 2.00;
		if (x >= centerPos[0] - widthHalf &&
				x <= centerPos[0] + widthHalf &&
				y >= centerPos[1] - heightHalf &&
				y <= centerPos[1] + heightHalf) {
			return true;
		} else {
			return false;
		}

	}

    
    //校正两个控制图标的位置
    private void adjustControlPos(double oriRetation, double retation, double width, double height, float[] centerPointPos) {
    	double degrees = 0;
    	double[] point = new double[2];
    	double l = Math.sqrt(width *  width + height*height) / 2;
    	if (retation <= 90 - oriRetation  || retation > 360 - oriRetation) {//第一象限
    		if (retation <= 90 - oriRetation) {
    			degrees = oriRetation + retation;
    		} else {
    			degrees = oriRetation - (360 - retation);
    		}
    		double angle = RecordUtil.getAngleByDegrees(degrees);
    		double cos = Math.cos(angle);
    		double sin = Math.sin(angle);
    		point[0] = centerPointPos[0] + l*cos;
    		point[1] = centerPointPos[1] + l * sin;
    		
    	}
    	if (retation > 90 - oriRetation && retation <= 180 - oriRetation) {//第二象限
    		degrees = 180 - (oriRetation + retation);
    		double angle = RecordUtil.getAngleByDegrees(degrees);
    		double cos = Math.cos(angle);
    		double sin = Math.sin(angle);
    		point[0] = centerPointPos[0] - l * cos;
    		point[1] = centerPointPos[1] + l * sin;
    	}
    	
    	if (retation > 180 - oriRetation && retation <= 270 - oriRetation) {//第三象限
    		degrees = oriRetation + retation - 180;
    		double angle = RecordUtil.getAngleByDegrees(degrees);
    		double cos = Math.cos(angle);
    		double sin = Math.sin(angle);
    		point[0] = centerPointPos[0] - l * cos;
    		point[1] = centerPointPos[1] - l * sin;
    	}
    	
    	
    	if (retation > 270 - oriRetation && retation <= 360 - oriRetation) {//第四象限
    		degrees = 360 - (oriRetation + retation );
    		double angle = RecordUtil.getAngleByDegrees(degrees);
    		double cos = Math.cos(angle);
    		double sin = Math.sin(angle);
    		point[0] = centerPointPos[0] + l * cos;
    		point[1] = centerPointPos[1] - l * sin;
    	}
    	
    	//Log.e("xsp", point[0] + " " + point[1]);
    	IDragView closeView  = mCloseView.get(curPutView.getIndex());
		IDragView dragView = mDragView.get(curPutView.getIndex());
		if (dragView != null) {
			float size[] = dragView.getSize();
			float dwidth = size[0] / 2;
			float dheight = size[1] / 2 ;
			double dl = point[0] - dwidth;
			double dt = point[1] - dheight;
			double dr = point[0] + dwidth;
			double db = point[1] + dheight;

			float  dragPos[] = dragView.getPos();
			dragPos[0] = (float)dl;
			dragPos[1] = (float)dt;
			dragPos[2] = (float)dr;
			dragPos[3] = (float)db;
			dragView.setPos(dragPos);
			((View)dragView).layout((int) dl, (int) dt, (int) dr, (int) db);
		}




		if (closeView != null) {
			float size[] = closeView.getSize();
			float cwidth = size[0] / 2;
			float cheight = size[1] / 2 ;
			double cl = point[0] - 2 * (point[0] - centerPointPos[0]) - cwidth;
			double ct = point[1] - 2 * (point[1] - centerPointPos[1]) - cheight;
			double cr = point[0] - 2 * (point[0] - centerPointPos[0]) + cwidth;
			double cb = point[1] - 2 * (point[1] - centerPointPos[1]) + cheight;

			float  closePos[] = closeView.getPos();
			closePos[0] = (float)cl;
			closePos[1] = (float)ct;
			closePos[2] = (float)cr;
			closePos[3] = (float)cb;
			closeView.setPos(closePos);
			((View)closeView).layout((int) cl, (int) ct, (int) cr, (int) cb);
		}

    }
    

    
    //拖动滑动计算控制层View的位置
    private void computeMoveControlPos(float px, float py) {
    	IDragView closeView  = mCloseView.get(curPutView.getIndex());
		IDragView dragView = mDragView.get(curPutView.getIndex());
		ICenterView centerView = mCenterView.get(curPutView.getIndex());//

    	if (closeView != null) {
        	float[] closePos = closeView.getPos();
        	closePos[0] = closePos[0] + (px - POSITION[0]);
        	closePos[1] = closePos[1] + (py - POSITION[1]);
        	closePos[2] = closePos[2] + (px - POSITION[0]);
        	closePos[3] = closePos[3] + (py - POSITION[1]);
        	closeView.setPos(closePos);
			((View)closeView).layout((int) closePos[0], (int) closePos[1], (int) closePos[2], (int) closePos[3]);
    	}
    	
    	if (dragView != null) {
        	float[] dragPos = dragView.getPos();
        	dragPos[0] = dragPos[0] + (px - POSITION[0]);
        	dragPos[1] = dragPos[1] + (py - POSITION[1]);
        	dragPos[2] = dragPos[2] + (px - POSITION[0]);
        	dragPos[3] = dragPos[3] + (py - POSITION[1]);
        	dragView.setPos(dragPos);
			((View)dragView).layout((int) dragPos[0], (int) dragPos[1], (int) dragPos[2], (int) dragPos[3]);
    	}
    	
    	if (centerView != null) {
        	float[] centerPos = centerView.getPos();
        	centerPos[0] = centerPos[0] + (px - POSITION[0]);
        	centerPos[1] = centerPos[1] + (py - POSITION[1]);
        	centerPos[2] = centerPos[2] + (px - POSITION[0]);
        	centerPos[3] = centerPos[3] + (py - POSITION[1]);
        	centerView.setPos(centerPos);
			((View)centerView).layout((int) centerPos[0], (int) centerPos[1], (int) centerPos[2], (int) centerPos[3]);
        	
        	float[] centerPointPos = centerView.getCenterPos();
        	centerPointPos[0] = centerPointPos[0] + (px - POSITION[0]);
        	centerPointPos[1] = centerPointPos[1] + (py - POSITION[1]);
        	centerView.setCenterPos(centerPointPos);
    	}

    	
    }

  
    //获取当前位置的VIEW
    private IDragView getViewByLocation() {
    	int size = mAllView.size();
		for (int i = 0; i < mAllView.size(); i++) {
			IDragView view = mAllView.get(size - i - 1);
			if (!(view instanceof View) || !isVisiable(view)) {
				continue;
			}
			Rect rect = new Rect();
			((View) view).getHitRect(rect);
			if (!rect.contains((int) POSITION[0], (int) POSITION[1])) {
				continue;
			}
			if (view instanceof ICenterView) {
				if (checkTouchPointInCenter((ICenterView)view)) {
					return view;
				}
			} else {
				return view;
			}
		}
		return null;
	}


	public int getLevels() {
		int count = 0;
		for(IDragView view : mAllView) {
			if (view.getFlag() == IDragView.TAG_CENTRE) {
				count++;
			}
		}
		return count;
	}

    @Override
    public void addView(View child) {
    	if (child instanceof IDragView) {
    		IDragView view = (IDragView)child;
    		mAllView.add(view);
    		switch(view.getFlag()) {
			case IDragView.TAG_TEXT:
				mDragText = view;
				break;
    		case IDragView.TAG_CENTRE:
				mCenterView.put(view.getIndex(), (ICenterView) view);
    			break;
    		case IDragView.TAG_DRAG:
    			mDragView.put(view.getIndex(), view);
    			break;
    		case IDragView.TAG_CLOSE:
    			mCloseView.put(view.getIndex(), view);
    			break;
    		default :
    			break;
    		}
    	}
    	super.addView(child);
    }


    @Override
    public void removeView(View view) {
    	if (view instanceof IDragView) {
    		IDragView child = (IDragView)view;
			mAllView.remove(child);
    		switch(child.getFlag()) {
			case IDragView.TAG_TEXT:
				mDragText = null;
				break;
    		case IDragView.TAG_CENTRE:
				mCenterView.remove(child.getIndex());
    			break;
    		case IDragView.TAG_DRAG:
    			mDragView.remove(child.getIndex());
    			break;
    		case IDragView.TAG_CLOSE:
    			mCloseView.remove(child.getIndex());
    			break;
    		default :
    			break;
    		}
    	}
		super.removeView(view);
    }

	public void removeViewByIndex(String index) {
		View closeView = (View)mCloseView.get(index);
		removeView(closeView);
		View dragView = (View)mDragView.get(index);
		removeView(dragView);
		View centerView = (View)mCenterView.get(String.valueOf(index));
		removeView(centerView);
		if (centerView instanceof ICenterView) {
			Bitmap map = ((ICenterView) centerView).getBitmap();
			if (map != null && !map.isRecycled()) {
				map.recycle();
			}
		}
	}

	public void recycled() {
		for (IDragView view : mAllView) {
			if (view instanceof ICenterView) {
				Bitmap map = ((ICenterView) view).getBitmap();
				if (map != null && !map.isRecycled()) {
					map.recycle();
				}
			}
		}
		mAllView.clear();
		mCloseView.clear();
		mCenterView.clear();
		mDragView.clear();
	}
    
    @Override
    public void removeAllViews() {
    	mCenterView.clear();
    	mDragView.clear();
    	mCloseView.clear();
    	mAllView.clear();
    	super.removeAllViews();
    }


	public void refreshView() {
		for (IDragView view : mAllView) {
			if (view instanceof ICenterView) {
				ICenterView center = (ICenterView)view;
				boolean isEdit = center.isEdit();
				String index = center.getIndex();
				IDragView drag = mDragView.get(index);
				IDragView close = mCloseView.get(index);
				View centerView = (View)center;
				int visible = centerView.getVisibility();
				View dragView = (View)drag;
				View closeView = (View)close;
				if (dragView != null) {
					if (!isEdit || visible != View.VISIBLE) {
						dragView.setVisibility(View.INVISIBLE);
					} else {
						dragView.setVisibility(View.VISIBLE);
					}
				}

				if (closeView != null) {
					if (!isEdit || visible != View.VISIBLE) {
						closeView.setVisibility(View.INVISIBLE);
					} else {
						closeView.setVisibility(View.VISIBLE);
					}
				}

				if (visible != View.VISIBLE) {
					centerView.setVisibility(View.INVISIBLE);
				} else {
					centerView.setVisibility(View.VISIBLE);
				}

				if (isEdit) {
					if (!center.isHasBackground()) {
						centerView.setBackgroundResource(R.drawable.drag_center_side);
						center.setHasBackground(true);
					}
				} else {
					if (center.isHasBackground()) {
						centerView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
						center.setHasBackground(false);
					}
				}
			}
		}
	}
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
        int mCount = getChildCount();
		int pWidth = getMeasuredWidth();
		int pHeigth = getMeasuredHeight();
		for (int i = 0; i < mCount; i++) {
			final View child = getChildAt(mCount - i - 1);
			if (child instanceof IDragView) {
				IDragView view = (IDragView) child;
				if (view.isInit()) {
					float[] pos = view.getPos();
					child.layout((int)pos[0], (int)pos[1], (int)pos[2], (int)pos[3]);
				} else {
					float ll = 0;
					float tt = 0;
					float rr = 0;
					float bb = 0;
					int selfWidth = child.getMeasuredWidth();
					int selfHeigh = child.getMeasuredHeight();
					if (view.getFlag() == IDragView.TAG_TEXT) {
						int startPos = (pHeigth / 3) * 2;
						ll = 0;
						tt = startPos;
						rr = pWidth;
						bb = startPos + selfHeigh;
					} else {
						int width = ((View)mCenterView.get(view.getIndex())).getMeasuredWidth();
						int height = ((View)mCenterView.get(view.getIndex())).getMeasuredHeight();
						switch (view.getFlag()) {
							case IDragView.TAG_CENTRE:
								ll = pWidth / 2 - selfWidth / 2;
								tt = pHeigth / 2 - selfHeigh / 2;
								rr = pWidth / 2 + selfWidth / 2;
								bb = pHeigth / 2 + selfHeigh / 2;
								((ICenterView)view).setCenterPos(new float[]{pWidth/2,pHeigth/2});
								break;
							case IDragView.TAG_DRAG:
								ll = pWidth / 2 + width / 2 - selfWidth / 2;
								tt = pHeigth / 2 + height / 2 - selfHeigh / 2;
								rr = pWidth / 2 + width / 2 + selfWidth / 2;
								bb = pHeigth / 2 + height / 2 + selfHeigh / 2;
								break;
							case IDragView.TAG_CLOSE:
								ll = pWidth / 2 - width / 2 - selfWidth / 2;
								tt = pHeigth / 2 - height / 2 - selfHeigh / 2;
								rr = pWidth / 2 - width / 2 + selfWidth / 2;
								bb = pHeigth / 2 - height / 2 + selfHeigh / 2;
								break;

						}
					}
					view.setPos(new float[]{ll, tt, rr, bb});
					view.setSize(new float[]{selfWidth, selfHeigh});
					view.setInit(true);
					child.layout((int)ll, (int) tt, (int) rr, (int) bb);
				}
				
			}
		}
    }
    
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int mWidth = MeasureSpec.getSize(widthMeasureSpec);
		int mHeight = MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(mWidth, mHeight);
    }
    
}
