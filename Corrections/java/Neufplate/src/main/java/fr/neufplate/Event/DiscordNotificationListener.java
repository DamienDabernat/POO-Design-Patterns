package fr.neufplate.Event;

import fr.neufplate.Nft;
import fr.neufplate.User.User;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class DiscordNotificationListener implements EventListener {

    @Override
    public void update(EventType eventType, User user, String json) {
        sendWebhook(json);
    }

    public static void sendWebhook(String jsonToSend) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://discord.com/api/webhooks/..../....");

        try {
            StringEntity entity = null;
            entity = new StringEntity(jsonToSend);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            CloseableHttpResponse response = client.execute(httpPost);
            //System.out.println(response.getStatusLine().getStatusCode());
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
