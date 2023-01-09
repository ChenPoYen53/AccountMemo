package com.example.accountmemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.accountmemo.Database.MainData;
import com.example.accountmemo.Database.RoomDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class tf_RecyclerViewAdapter extends RecyclerView.Adapter<tf_RecyclerViewAdapter.ViewHolder>
{
    private static final String TAG = "tf_RecyclerViewAdapter";
    private static final String DATEPICKER = "datePicker";
    private final Activity context;
    private List<MainData> mainDataList = new ArrayList<>();
    private RoomDB roomDB;
    private MainData mainData = new MainData();

    public tf_RecyclerViewAdapter(Activity context, List<MainData> mainDataList) {
        this.context = context;
        this.mainDataList = mainDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tf_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final String DELETE = context.getResources().getString(R.string.DELETE);
        final String Delete = context.getResources().getString(R.string.Delete);
        final String Are_you_sure_to_Delete = context.getString(R.string.Are_you_sure_to_Delete);
        final String Cancel = context.getString(R.string.Cancel);

        mainData = mainDataList.get(position);
        roomDB = RoomDB.getInstance(context);
        holder.No.setText(String.valueOf(position+1));
        int Y , N , D ;
        Y = mainData.getYear();
        N = mainData.getMonth();
        D = mainData.getDate();
        String YND = String.valueOf(Y)+"/"+String.valueOf(N)+"/"+String.valueOf(D);
        Log.d(TAG,"YND..."+YND);
        holder.time.setText(YND);
        holder.cost.setText(String.valueOf(mainData.getCost()));
        holder.note.setText(mainData.getNote());
        holder.type.setText(mainData.getType());
        holder.asset.setText(mainData.getAsset());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle(DELETE).setMessage(Are_you_sure_to_Delete)
                        .setPositiveButton(Delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mainData = mainDataList.get(holder.getAdapterPosition());
                                roomDB.mainDao().delete(mainData);
                                int p = holder.getAdapterPosition();
                                mainDataList.remove(p);
                                notifyItemRemoved(p);
                                notifyItemRangeRemoved(p,mainDataList.size());
                                Intent intent = new Intent(context,MainActivity.class);
                                context.startActivity(intent);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(Cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return mainDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView No,time,type,cost,note,asset;
        ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            No = itemView.findViewById(R.id.item_No_);
            time = itemView.findViewById(R.id.item_time);
            type = itemView.findViewById(R.id.item_type);
            cost = itemView.findViewById(R.id.item_cost);
            note = itemView.findViewById(R.id.item_note);
            delete = itemView.findViewById(R.id.item_delete);
            asset = itemView.findViewById(R.id.item_asset);
        }
    }
}
