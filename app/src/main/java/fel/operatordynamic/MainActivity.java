package fel.operatordynamic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;

import fel.operatordynamic.uilib.CenterView;
import fel.operatordynamic.uilib.CloseImageView;
import fel.operatordynamic.uilib.DragDynamicView;
import fel.operatordynamic.uilib.DragImageView;
import fel.operatordynamic.uilib.base.ICenterView;
import fel.operatordynamic.uilib.base.IDragView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
        ,DragDynamicView.OnOutSideClickListener{

    private Button mAddButton;
    private DragDynamicView mDragControlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAddButton = findViewById(R.id.btn_add);
        mAddButton.setOnClickListener(this);
        mDragControlView = findViewById(R.id.drag_control_layout);
        mDragControlView.setOnOutSideClickListener(this);
    }

    private void addDynamicView() {
        CloseImageView closeView = (CloseImageView)getLayoutInflater().inflate(R.layout.story_close_view, null);
        closeView.setImageResource(R.mipmap.story_dragdynamic_close);
        closeView.setIndex(String.valueOf(mDragControlView.LEVELS));
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof CloseImageView && mDragControlView != null) {
                    mDragControlView.removeViewByIndex(((CloseImageView) v).getIndex());
                }

            }
        });
        DragImageView dragView = (DragImageView)getLayoutInflater().inflate(R.layout.story_drag_view, null);
        dragView.setImageResource(R.mipmap.story_dragdynamic_drag);
        dragView.setIndex(String.valueOf(mDragControlView.LEVELS));
        CenterView centerView = (CenterView)getLayoutInflater().inflate(R.layout.story_center_view, null);
        ImageView centerImageView = (ImageView)centerView.findViewById(R.id.center_pic);
        centerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewParent parent = v.getParent();
                if (parent instanceof CenterView) {
                    CenterView parentView = (CenterView) parent;
                    parentView.setIsEdit(true);
                    if (mDragControlView != null) {
                        mDragControlView.refreshView();
                    }
                }

            }
        });
        centerView.setIsEdit(true);
        centerView.setBitmap(null);
        centerImageView.setImageResource(R.mipmap.timg);
        centerView.setIndex(String.valueOf(mDragControlView.LEVELS));
        mDragControlView.LEVELS++;
        mDragControlView.addView(centerView);
        mDragControlView.addView(closeView);
        mDragControlView.addView(dragView);
    }

    private void setDragViewAllEnEdit() {
        if (mDragControlView != null && mDragControlView.getAllViews() != null) {
            for (int i = 0; i < mDragControlView.getAllViews().size(); i++) {
                IDragView view = mDragControlView.getAllViews().get(i);
                if (view instanceof ICenterView) {
                    ((ICenterView) view).setIsEdit(false);
                }
            }
            mDragControlView.refreshView();
        }
    }


    @Override
    public void onClick(View v) {
        if(v == mAddButton) {
            addDynamicView();
        } else {
            setDragViewAllEnEdit();
        }
    }
}
