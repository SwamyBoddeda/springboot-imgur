
package com.syncb.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Component
public class ImgurApiClient {
    private final WebClient client;
    private final String clientId;

    public ImgurApiClient(@Value("${imgur.base-url}") String baseUrl, @Value("${imgur.client-id}") String clientId) {
        this.clientId = clientId;
        this.client = WebClient.builder().baseUrl(baseUrl).build();
    }

    public static class UploadData { public String id; public String deletehash; public String link; }
    public static class UploadResponse { public boolean success; public int status; public UploadData data; }

    public UploadData upload(byte[] bytes, String title) {
        String b64 = Base64.getEncoder().encodeToString(bytes);
        MultiValueMap<String,String> form = new LinkedMultiValueMap<>();
        form.add("image", b64);
        if (title != null) form.add("title", title);

        UploadResponse resp = client.post().uri("/image")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", "Client-ID " + clientId)
                .body(BodyInserters.fromFormData(form))
                .retrieve().bodyToMono(UploadResponse.class).block();
        if (resp == null || !resp.success) throw new RuntimeException("Imgur upload failed");
        return resp.data;
    }

    public void delete(String deleteHashOrId) {
        client.delete().uri("/image/{id}", deleteHashOrId)
                .header("Authorization", "Client-ID " + clientId)
                .retrieve().bodyToMono(Void.class).onErrorResume(e -> Mono.empty()).block();
    }
}
