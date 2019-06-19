package net.sornas.firefly.volley;

import com.android.volley.Request.Method;

public class VolleyRequest {
    private int method;
    private String endpoint;

    public static VolleyRequest listAccounts = new VolleyRequest(Method.GET, "accounts");
    public static VolleyRequest listTransactions = new VolleyRequest(Method.GET, "transactions");

    private VolleyRequest(int method, String endpoint) {
        this.method = method;
        this.endpoint = endpoint;
    }

    int getMethod() {
        return method;
    }

    String getEndpoint() {
        return endpoint;
    }

}
