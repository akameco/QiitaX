package io.github.akameco.qiitax.model;


import android.support.annotation.Nullable;

public class User extends EasyParcelable {
	public static final Creator<User> CREATOR = new EasyCreator<>(User.class);
	public String id;
	@Nullable public String name;
	public String profile_image_url;
	@Nullable public String description;
	@Nullable public String github_login_name;
	@Nullable public String twitter_screen_name;
}
