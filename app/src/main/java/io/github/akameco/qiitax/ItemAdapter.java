package io.github.akameco.qiitax;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.akameco.qiitax.model.Item;

class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
	private final Context mContext;
	private List<Item> list = new ArrayList<>();

	ItemAdapter(@NonNull final Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public ItemViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
		view.setFocusable(true);
		return new ItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ItemViewHolder holder, final int position) {
		holder.bind(position);
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	void setList(List<Item> data) {
		list = data;
		notifyDataSetChanged();
	}

	class ItemViewHolder extends RecyclerView.ViewHolder {
		TextView mTextView;

		ItemViewHolder(final View itemView) {
			super(itemView);
			mTextView = (TextView) itemView.findViewById(R.id.tv_name);
			itemView.setOnClickListener(v -> {
				int position = getAdapterPosition();
				Item item = list.get(position);
				Intent intent = new Intent(mContext, DetailActivity.class);
				intent.putExtra(DetailActivity.TITLE, item.title);
				intent.putExtra(DetailActivity.BODY, item.body);
				mContext.startActivity(intent);
			});
		}

		void bind(int position) {
			Item item = list.get(position);
			mTextView.setText(item.title);
		}
	}
}
