package a2ndrade.giphygallery.data;

import java.util.ArrayList;
import java.util.List;

import a2ndrade.giphygallery.data.model.Data;
import a2ndrade.giphygallery.data.model.Images;
import a2ndrade.giphygallery.data.model.Result;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GiphyService {
    private static final GiphyService INSTANCE = new GiphyService();
    private GiphyApi api;

    private GiphyService() {
        // Singleton
        createApi();
    }

    private void createApi() {
        if (api == null) {
            api = new RestAdapter.Builder()
                    .setEndpoint(GiphyApi.ENDPOINT)
                    .build()
                    .create(GiphyApi.class);
        }
    }

    public void fetchPhotos(String searchTerm, final GiphyImagesListener listener) {
        final Callback<Result> callback = new Callback<Result>() {
            @Override
            public void success(Result result, Response response) {
                if (result == null || listener == null) {
                    return;
                }

                final List<Data> data = result.data;
                final int size = data.size();
                List<Images> images = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    images.add(data.get(i).images);
                }
                listener.onImagesLoaded(images);
            }

            @Override
            public void failure(RetrofitError error) {
                if (listener != null) {
                    listener.onFailure(error);
                }
            }
        };
        if (searchTerm == null || searchTerm.isEmpty()) {
            api.getImages(callback);
        } else {
            api.getImages(searchTerm, callback);
        }
    }

    public static GiphyService getInstance() {
        return INSTANCE;
    }

    public interface GiphyImagesListener {
        void onImagesLoaded(List<Images> images);
        void onFailure(Throwable error);
    }
}
