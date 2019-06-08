package net.sornas.firefly.http.volley;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
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

    public FireflyRequester(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    private void makeRequest(int method, String url, String token, final HttpCallback callback) {
        StringRequest stringRequest = new StringRequest(method,
                url,
                s -> {
                    FireflyResponse response = new Gson().fromJson(s, FireflyResponse.class);
                    if (response.links.next != null) {
                        makeRequest(method, response.links.next, token, callback);
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
        requestQueue.add(stringRequest);
    }

    public void makeRequest(VolleyRequest request, String token, final HttpCallback callback) {
        makeRequest(request.getMethod(), request.getEndpoint(), token, callback);
    }
}
