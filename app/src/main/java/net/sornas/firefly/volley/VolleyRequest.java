package net.sornas.firefly.volley;

import com.android.volley.Request.Method;

public class VolleyRequest {
    private int method;
    private String endpoint;

    private static final String BASE_URL = "https://firefly.xn--srns-noa9h.se/api/v1/";

    public static VolleyRequest listAccounts = new VolleyRequest(Method.GET, BASE_URL + "accounts");

    private VolleyRequest(int method, String endpoint) {
        this.method = method;
        this.endpoint = endpoint;
    }

    public int getMethod() {
        return method;
    }

    public String getEndpoint() {
        return endpoint;
    }

}
