package a2ndrade.giphygallery.recycler;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import a2ndrade.giphygallery.R;
import a2ndrade.giphygallery.data.model.Image;
import a2ndrade.giphygallery.data.model.Images;

public class ImagesAdapter  extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {

    private final LayoutInflater inflater;
    private final ImagesOnClickListener listener;
    private final Resources res;
    private ArrayList<Images> items = new ArrayList<>();

    public ImagesAdapter(LayoutInflater inflater, ImagesOnClickListener listener) {
        this.inflater = inflater;
        this.listener = listener;
        res = inflater.getContext().getResources();
    }

    public void addItems(List<Images> newItems) {
        final int insertRangeStart = getItemCount();
        items.addAll(newItems);
        notifyItemRangeInserted(insertRangeStart, newItems.size());
    }

    public void clearItems() {
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(inflater.inflate(R.layout.image_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        final Images images = items.get(position);
        final View itemView = holder.itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = holder.getAdapterPosition();
                if (position == RecyclerView.NO_POSITION || listener == null) {
                    return;
                }

                listener.onClick(items.get(position));
            }
        });

        final Image downSized = images.downsized_still;
        if (downSized == null) {
            return;
        }

        final String url = downSized.url;
        if (url != null && !url.isEmpty()) {
            Glide.with(itemView.getContext())
                    .load(url)
                    .placeholder(R.color.placeholder)
                    .into(holder.image);
            final String size = String.format(res.getString(R.string.image_size), downSized.height, downSized.width);
            (holder).title.setText(size);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView image;

        public ImageViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            image = (ImageView) itemView.findViewById(R.id.photo);
        }
    }

    public interface ImagesOnClickListener {
        void onClick(Images images);
    }
}


