package pl.pwr.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import pl.pwr.model.BookInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BooksApiClient {

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();
    private static final String URL = "https://books-api7.p.rapidapi.com/books/get/random/";
    private static final String RAPIDAPI_KEY = "1d4debc36amshfe9a73850a510cep11697ajsna3d25760ffdd";
    private static final String RAPIDAPI_HOST = "books-api7.p.rapidapi.com";

    public List<BookInfo> fetchRandomBooks(int numberOfBooks) {
        List<BookInfo> books = new ArrayList<>();

        for (int i = 0; i < numberOfBooks; i++) {
            Request request = new Request.Builder()
                    .url(URL)
                    .get()
                    .addHeader("X-RapidAPI-Key", RAPIDAPI_KEY)
                    .addHeader("X-RapidAPI-Host", RAPIDAPI_HOST)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                assert response.body() != null;
                String responseBody = response.body().string();
                JSONObject bookJson = new JSONObject(responseBody);

                JSONObject author = bookJson.getJSONObject("author");
                String title = bookJson.getString("title");
                int pages = bookJson.getInt("pages");
                String authorFirstName = author.getString("first_name");
                String authorLastName = author.getString("last_name");

                books.add(new BookInfo(title, authorFirstName, authorLastName, pages));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return books;
    }
}

