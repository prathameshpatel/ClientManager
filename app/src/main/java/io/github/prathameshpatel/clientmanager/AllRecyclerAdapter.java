package io.github.prathameshpatel.clientmanager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.prathameshpatel.clientmanager.entity.Client;

/**
 * Created by Prathamesh Patel on 12/17/2017.
 */

public class AllRecyclerAdapter extends RecyclerView.Adapter<AllRecyclerAdapter.ViewHolder>{

    private static String LOG_TAG = "AllRecyclerAdapter";
    private List<String> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_title;

        public ViewHolder(View itemView) {
            super(itemView);
            name_title = itemView.findViewById(R.id.all_item_title);
        }
    }

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Find out the data, based on this view holder's position
        String thisItemName = mDataset.get(position);

        //Set the data to the view_holder
        holder.name_title.setText(thisItemName);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
