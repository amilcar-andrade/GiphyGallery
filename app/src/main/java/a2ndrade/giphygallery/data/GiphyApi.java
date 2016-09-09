package a2ndrade.giphygallery.data;

import a2ndrade.giphygallery.data.model.Result;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Modeling the Giphy API.
 */
public interface GiphyApi {

    // Base url for all requests: http://api.giphy.com/
    String ENDPOINT = "http://api.giphy.com";

    // Trending EndPoint
    // Changing to 33 instead of the default (25)
    @GET("/v1/gifs/trending?api_key=dc6zaTOxFJmzC&limit=33")
    void getImages(Callback<Result> data);

    // Search Endpoint
    // Changing to 33 instead of the default (25)
    @GET("/v1/gifs/search?api_key=dc6zaTOxFJmzC&limit=33")
    void getImages(@Query("q") String text, Callback<Result> data);
}
