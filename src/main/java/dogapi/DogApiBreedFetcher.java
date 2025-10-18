package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */
    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        String url = "https://dog.ceo/api/breed/" + breed + "/list";

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                throw new BreedNotFoundException("API request failed with HTTP code: " + response.code());
            }

            String responseBody = response.body().string();
            if (responseBody == null || responseBody.isEmpty()) {
                throw new BreedNotFoundException("API returned an empty response.");
            }

            JSONObject jsonObject = new JSONObject(responseBody);

            String status = jsonObject.getString("status");
            if (!"success".equals(status)) {
                String errorMessage = jsonObject.optString("message", "Breed not found or API error.");
                throw new BreedNotFoundException(errorMessage);
            }

            JSONArray subBreedsJsonArray = jsonObject.getJSONArray("message");
            List<String> subBreedsList = new ArrayList<>();
            for (int i = 0; i < subBreedsJsonArray.length(); i++) {
                subBreedsList.add(subBreedsJsonArray.getString(i));
            }

            return subBreedsList;

        } catch (IOException | JSONException e) {
            throw new BreedNotFoundException("Failed to fetch or parse sub-breeds for: " + breed, e);
        }
    }
}