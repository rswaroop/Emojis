package com.swaroop.emojis.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.swaroop.emojis.R;
import com.swaroop.emojis.utils.PicassoFactory;

/**
 * Created by swaroop.rayudu on 11/9/16.
 */

public class EmojiDetailActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emoji_fullscreenview);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();

        TextView titleView = (TextView) findViewById(R.id.name);
        titleView.setText(bundle.getString("name"));

        ImageView imageView = (ImageView) findViewById(R.id.emojiFull);

        String imageUrl = bundle.getString("imageUrl");

        PicassoFactory.getPicassoInstance(getApplicationContext())
                .load(TextUtils.isEmpty(imageUrl) ? "https://upload.wikimedia.org/wikipedia/commons/a/ac/No_image_available.svg" : imageUrl)
                .into(imageView);

        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.detail_background);
        ColorDrawable colorDrawable = new ColorDrawable(Color.BLACK);
        constraintLayout.setBackground(colorDrawable);
    }
}