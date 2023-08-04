import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class TextToImage {
    public static void main(String[] args) {
        String apiKey = "sk-Kg4TBG2SKF66Bghc5dhl0mNCO5dZsAGeGY86ucRDA5o0DVBP";
        String apiUrl = "https://api.stability.ai/v1/generation/stable-diffusion-xl-beta-v2-2-2/text-to-image";

        JSONArray textPrompts = new JSONArray();
        textPrompts.put("Gray Wolf");

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("text_prompts", textPrompts);

        System.out.println("JSON Request Body: " + jsonBody.toString());

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody.toString()))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            String responseBody = response.body();

            if (statusCode == 200) {
                JSONObject jsonResponse = new JSONObject(responseBody);
                String imageUrl = jsonResponse.getString("image_url");

                BufferedImage image = ImageIO.read(new URL(imageUrl));
                File outputFile = new File("output_image.png");
                ImageIO.write(image, "png", outputFile);

                System.out.println("Зображення успішно збережено: " + outputFile.getPath());
            } else {
                System.err.println("Помилка при отриманні зображення. Код: " + statusCode);
                System.err.println("Повідомлення про помилку: " + responseBody);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

