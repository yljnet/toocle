package com.netsun.toocle.model;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.netsun.toocle.R;

/**
 * Created by Administrator on 2016/12/13.
 */

public class CircleFragment extends ListFragment {
    private ListView circleView;
    private Button searchCircle;
    private TextView circleNum;

    public static CircleFragment newInstence(String paraml){
        Bundle args = new Bundle();
        args.putString("arg1",paraml);
        CircleFragment circleFragment = new CircleFragment();
        circleFragment.setArguments(args);
        return circleFragment;
    }
    //碎片与活动建立关联
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    //创建实例
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //创建视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("tag", "onCreateView: CircleFragment");
        View view = inflater.inflate(R.layout.fragme_syq,container,false);
        circleNum = (TextView)view.findViewById(R.id.circle_num);
        searchCircle = (Button)view.findViewById(R.id.search_circle);
        searchCircle.setOnClickListener(searchCircleListener);
        return view;
    }
    //确认与碎片关联的活动一定已经创建完毕
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        circleView = getListView();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
    }
    private View.OnClickListener searchCircleListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent("com.netsun.toocle.ACTION_SEARCH_CIRCLE");
            intent.addCategory("android.intent.category.DEFAULT");
            startActivity(intent);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
