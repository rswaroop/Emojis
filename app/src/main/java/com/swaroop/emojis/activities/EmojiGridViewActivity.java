package com.swaroop.emojis.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.swaroop.emojis.R;
import com.swaroop.emojis.adapters.EmojiViewAdapter;
import com.swaroop.emojis.model.Emoji;
import com.swaroop.emojis.utils.Common;
import com.swaroop.emojis.utils.OkHttp3Factory;
import com.swaroop.emojis.utils.loggingutils.Logging;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by swaroop.rayudu on 11/9/16.
 */

public class EmojiGridViewActivity extends AppCompatActivity {
    private static final String EMOJIS_FETCH_URL = "https://api.github.com/emojis";
    private final Logger mLog = LoggerFactory.getLogger(getClass());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emojis_gridview);

        Logging.init(true);

        GridView emojiGridView = (GridView) findViewById(R.id.emojiView);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        ArrayList<Emoji> emojis = new ArrayList<>();
        EmojiViewAdapter emojiViewAdapter = new EmojiViewAdapter(this, R.layout.single_emoji_view, emojis);
        emojiGridView.setAdapter(emojiViewAdapter);

        final Context context = getApplicationContext();

        emojiGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                final Emoji emoji = (Emoji) parent.getItemAtPosition(position);

                Intent intent = new Intent(context, EmojiDetailActivity.class);
                intent.putExtra("name", emoji.name);
                intent.putExtra("imageUrl", emoji.imageUrl);

                startActivity(intent);
            }
        });

        fetchAsync(context, emojis, emojiViewAdapter, progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void fetchAsync(final Context context, final ArrayList<Emoji> emojis, final EmojiViewAdapter emojiViewAdapter, final ProgressBar progressBar) {
        Request request = new Request.Builder().url(EMOJIS_FETCH_URL).build();

        OkHttp3Factory.getOkHttpInstance(context).newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Common.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Failed to fetch emojis!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject result = new JSONObject(response.body().string());
                        Iterator<String> keys = result.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            emojis.add(new Emoji(key, result.optString(key)));
                        }
                    } catch (JSONException e) {
                        mLog.error("Failed to parse response : ", e);
                    }
                } else {
                    mLog.error("Something unexpected happened!");
                }

                Common.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        emojiViewAdapter.setData(emojis);
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}
