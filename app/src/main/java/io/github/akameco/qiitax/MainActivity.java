package io.github.akameco.qiitax;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import io.github.akameco.qiitax.adapter.ItemAdapter;
import io.github.akameco.qiitax.api.QiitaService;
import io.github.akameco.qiitax.model.Item;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
	public static final String TAG = MainActivity.class.getSimpleName();
	public static final String API_URL = "https://qiita.com";
	public static final String STATE_ITEM_LIST = "item_list";

	private ItemAdapter mAdapter;
	private Retrofit mRetrofit;
	private ArrayList<Item> itemList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();

		mRetrofit = new Retrofit
			.Builder()
			.baseUrl(API_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.build();

		if (savedInstanceState != null) {
			itemList = savedInstanceState.getParcelableArrayList(STATE_ITEM_LIST);
		}

		// TODO 前回の値をプリファレンスに保存
		String firstQuery = "android";
		if (itemList == null || itemList.isEmpty()) {
			loadItems(firstQuery);
		} else {
			render(itemList);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList(STATE_ITEM_LIST, itemList);
		super.onSaveInstanceState(outState);
	}

	private void initView() {
		setContentView(R.layout.activity_main);

		LinearLayoutManager manager = new LinearLayoutManager(
			MainActivity.this,
			LinearLayoutManager.VERTICAL,
			false
		);

		final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.listView);
		mRecyclerView.setLayoutManager(manager);
		mRecyclerView.setHasFixedSize(true);

		mAdapter = new ItemAdapter(this);
		mRecyclerView.setAdapter(mAdapter);
	}

	private void loadItems(String query) {
		query = (query == null || query.equals("")) ? "android" : query;
		QiitaService qiitaService = mRetrofit.create(QiitaService.class);
		qiitaService.items(query, null, null)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(this::render, Timber::e);
	}

	private void render(List<Item> items) {
		itemList = new ArrayList<>(items);
		mAdapter.setList(itemList);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.search, menu);

		MenuItem menuItem = menu.findItem(R.id.action_search);
		final SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(menuItem);

		if (mSearchView == null) {
			return super.onCreateOptionsMenu(menu);
		}

		mSearchView.setIconified(true);
		mSearchView.setSubmitButtonEnabled(false);

		mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(final String query) {
				loadItems(query);
				return true;
			}

			@Override
			public boolean onQueryTextChange(final String newText) {
				return false;
			}
		});

		return super.onCreateOptionsMenu(menu);
	}
}
