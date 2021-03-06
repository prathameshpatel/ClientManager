package io.github.prathameshpatel.clientmanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.github.prathameshpatel.clientmanager.db.AppDatabase;
import io.github.prathameshpatel.clientmanager.entity.Client;

/**
 * Created by Prathamesh Patel on 12/17/2017.
 */

public class AllRecyclerAdapter extends RecyclerView.Adapter<AllRecyclerAdapter.ViewHolder>{

    private static String LOG_TAG = "AllRecyclerAdapter";
    private List<Client> mDataset;
//    private static OnItemClickListener onItemClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener, View.OnLongClickListener*/{
        public TextView name_title;
        public View mView; //view for the view holder
        public CheckBox mCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
//            itemView.setOnClickListener(this);
//            itemView.setOnLongClickListener(this);
            mView = itemView;
            name_title = itemView.findViewById(R.id.all_item_title);
            mCheckBox = itemView.findViewById(R.id.star_checkbox);
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

    public AllRecyclerAdapter(List<Client> dataset) {
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // Find out the data, based on this view holder's position
        final Client currentClient = mDataset.get(position);
        String fullName = currentClient.getFirstName()+" "+currentClient.getLastName();
        //Set the data to the view_holder
        holder.name_title.setText(fullName);

        final int id = currentClient.getClientId();

        //Set onClickListener for the view holder
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context,DetailsActivity.class);
                intent.putExtra("client_id",id);
                context.startActivity(intent);
//                Toast.makeText(context, "position= "+position+", id= "+id, Toast.LENGTH_SHORT).show();
            }
        });

        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.mCheckBox.isChecked()) {
                    Toast.makeText(v.getContext(),"checked!", Toast.LENGTH_SHORT).show();
                    FavoritesFragment.newInstance(null,null).addFavorite(id);
                    notifyDataSetChanged();
                }
                else {
                    Toast.makeText(v.getContext(),"un-checked!", Toast.LENGTH_SHORT).show();
                    FavoritesFragment.newInstance(null,null).removeFavorite(id);
                    notifyDataSetChanged();
                }
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
