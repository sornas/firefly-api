package net.sornas.firefly.api.http;

public interface ResponseCallback {
    void onSuccess(String response, Pagination pagination);
}
