package TextToImage;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;

import com.google.gson.Gson;

import javax.imageio.ImageIO;


public class TextToImage {
    private static final String API_URL = "https://api.stability.ai/v1/generation/stable-diffusion-xl-beta-v2-2-2/text-to-image";
    private static final String API_KEY = "sk-Kg4TBG2SKF66Bghc5dhl0mNCO5dZsAGeGY86ucRDA5o0DVBP";
    public static void main(String[] args) {
        Params params = new Params("Кошечка", 1);
        List<Params> paramsList = new ArrayList<>();
        paramsList.add(params);
        Body body = new Body(512, 512, 50, 0, 7, 1, "enhance", paramsList);
        Gson gson = new Gson();
        String s = gson.toJson(body);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(s.toString()))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            String responseBody = response.body();

            if (statusCode == 200) {
                Answer answer = gson.fromJson(responseBody, Answer.class);
                String imageUrl = answer.getArtifacts().get(0).getBase64();
                byte[] imageBytes = Base64.getDecoder().decode(imageUrl);


                InputStream in = new ByteArrayInputStream(imageBytes);
                BufferedImage image = ImageIO.read(in);
                if(image != null){
                    File outputFile = new File("output_image.png");
                    ImageIO.write(image, "png", outputFile);

                    System.out.println("Зображення успішно збережено: " + outputFile.getPath());
                }

            } else {
                System.err.println("Помилка при отриманні зображення. Код: " + statusCode);
                System.err.println("Повідомлення про помилку: " + responseBody);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

