package stocky;

import net.pushover.client.*;

import java.util.List;

/**
 * Created by erik on 09/03/14.
 */
public class PushoverNotifier {

    private static final String API_KEY = "aAuxBBMEw8H87H2Rm6f5qQo2zvnxi2";
    private PushoverClient client;
    private List<String> recipients;

    public PushoverNotifier(List<String> recipients) {
        client = new PushoverRestClient();
        this.recipients = recipients;
    }

    public void send(String title, String msg) {
        send(title, msg, null, null);
    }

    public void send(String title, String msg, String urlTitle, String url) {

        for (String recipient : recipients) {
            try {
                PushoverMessage.Builder builder = PushoverMessage.builderWithApiToken(API_KEY);
                builder.setUserId(recipient);
                builder.setMessage(msg);
                builder.setPriority(MessagePriority.NORMAL);
                builder.setTitle(title);
                builder.setTimestamp(System.currentTimeMillis() / 1000L);
                if (urlTitle != null && url != null) {
                    builder.setTitleForURL(urlTitle);
                    builder.setUrl(url);
                }
                Status result = client.pushMessage(builder.build());
                if (result.getStatus() != 1) {
                    System.err.println(String.format("Failed to send Pushover to: %s", recipient));
                }
            } catch (PushoverException e) {
                System.err.println(String.format("Error while sending Pushover to: %s (%s)", recipient, e.getMessage()));
            }
        }

    }

}
