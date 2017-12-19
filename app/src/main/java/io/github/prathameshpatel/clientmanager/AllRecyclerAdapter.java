package io.github.prathameshpatel.clientmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.github.prathameshpatel.clientmanager.entity.Client;

/**
 * Created by Prathamesh Patel on 12/17/2017.
 */

public class AllRecyclerAdapter extends RecyclerView.Adapter<AllRecyclerAdapter.ViewHolder>{

    private static String LOG_TAG = "AllRecyclerAdapter";
    private List<String> mDataset;
//    private static OnItemClickListener onItemClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener, View.OnLongClickListener*/{
        public TextView name_title;
        public View mView; //view for the view holder

        public ViewHolder(View itemView) {
            super(itemView);
//            itemView.setOnClickListener(this);
//            itemView.setOnLongClickListener(this);
            mView = itemView;
            name_title = itemView.findViewById(R.id.all_item_title);
        }

//        @Override
//        public void onClick(View v) {
//            onItemClickListener.onItemClick(getAdapterPosition(), v);
//        }
//
//        @Override
//        public boolean onLongClick(View v) {
//            onItemClickListener.onItemLongClick(getAdapterPosition(), v);
//            return false;
//        }
    }

//    public OnItemClickListener getOnItemClickListener() {
//        return onItemClickListener;
//    }
//
//    public void setOnItemClickListener(OnItemClickListener clickListener) {
//        this.onItemClickListener = clickListener;
//    }

    public AllRecyclerAdapter(List<String> dataset) {
        mDataset = dataset;
    }

    //Set the item view to the view_holder and inflate it
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_all_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //Bind the data to the view_holder
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // Find out the data, based on this view holder's position
        String thisItemName = mDataset.get(position);

        //Set the data to the view_holder
        holder.name_title.setText(thisItemName);

        //Set onClickListener for the view holder
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Toast.makeText(context, "position= "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

//    public interface OnItemClickListener {
//        public void onItemClick(int position, View v);
//        public void onItemLongClick(int position, View v);
//    }
}
