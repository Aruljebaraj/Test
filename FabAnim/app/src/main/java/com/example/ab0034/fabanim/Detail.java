package com.example.ab0034.fabanim;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Detail extends AppCompatActivity {
    String Name, Price, Date;
    int icon;
    ImageView img_card;
    TextView tv_title, tv_amount, tv_title_date;
    FloatingActionButton floatingnegative, floatingpositive;
    RecyclerView Detailrecyclerview;
    List<Model> fakeProducts = Model.createFakeProducts();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        img_card = (ImageView) findViewById(R.id.img_card);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        tv_title_date = (TextView) findViewById(R.id.tv_title_date);
        floatingnegative = (FloatingActionButton) findViewById(R.id.floatingnegative);
        floatingpositive = (FloatingActionButton) findViewById(R.id.floatingpositive);
        Detailrecyclerview = (RecyclerView) findViewById(R.id.Detailrecyclerview);
        Detailrecyclerview.setAdapter(new ProductAdapter(fakeProducts));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        Detailrecyclerview.setLayoutManager(linearLayoutManager);
        getintent();
    }

    public void getintent() {
        if (getIntent() != null) {
            Name = getIntent().getStringExtra("Name");
            Price = getIntent().getStringExtra("Price");
            Date = getIntent().getStringExtra("Date");
            icon = getIntent().getIntExtra("Image", 1);

            tv_title.setText(Name);
            tv_amount.setText(Price);
            tv_title_date.setText(Date);
            img_card.setImageResource(icon);

        }

    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
        animateViewsOut();
    }


    private void animateViewsOut() {
        float translateTo = floatingnegative.getHeight() * 0f;

        floatingnegative.animate()
                .translationY(translateTo)
                .setDuration(650)
                .start();
        floatingpositive.animate()
                .translationY(translateTo)
                .setStartDelay(50)
                .setDuration(350)
                .start();

    }
    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

        private final List<Model> arrayList;

        public ProductAdapter(List<Model> fakeProducts) {
            this.arrayList = fakeProducts;
        }

        @Override

        public ProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ProductAdapter.MyViewHolder holder, int i) {
            final Model product = arrayList.get(i);
            holder.img_card.setImageResource(product.image);
            holder.tv_title.setText(product.name);
            holder.tv_amount.setText(product.price);
            holder.tv_title_date.setText(product.Date);
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView img_card;
            TextView tv_title, tv_amount, tv_title_date;
            CardView card_view;

            public MyViewHolder(View itemView) {
                super(itemView);
                img_card = (ImageView) itemView.findViewById(R.id.img_card);
                tv_title = (TextView) itemView.findViewById(R.id.tv_title);
                tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
                tv_title_date = (TextView) itemView.findViewById(R.id.tv_title_date);
                card_view = (CardView) itemView.findViewById(R.id.card_view);

            }
        }
    }
}
