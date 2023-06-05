package com.example.dogapirest13

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class DogsViewHolder(view:View):RecyclerView.ViewHolder(view) {

    private var ivDog:ImageView = view.findViewById(R.id.ivDog);

    fun bind(images:String){
        Picasso.get().load(images).into(ivDog);
    }
}