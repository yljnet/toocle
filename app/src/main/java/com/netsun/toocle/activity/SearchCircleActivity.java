package com.netsun.toocle.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netsun.toocle.R;
import com.netsun.toocle.model.PopSpinner;
import com.netsun.toocle.util.Circle;
import com.netsun.toocle.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchCircleActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvZlx, tvClx;
    Button button;
    ArrayList<Circle> zlxArr = new ArrayList<Circle>();
    ArrayList<Circle> clxArr = new ArrayList<Circle>();
    List<List<Circle>> lists = new ArrayList<List<Circle>>();
    int zlxId, clxId;
    int currentPage = 0;
    int freshQs = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_circle);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        tvClx = (TextView) findViewById(R.id.tv_clx);
        tvClx.setOnClickListener(this);
        tvZlx = (TextView) findViewById(R.id.tv_zlx);
        tvZlx.setOnClickListener(this);
        button = (Button) findViewById(R.id.find_q);
        button.setOnClickListener(this);
        HttpUtil.getHttp("http://sns.toocle.com/index.php?_a=mobile&f=category_q&grade=2", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                JSONObject object = null;
                JSONArray list = null;
                try {
                    object = new JSONObject(json);
                    list = object.getJSONArray("items");
                    Gson gson = new Gson();
                    List<Circle> zlxList = gson.fromJson(list.toString(), new TypeToken<List<Circle>>() {
                    }.getType());
                    for (Circle circle : zlxList) {
                        zlxArr.add(circle);
                        String jsons = object.getJSONArray("items" + circle.getId()).toString();
//                        Log.d("jsons", "onResponse: " + jsons);
                        List<Circle> circleList = gson.fromJson(jsons, new TypeToken<List<Circle>>() {
                        }.getType());
                        lists.add(circleList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_zlx:
                PopSpinner zlxSpinner = new PopSpinner(this, new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.what == 1) {
                            Circle zlx = (Circle) msg.obj;
                            zlxId = zlx.getId();
                            clxId = 0;
                            int po = msg.arg1;
                            tvZlx.setText(zlx.getName());
                            tvClx.setText("次类型");
                            clxArr.clear();
//                            Log.d("tag", "handleMessage: " + po + ";size:" + lists.size());
                            for (Circle circle : lists.get(po)) {
                                clxArr.add(circle);
                            }
                        }
                    }
                }, zlxArr);
                zlxSpinner.show(tvZlx);
                break;
            case R.id.tv_clx:
                PopSpinner clxSpinner = new PopSpinner(this, new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.what == 1) {
                            Circle clx = (Circle) msg.obj;
                            tvClx.setText(clx.getName());
                            clxId = clx.getId();
                        }
                    }
                }, clxArr);
                clxSpinner.show(tvClx);
                break;
            case R.id.find_q:
                String url = null;
                String qname = ((EditText) findViewById(R.id.cname_text)).getText().toString();
                if (clxId == 0) {
                    url = "http://sns.toocle.com/index.php?_a=mobile&f=search&client_id=snstoocleapp&category=" +
                            +zlxId + "&terms=" + qname + "&cate1=1&p=" + currentPage + "&limit=" + freshQs;
                } else {
                    url = "http://sns.toocle.com/index.php?_a=mobile&f=search&client_id=snstoocleapp&category=" +
                            +clxId + "&terms=" + qname + "&cate1=1&p=" + currentPage + "&limit=" + freshQs;
                }
                Log.d("tag", "url: " + url);
                HttpUtil.getHttp(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();
                        Log.d("json", "onResponse: " + json);
                    }
                });
                break;
            default:
                break;
        }
    }
}
