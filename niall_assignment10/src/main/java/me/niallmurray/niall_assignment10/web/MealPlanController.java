package me.niallmurray.niall_assignment10.web;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import me.niallmurray.niall_assignment10.dto.DayResponse;
import me.niallmurray.niall_assignment10.dto.WeekResponse;

@RestController
public class MealPlanController {

	private static final String APIKEY = "4c6d20b7b89f45d396ab0c8c004c8d27";

	@Value("${spoonacular.urls.base}${spoonacular.urls.mealplan}")
	private String spoonacularURL;

	public URI uriBuilder(String apiKey, String timeFrame, String numCalories, String diet, String exclusions) {

		return UriComponentsBuilder.fromHttpUrl(spoonacularURL)
				.queryParam("apiKey", APIKEY)
				.queryParam("timeFrame", timeFrame)
				.queryParamIfPresent("targetCalories", Optional.ofNullable(numCalories))
				.queryParamIfPresent("diet", Optional.ofNullable(diet))
				.queryParamIfPresent("exclude", Optional.ofNullable(exclusions))
				.build()
				.toUri();
	}

	@GetMapping("mealplanner/day")
	public ResponseEntity<DayResponse> getDayMeals(String numCalories, String diet, String exclusions) {
		String timeFrame = "day";
		URI uri = uriBuilder(APIKEY, timeFrame, numCalories, diet, exclusions);

		return new RestTemplate().getForEntity(uri, DayResponse.class);
	}

	@GetMapping("mealplanner/week")
	public ResponseEntity<WeekResponse> getWeekMeals(String numCalories, String diet, String exclusions) {
		String timeFrame = "week";
		URI uri = uriBuilder(APIKEY, timeFrame, numCalories, diet, exclusions);

		return new RestTemplate().getForEntity(uri, WeekResponse.class);
	}
}
