package a2ndrade.giphygallery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import a2ndrade.giphygallery.R;
import a2ndrade.giphygallery.data.GiphyService;
import a2ndrade.giphygallery.data.model.Images;
import a2ndrade.giphygallery.recycler.ImagesAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GiphyGalleryFragment extends Fragment implements GiphyService.GiphyImagesListener,
        ImagesAdapter.ImagesOnClickListener {
    private static final String TAG = GiphyGalleryFragment.class.getSimpleName();
    private static final String BUNDLE_KEY_LAST_SEARCH = "lastSearch";
    private static final String BUNDLE_KEY_IMAGES = "images";

    @BindView(R.id.image_grid)
    RecyclerView grid;
    @BindView(android.R.id.empty)
    ProgressBar empty;
    @BindView(R.id.no_images)
    TextView noImages;

    private ImagesAdapter adapter;
    private String lastSearch;
    private Unbinder unbinder;
    private ArrayList<Images> images;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            lastSearch = savedInstanceState.getString(BUNDLE_KEY_LAST_SEARCH);
            images = savedInstanceState.getParcelableArrayList(BUNDLE_KEY_IMAGES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragment_gallery, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new ImagesAdapter(LayoutInflater.from(getActivity()), GiphyGalleryFragment.this);
        grid.setAdapter(adapter);
        displayData(lastSearch);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_gallery_menu, menu);
        final MenuItem item = menu.findItem(R.id.menu_item_search);
        setupSearchView((SearchView) MenuItemCompat.getActionView(item));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_KEY_LAST_SEARCH, lastSearch);
        outState.putParcelableArrayList(BUNDLE_KEY_IMAGES, images);
    }

    private void displayData(final String searchTerm) {
        if (images != null) {
            onImagesLoaded(images);
            return;
        }
        GiphyService.getInstance().fetchPhotos(searchTerm, this);
    }

    private void setupSearchView(final android.support.v7.widget.SearchView searchView) {
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery(lastSearch, false);
            }
        });
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Update the last search
                lastSearch = query;
                grid.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
                displayData(query);
                adapter.clearItems();
                searchView.onActionViewCollapsed();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onImagesLoaded(List<Images> images) {
        if (!isAdded()) {
            return;
        }

        if (images.isEmpty()) {
            // Show empty screen
            noImages.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
            return;
        }

        this.images = (ArrayList<Images>) images;
        adapter.addItems(images);
        empty.setVisibility(View.GONE);
        noImages.setVisibility(View.GONE);
        grid.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailure(Throwable error) {
        Log.e(TAG, "Error retrieving images:", error);
    }

    @Override
    public void onClick(Images images) {
        Intent intent = new Intent(getActivity(), FullscreenActivity.class);
        intent.putExtra(FullscreenActivity.EXTRA_IMAGE, images);
        startActivity(intent);
    }
}
