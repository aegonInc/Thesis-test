package com.example.thesis.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thesis.Model.House;
import com.example.thesis.R;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView authorView;
    public TextView housenameView;
    public TextView houseTypeView;
    public TextView descriptionView;
    public TextView addressLatView;
    public TextView addressLongView;
    public TextView addressBarangayView;
    public TextView permitView;
    public TextView roomNumberView;
    public TextView bathroomNumberView;
    public TextView amenetiesView;
    public TextView houseRuleView;
    public TextView paymentView;
    public TextView paymentTypeView;
    public TextView housePaymentRuleView;
    public TextView kitchenNumberView;
    public TextView bedNumberView;

    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView;

    public PostViewHolder(View itemView) {
        super(itemView);

        authorView = itemView.findViewById(R.id.post_author);

        housenameView = itemView.findViewById(R.id.houseName);
        houseTypeView = itemView.findViewById(R.id.houseType);
        descriptionView = itemView.findViewById(R.id.houseDesc);

        starView = itemView.findViewById(R.id.star);
        numStarsView = itemView.findViewById(R.id.post_num_stars);
        bodyView = itemView.findViewById(R.id.houseType);
    }

    public void bindToPost(House post, View.OnClickListener starClickListener) {
        housenameView.setText(post.housename);
        authorView.setText(post.author);
        numStarsView.setText(String.valueOf(post.starCount));
        bodyView.setText(post.description);

        starView.setOnClickListener(starClickListener);
    }
}
