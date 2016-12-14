package com.netsun.toocle.model;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.netsun.toocle.R;
import com.netsun.toocle.util.Circle;
import com.netsun.toocle.util.PopupSpinnerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/13.
 */

public class PopSpinner extends PopupWindow {
    private ArrayList<Circle> circles = null;
    private TextView setTV;
    private RecyclerView itemsView;

    public PopSpinner(final Activity context, Handler handler, ArrayList<Circle> circlelist) {
        super(context);
        this.circles = circlelist;
        final View view = LayoutInflater.from(context).inflate(R.layout.pop_spinner, null);
        setTV = (TextView) view.findViewById(R.id.set_tv);
        setTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PopSpinner.this.dismiss();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        itemsView = (RecyclerView) view.findViewById(R.id.items);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        itemsView.setLayoutManager(manager);
        itemsView.setAdapter(new PopupSpinnerAdapter(circles, handler));
        itemsView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL_LIST));

        //设置SelectPicPopupWindow的View
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.whitesmoke));
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(null);
    }

    public void show(View parent) {

        if (!this.isShowing()) {
            this.showAsDropDown(parent, 20, parent.getLayoutParams().height + 20);
        } else {
            this.dismiss();
        }
    }

    static class DividerItemDecoration extends RecyclerView.ItemDecoration {
        private static final int[] ATTRS = new int[]{
                android.R.attr.listDivider
        };

        public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

        public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

        private Drawable mDivider;

        private int mOrientation;
        public  DividerItemDecoration(Context context, int orientation){
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
            setOrientation(orientation);
        }

        private void setOrientation(int orientation) {
            if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
                throw new IllegalArgumentException("invalid orientaion");
            }
            mOrientation = orientation;
        }
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            if (mOrientation == HORIZONTAL_LIST) {
                drawHorizontal(c,parent );
            } else  if(mOrientation == VERTICAL_LIST) {
                drawVertical(c,parent);
            }
        }
        private void drawHorizontal(Canvas c, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getPaddingBottom() - parent.getPaddingTop();
            final int childCount = parent.getChildCount();
            for (int i = 0; i< childCount; i++) {
                final View v = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) v.getLayoutParams();
                final int left = v.getRight() + params.rightMargin;
                final int right = left + mDivider.getIntrinsicWidth()   ;
                mDivider.setBounds(left,top,right,bottom);
                mDivider.draw(c);
            }
        }
        private void  drawVertical(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getPaddingRight() - parent.getPaddingLeft();
            final int childCount = parent.getChildCount();
            for (int i = 0; i< childCount; i++) {
                final View v = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) v.getLayoutParams();
                final int top = v.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left,top,right,bottom);
                mDivider.draw(c);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (mOrientation == VERTICAL_LIST) {
                outRect.set(0,0,0,mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0,0,mDivider.getIntrinsicWidth(),0);
            }
        }
    }
}
