package io.github.akameco.qiitax;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import io.github.akameco.qiitax.adapter.ItemAdapter;
import io.github.akameco.qiitax.api.QiitaService;
import io.github.akameco.qiitax.model.Item;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
	public static final String TAG = MainActivity.class.getSimpleName();
	public static final String API_URL = "https://qiita.com";
	RecyclerView mRecyclerView;
	private ItemAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();

		Retrofit retrofit = new Retrofit
			.Builder()
			.baseUrl(API_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.build();

		loadRepos(retrofit);
	}

	private void initView() {
		LinearLayoutManager manager = new LinearLayoutManager(
			MainActivity.this,
			LinearLayoutManager.VERTICAL,
			false
		);

		mRecyclerView = (RecyclerView) findViewById(R.id.listView);
		mRecyclerView.setLayoutManager(manager);
		mRecyclerView.setHasFixedSize(true);

		mAdapter = new ItemAdapter(this);
		mRecyclerView.setAdapter(mAdapter);
	}

	private void loadRepos(Retrofit retrofit) {
		QiitaService qiitaService = retrofit.create(QiitaService.class);
		qiitaService.items("android", null, null)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(this::render, this::errorHandler);
	}

	private void errorHandler(Throwable t) {
		t.printStackTrace();
		Log.d(TAG, String.valueOf(t));
	}

	private void render(List<Item> items) {
		mAdapter.setList(items);
	}
}
