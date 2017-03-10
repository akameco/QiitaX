package io.github.akameco.qiitax;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.github.akameco.qiitax.databinding.ActivityDetailBinding;
import io.github.akameco.qiitax.model.Item;
import io.github.akameco.qiitax.viewmodel.DetailViewModel;

public class DetailActivity extends AppCompatActivity {
	public static final String TAG = DetailActivity.class.getSimpleName();

	public static final String TITLE = "title";
	public static final String BODY = "body";

	private ActivityDetailBinding mBinding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView();
	}

	private void initView() {
		mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
		Intent intent = getIntent();
		String title = intent.getStringExtra(TITLE);
		String body = intent.getStringExtra(BODY);
		DetailViewModel viewModel = new DetailViewModel(new Item(title, body));
		mBinding.setViewModel(viewModel);
	}
}
