package io.github.akameco.qiitax.adapter;


import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.github.akameco.qiitax.R;
import io.github.akameco.qiitax.model.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
	private final Context mContext;
	private List<Item> list = new ArrayList<>();

	public ItemAdapter(@NonNull final Context mContext) {
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

	public void setList(List<Item> data) {
		list = data;
		notifyDataSetChanged();
	}

	public class ItemViewHolder extends RecyclerView.ViewHolder {
		TextView mTextView;
		ImageView mAvater;
		TextView mSubTitle;

		ItemViewHolder(final View itemView) {
			super(itemView);
			// TODO data binding
			mTextView = (TextView) itemView.findViewById(R.id.tv_name);
			mAvater = (ImageView) itemView.findViewById(R.id.avater);
			mSubTitle = (TextView) itemView.findViewById(R.id.tv_sub_title);

			itemView.setOnClickListener(v -> {
				int position = getAdapterPosition();
				Item item = list.get(position);
				CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
				CustomTabsIntent customTabsIntent = builder.build();
				customTabsIntent.launchUrl(mContext, Uri.parse(item.url));
			});
		}

		void bind(int position) {
			Item item = list.get(position);
			String imageUrl = item.user.profile_image_url;
			mTextView.setText(item.title);
			mSubTitle.setText(item.url);
			if (imageUrl != null) {
				Picasso.with(mContext).load(imageUrl).into(mAvater);
			}
		}
	}
}
