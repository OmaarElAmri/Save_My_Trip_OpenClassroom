package com.openclassrooms.savemytrip.todolist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.savemytrip.R;
import com.openclassrooms.savemytrip.models.Item;

import java.io.InputStream;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.activity_todo_list_item_text) TextView textView;
    @BindView(R.id.activity_todo_list_item_image) ImageView imageView;
    @BindView(R.id.activity_todo_list_item_image_custom) ImageView imageViewCustom;
    @BindView(R.id.activity_todo_list_item_remove) ImageButton imageButton;
    @BindView(R.id.activity_todo_list_item_share) ImageButton imageButtonShare;

    // FOR DATA
    private WeakReference<ItemAdapter.Listener> callbackWeakRef;

    public ItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithItem(Item item, ItemAdapter.Listener callback){
        this.callbackWeakRef = new WeakReference<ItemAdapter.Listener>(callback);
        this.textView.setText(item.getText());
        this.imageButton.setOnClickListener(this);
        this.imageButtonShare.setOnClickListener(this);
        switch (item.getCategory()){
            case 0: // TO VISIT
                this.imageView.setBackgroundResource(R.drawable.ic_room_black_24px);
                break;
            case 1: // IDEAS
                this.imageView.setBackgroundResource(R.drawable.ic_lightbulb_outline_black_24px);
                break;
            case 2: // RESTAURANTS
                this.imageView.setBackgroundResource(R.drawable.ic_local_cafe_black_24px);
                break;
        }
        if (item.getSelected()){
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        if(item.getImageUri() != null){
            Uri mUri = Uri.parse(item.getImageUri());

            Log.i("TEST Ok : ", mUri.toString());
            this.imageViewCustom.setImageURI(mUri);
        }else{
            this.imageViewCustom.setVisibility(View.GONE);
            this.imageButtonShare.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        ItemAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null){
            if (view.getId() == R.id.activity_todo_list_item_remove) {
                callback.onClickDeleteButton(getAdapterPosition());
            }

            if (view.getId() == R.id.activity_todo_list_item_share) {
                callback.onClickShareButton(getAdapterPosition());
            }
        }
    }

}