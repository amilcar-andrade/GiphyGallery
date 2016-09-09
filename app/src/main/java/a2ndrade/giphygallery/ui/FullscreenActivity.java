package a2ndrade.giphygallery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import a2ndrade.giphygallery.R;
import a2ndrade.giphygallery.data.model.Images;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FullscreenActivity extends AppCompatActivity {
    public static final String EXTRA_IMAGE = "extraImage";

    @BindView(android.R.id.empty)
    ProgressBar progressBar;
    @BindView(R.id.fullscreen_image)
    ImageView imageView;
    @BindView(R.id.image_url)
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        ButterKnife.bind(this);

        final Intent intent = getIntent();
        final Images image = intent.getParcelableExtra(EXTRA_IMAGE);

        if (image == null || image.downsized_still.url == null) {
            // LATER: Notify the user
            return;
        }
        textView.setText(image.original_still.url);

        Glide.with(this)
                .load(image.original_still.url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .placeholder(R.color.colorPrimaryDark)
                .into(imageView);
    }
}
