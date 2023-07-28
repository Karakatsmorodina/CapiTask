package com.example.capitask;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageView css,css1,css2;
    TextView textView;
    int score = 0;
    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        SharedPreferences sp = this.getSharedPreferences("Myscore", Context.MODE_PRIVATE);
        score = sp.getInt("score",0);
        textView.setText(Integer.toString(score));

        css = findViewById(R.id.css);
        css1 = findViewById(R.id.css1);
        css2 = findViewById(R.id.css2);

        Glide.with(this)
                .load(R.drawable.catdance)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(new RoundedCornersTransformation(
                        20,0
                ))
                .into(css2);
        Glide.with(this)
                .load(R.drawable.css)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(new RoundedCornersTransformation(
                        20,0
                ))
                .into(css);
        Glide.with(this)
                .load(R.drawable.css1)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(new RoundedCornersTransformation(
                        20,0
                ))
                .into(css1);

        css1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int randomNumber = random.nextInt(6);
                String[] url = new String[10];
                url[0] = "https://www.shortform.com/summary/the-master-guides-living-your-best-life-summary-shortform?gbraid=0AAAAACvyfSRoeULfiTPda5ydfvVsqC1Af&gclid=CjwKCAjwq4imBhBQEiwA9Nx1Bvf4Kh-ZdZwF-VKH4zK8OIB0zDrj1ojwQgJV7v5x1cYODLlnDQD8HhoCw5gQAvD_BwE";
                url[1] = "https://quantive.com/resources/articles/goal-setting?utm_term=planning%20goal%20setting&utm_campaign=awareness-goal-setting-guide-04-23-v1-emea-apac-google-search&utm_source=google&utm_medium=cpc&hsa_acc=8866348274&hsa_cam=19663679037&hsa_kw=planning%20goal%20setting&hsa_ver=3&hsa_mt=b&hsa_grp=146492050485&hsa_ad=655116257245&hsa_tgt=kwd-488810968072&hsa_net=adwords&hsa_src=g&gad=1&gbraid=0AAAAApBT6qEjsr6y7cyFGozJ21Wr7vAM7&gclid=CjwKCAjwq4imBhBQEiwA9Nx1BgaFl5XB1UBqVa8zFLhnAei3WUUZ9irsHpjUR6L6Sx8WRMgXgbnjzRoCt3UQAvD_BwE";
                url[2] = "https://www.mindtools.com/a5g2h6s/golden-rules-of-goal-setting";
                url[3] = "https://positivepsychology.com/benefits-goal-setting/#:~:text=Goals%20give%20us%20a%20roadmap,also%20helps%20us%20prioritize%20things";
                url[4] = "https://time.com/5909923/how-to-set-goals/?amp=true";
                url[5] = "https://littlecoffeefox.com/setting-goals-and-rewards/    ";
                url[6] = "https://thebetterbusiness.network/goal-setting-and-rewards/amp/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url[randomNumber]));
                startActivity(intent);
            }
        });

        css2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationView = findViewById(R.id.cat);
                animationView.addAnimatorListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animationView.setVisibility(View.GONE);
                    }
                });
                animationView.playAnimation();
            }
        });

        css.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NoteActivity.class);
                startActivity(intent);
            }
        });
    }

}