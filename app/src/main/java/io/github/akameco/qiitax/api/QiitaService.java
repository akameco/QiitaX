package io.github.akameco.qiitax.api;

import java.util.List;

import io.github.akameco.qiitax.model.Item;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QiitaService {
	@GET("/api/v2/items/")
	Observable<List<Item>> items(
		@Query("query") String query,
		@Query("page") String page,
		@Query("per_page") String per_page
	);
}
