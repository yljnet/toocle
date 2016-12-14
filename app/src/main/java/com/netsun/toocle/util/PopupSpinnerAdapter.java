package com.netsun.toocle.util;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netsun.toocle.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/13.
 */

public class PopupSpinnerAdapter extends RecyclerView.Adapter<PopupSpinnerAdapter.ViewHolder> {
    private ArrayList<Circle> circles;
    private Handler handler;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemtext;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView = (TextView) itemView.findViewById(R.id.item_text);
        }
    }

    public PopupSpinnerAdapter(ArrayList<Circle> circlelist, Handler handler) {
        this.circles = circlelist;
        this.handler = handler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemtext = (TextView) holder.itemView.findViewById(R.id.item_text);
        holder.itemtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = position;
                msg.obj = circles.get(position);
                handler.sendMessage(msg);
            }
        });
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String string = circles.get(position).getName();
        if (string != null) {
            if (holder.itemtext != null) {
                holder.itemtext.setText(string);
            } else {
                holder.itemtext = (TextView) holder.itemView.findViewById(R.id.item_text);
                holder.itemtext.setText(string);
            }
        }
    }

    @Override
    public int getItemCount() {
        return circles.size();
    }
}
