package com.example.ab0034.fabanim;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView ProductRecyclerview;
    List<Model> fakeProducts = Model.createFakeProducts();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Slide fade = new Slide();
//            fade.excludeTarget(R.id.ProductRecyclerview, true);
//            fade.excludeTarget(android.R.id.statusBarBackground, true);
//            fade.excludeTarget(android.R.id.navigationBarBackground, true);


            Transition changeTransform = TransitionInflater.from(this).
                    inflateTransition(R.transition.change_image_transform);
            Transition explodeTransform = TransitionInflater.from(this).
                    inflateTransition(android.R.transition.explode);

            getWindow().setEnterTransition(changeTransform);
            getWindow().setExitTransition(explodeTransform);
        }
        ProductRecyclerview = (RecyclerView) findViewById(R.id.ProductRecyclerview);
        ProductRecyclerview.setAdapter(new ProductAdapter(fakeProducts));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        ProductRecyclerview.setLayoutManager(linearLayoutManager);
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
            holder.card_view.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Detail.class);
                    intent.putExtra("Name", product.name);
                    intent.putExtra("Price", product.price);
                    intent.putExtra("Date", product.Date);
                    intent.putExtra("Image", product.image);
                    ActivityOptionsCompat options =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, holder.img_card, "profile");
                    ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());


                }
            });

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
