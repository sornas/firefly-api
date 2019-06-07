package net.sornas.firefly.http.volley;

import android.content.Context;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import net.sornas.firefly.MainActivity;
import net.sornas.firefly.http.AccountResponse;
import net.sornas.firefly.http.HttpCallback;

import java.util.HashMap;
import java.util.Map;

public class FireflyRequester {
    private class FireflyResponse {
        private class ResponseMeta {
            private ResponseMetaPagination pagination;
            private class ResponseMetaPagination {
                private int total;
                private int count;
                private int per_page;
                private int current_page;
            }
        }
        private class ResponseLinks {
            private String self = null;
            private String first = null;
            private String prev = null;
            private String next = null;
            private String last = null;
        }
        private ResponseMeta meta;
        private ResponseLinks links;

    }

    private RequestQueue requestQueue;
    private final String baseAPI = "https://firefly.xn--srns-noa9h.se/api/v1/";
    private final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImRlOGZkMTE0ZmM5ZGExMjc1Njg5Mzk5ZGU3NDgxZTk4NTYyZGY0ODFiY2IyYjBlMDZjOWEzN2VjYTVlOGY3NjMyZWRjY2NiOGJiZjA4NGUyIn0.eyJhdWQiOiIxIiwianRpIjoiZGU4ZmQxMTRmYzlkYTEyNzU2ODkzOTlkZTc0ODFlOTg1NjJkZjQ4MWJjYjJiMGUwNmM5YTM3ZWNhNWU4Zjc2MzJlZGNjY2I4YmJmMDg0ZTIiLCJpYXQiOjE1NTg0NzYyNTUsIm5iZiI6MTU1ODQ3NjI1NSwiZXhwIjoxNTkwMDk4NjU1LCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.AMd23IRigU_LZPLGm50OAWkjFp9_IRKELkBetdW6cDkauq0_zzDdCU2e6-uHnztBXXfij68MigriePDYX-hfP5yAVbhm8bBdEMlviYIOs4b1tfcQdspTUQuBRis1DOP_VDxdrSDG75yPLpoXYeslcGquWcTI49RE39U1bVmMMSPrYgJzFqzn9hPJDf9hvssvc2mo-P4GLRbhf6VJwK9xbp1HDX3ObEABNd5g32iVJuT94Sz_UPxKljJ6btvNlWVRdAHD0yHIapf7Mssz8FNRSnj4DjpD_dMv4-Mri9vqpTtAkbIyLyB7GEKYRH7R--AkB9aL53fm227Ro6jWaMPdToMKZwjJ-pIi1qCmRAh1uFkFjzMp0iYedZN-ltuYI6qkLSBwWoMBUhKEUuUazC097PyGZd6azfcXJ3z5DCaFcgzKs9LpNrrB4CnZshFLKLmI9KehPXYcSc0imXiH_ZH4vLhxQGwZ0Q14jyCA5p6YIo8m0P3_XFhpAt5lfhfm3y6XSLxEO1dCZLrwAKQlqLC6TKpZ3XBvvuNlO2xsSXFtwhsr3Pd6H1KqBwrUonqCdzhyZC6LexUaeB4F2sPYBuccOVQA9y81vdgbK6kLVjKfQC2sL-rHOirfjGH5yW18i32a-yMYKPVUcilZSuPdvcGViMr_LhbmnD5brYc27dSdqSc";

    public FireflyRequester(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    private void makeRequest(int method, String endpoint, String token, final HttpCallback callback) {
        StringRequest request = new StringRequest(method,
                baseAPI + endpoint,
                s -> {
                    FireflyResponse response = new Gson().fromJson(s, FireflyResponse.class);
                    if (response.links.next != null) {
                        StringRequest nextRequest = new StringRequest(
                                method,
                                response.links.next,
                                r -> callback.onSuccess(r),
                                e -> {})
                        {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<>();
                                headers.put("Authorization", "Bearer " + token);
                                return headers;
                            }
                        };
                        requestQueue.add(nextRequest);
                    }
                    callback.onSuccess(s);
                },
                e -> {})
            {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }
            };
        requestQueue.add(request);
    }

    public void listAccounts(final HttpCallback callback) {
        makeRequest(Request.Method.GET, "accounts", token, callback);
    }
}
