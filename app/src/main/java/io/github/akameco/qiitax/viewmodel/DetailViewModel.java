package io.github.akameco.qiitax.viewmodel;


import android.databinding.BaseObservable;

import io.github.akameco.qiitax.model.Item;

public class DetailViewModel extends BaseObservable {
	private Item item;
	public DetailViewModel(Item item) {
		this.item = item;
	}
	public Item getItem() {
		return this.item;
	}
}
