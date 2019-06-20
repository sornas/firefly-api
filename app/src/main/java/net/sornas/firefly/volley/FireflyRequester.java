package net.sornas.firefly.volley;

import android.content.Context;
import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import net.sornas.firefly.api.http.Pagination;
import net.sornas.firefly.api.http.ResponseCallback;

import java.util.HashMap;
import java.util.Map;

public class FireflyRequester {
    private class FireflyResponse {
        private class ResponseMeta {
            private Pagination pagination;
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

    public FireflyRequester(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    private void makeRequest(int method, String url, String token, final ResponseCallback callback) {
        Log.d("FireflyRequest", "Making request:\n\tMethod: " + method + "\n\turl: " + url +
                "\n\ttoken" + token);
        StringRequest stringRequest = new StringRequest(method,
                url,
                s -> {
                    FireflyResponse response = new Gson().fromJson(s, FireflyResponse.class);
                    if (response.links.next != null) {
                        makeRequest(method, response.links.next, token, callback);
                    }
                    Log.d("FireflyRequest", "Request success, returning " + response.meta.pagination.count +
                            " posts");
                    callback.onSuccess(s, response.meta.pagination);
                },
                e -> Log.e("FireflyRequest", "Error: " + e.toString()))
        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void makeRequest(VolleyRequest request, String token, String rootURL, final ResponseCallback responseCallback) {
        makeRequest(request.getMethod(), rootURL + request.getEndpoint(), token, responseCallback);
    }
}
