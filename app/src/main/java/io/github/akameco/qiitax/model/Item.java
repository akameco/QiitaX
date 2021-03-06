package io.github.akameco.qiitax.model;


import java.util.ArrayList;

public class Item extends EasyParcelable {
	public static final Creator<Item> CREATOR = new EasyCreator<>(Item.class);
	public String title;
	public String body;
	public String rendered_body;
	public String url;
	public String id;
	public ArrayList<Tag> tags;
	public User user;

	public Item(String title, String body)  {
		this.title = title;
		this.body = body;
	}
}
