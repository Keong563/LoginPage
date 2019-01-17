package com.example.crysis.loginpage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    View mView;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view , getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view , getAdapterPosition());
                return true;
            }
        });
    }

    public void setDetail(Context ctx , String foodName ,String foodId,String email, String NewFoodImage , String pickUpDetail , String price ,String location , String expiredDate , String todayDate){

        TextView mTitleTv= mView.findViewById(R.id.rTitleTv);
        TextView mDescTv = mView.findViewById(R.id.rDescTv);
        TextView mPriceTv = mView.findViewById(R.id.rPriceTv);
        ImageView mImageView = mView.findViewById(R.id.rImageView);

        mTitleTv.setText(foodName);
        mDescTv.setText(pickUpDetail);
        mPriceTv.setText(price);
        Picasso.get().load(NewFoodImage).into(mImageView);
    }

    private ViewHolder.ClickListener mClickListener;

    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void  setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

}
