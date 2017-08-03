package kr.co.bootpay.javaApache;

import com.google.gson.Gson;
import kr.co.bootpay.javaApache.model.request.Cancel;
import kr.co.bootpay.javaApache.model.request.Confirm;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * Created by ehowlsla on 2017. 8. 3..
 */
public class BootpayApi {

    private final String BASE_URL = "https://api.bootpay.co.kr/";
    private final String URL_CONFIRM = BASE_URL + "receipt";
    private final String URL_CANCEL = BASE_URL + "cancel";

    private String application_id;
    private String pk;

    public BootpayApi() {}
    public BootpayApi(String application_id, String pk) {
        this.application_id = application_id;
        this.pk = pk;
    }

    public HttpResponse confirm(Confirm confirm) throws Exception {
        confirm = validConfirm(confirm);

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = getPost(URL_CONFIRM, new StringEntity(new Gson().toJson(confirm)));
        return client.execute(post);
    }

    public HttpResponse cancel(Cancel cancel)throws Exception {
        cancel = validCancel(cancel);

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = getPost(URL_CANCEL, new StringEntity(new Gson().toJson(cancel)));
        return client.execute(post);
    }

    private HttpPost getPost(String url, StringEntity entity) {
        HttpPost post = new HttpPost(url);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-Type", "application/json");

        post.setEntity(entity);
        return post;
    }

    private Confirm validConfirm(Confirm confirm) throws Exception {
        if(confirm.application_id == null || confirm.application_id.isEmpty()) {
            if(this.application_id == null || this.application_id.isEmpty()) throw new Exception("application_id 값이 비어있습니다.");
            confirm.application_id = this.application_id;
        }

        if(confirm.pk == null || confirm.pk.isEmpty()) {
            if(this.pk == null || this.pk.isEmpty()) throw new Exception("pk 값이 비어있습니다.");
            confirm.pk = this.pk;
        }
        if(confirm.receipt_id == null || confirm.receipt_id.isEmpty()) throw new Exception("receipt_id 값이 비어있습니다.");
        return confirm;
    }

    private Cancel validCancel(Cancel cancel) throws Exception {
        if(cancel.application_id == null || cancel.application_id.isEmpty()) {
            if(this.application_id == null || this.application_id.isEmpty()) throw new Exception("application_id 값이 비어있습니다.");
            cancel.application_id = this.application_id;
        }

        if(cancel.pk == null || cancel.pk.isEmpty()) {
            if(this.pk == null || this.pk.isEmpty()) throw new Exception("pk 값이 비어있습니다.");
            cancel.pk = this.pk;
        }
        if(cancel.receipt_id == null || cancel.receipt_id.isEmpty()) throw new Exception("receipt_id 값이 비어있습니다.");
        if(cancel.name == null || cancel.name.isEmpty()) throw new Exception("name 값이 비어있습니다.");
        if(cancel.reason == null || cancel.reason.isEmpty()) throw new Exception("reason 값이 비어있습니다.");

        return cancel;
    }
}
