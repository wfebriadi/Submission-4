package id.web.wfebriadi.submission4favoritemovie;

import android.content.CursorLoader;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import id.web.wfebriadi.submission4favoritemovie.adapter.FavoriteAdapter;
import id.web.wfebriadi.submission4favoritemovie.database.DatabaseContract;

import static id.web.wfebriadi.submission4favoritemovie.database.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    private FavoriteAdapter favoriteAdapter;
    ListView listFilm;
    FilmItem filmItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listFilm = (ListView)findViewById(R.id.lvfilm);
        favoriteAdapter = new FavoriteAdapter(this, null, true);
        listFilm.setAdapter(favoriteAdapter);
        listFilm.setOnItemClickListener(this);

        getSupportLoaderManager().initLoader(110, null, this);
    }
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(110, null, this);
    }
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new android.support.v4.content.CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        favoriteAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<Cursor> loader) {
        favoriteAdapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) favoriteAdapter.getItem(position);
        int id1 = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FilmColumns._ID));
        Intent intent = new Intent(MainActivity.this, FilmDetailActivity.class);
        intent.putExtra(FilmDetailActivity.TITLE, filmItem.getTitle());
        intent.putExtra(FilmDetailActivity.OVERVIEW, filmItem.getOverview());
        intent.putExtra(FilmDetailActivity.RELEASE_DATE, filmItem.getRelease());
        intent.putExtra(FilmDetailActivity.POSTER, filmItem.getPoster());
        intent.putExtra(FilmDetailActivity.BACKDROP_POSTER, filmItem.getBackdropPoster());
        intent.setData(Uri.parse(CONTENT_URI+"/"+id1));
        startActivity(intent);
    }
}
