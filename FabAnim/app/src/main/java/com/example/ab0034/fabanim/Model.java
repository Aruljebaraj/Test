package com.example.ab0034.fabanim;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Model implements Serializable {
    public final String name;
    public final String price;
    public final String Date;
    public final int image;
    public String Details;
//    public final int color;

    private Model(String name, String price,String Date, int image) {
        this.name = name;
        this.price = price;
        this.Date = Date;
        this.image = image;
//        this.color = color;
    }

    public static List<Model> createFakeProducts() {
        return Arrays.asList(
                new Model("Google Play", "$38,456.78","28.07.2018", R.drawable.img_google_play),
                new Model("Twitter", "$1,550.60","27.07.2018", R.drawable.img_twitter),
                new Model("YouTube", "$14,340.00", "27.07.2018",R.drawable.img_youtube),
                new Model("VK", "$13,846.13","25.07.2018", R.drawable.img_vk),
                new Model("Vimeo", "$18,347.32","10.07.2018", R.drawable.img_vimeo),
                new Model("Facebook", "$18,347.32","10.07.2018", R.drawable.img_facebook)
        );
    }
}

