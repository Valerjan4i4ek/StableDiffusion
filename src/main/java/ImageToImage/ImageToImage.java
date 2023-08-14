package ImageToImage;

import TextToImage.Answer;
import com.google.gson.Gson;
import org.apache.http.entity.ContentType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class ImageToImage {
    private static final String API_URL = "https://api.stability.ai/v1/generation/stable-diffusion-xl-1024-v1-0/image-to-image";
    private static final String INIT_IMAGE_PNG = "init_image.png";
    private static final String API_KEY = "sk-Kg4TBG2SKF66Bghc5dhl0mNCO5dZsAGeGY86ucRDA5o0DVBP";
    public static void main(String[] args) {
        try {
            File initImageFile = new File(INIT_IMAGE_PNG);
            byte[] initImageBytes = Files.readAllBytes(initImageFile.toPath());

//            ParamsToJSON paramsToJSON = new ParamsToJSON("", 1);
//            List<ParamsToJSON> paramsToJSONList = new ArrayList<>();
//            paramsToJSONList.add(paramsToJSON);

            HttpClient client = HttpClient.newHttpClient();

            MultipartBodyPublisher multipartBody = new MultipartBodyPublisher()
                    .addFilePart("init_image", INIT_IMAGE_PNG, initImageBytes)
                    .addFormField("init_image_mode", "IMAGE_STRENGTH")
                    .addFormField("image_strength", "0.35")
                    .addFormField("samples", "1")
                    .addFormField("steps", "50")
                    .addFormField("seed", "0")
                    .addFormField("cfg_scale", "7")
                    .addFormField("style_preset", "enhance")
                    .addFormField("text_prompts[0][text]", "")
                    .addFormField("text_prompts[0][weight]", "1");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "multipart/form-data")
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(multipartBody.build())
                    .build();

//            HttpClient client = HttpClient.newHttpClient();
//
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create(API_URL))
//                    .header("Content-Type", "multipart/form-data")
//                    .header("Accept", "application/json")
//                    .header("Authorization", "Bearer " + API_KEY)
//                    .POST(HttpRequest.BodyPublishers.ofString(jsonString.toString()))
//                    .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response);
                int statusCode = response.statusCode();
                String responseBody = response.body();

                System.out.println(responseBody);

                if (statusCode == 200) {
                    Answer answer = new Gson().fromJson(responseBody, Answer.class);
                    String imageUrl = answer.getArtifacts().get(0).getBase64();
                    byte[] imageBytes = Base64.getDecoder().decode(imageUrl);


                    InputStream in = new ByteArrayInputStream(imageBytes);
                    BufferedImage image = ImageIO.read(in);
                    if (image != null) {
                        File outputFile = new File("output_image_to_image.png");
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
//            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//            builder.addTextBody("image_strength", "0.35");
//            builder.addTextBody("init_image_mode", "IMAGE_STRENGTH");
//            builder.addTextBody("text_prompts[0][text]", "");
//            builder.addTextBody("cfg_scale", "7");
//            builder.addTextBody("clip_guidance_preset", "FAST_BLUE");
//            builder.addTextBody("samples", "1");
//            builder.addTextBody("steps", "30");
//            HttpEntity httpEntity = builder.build();




//            String boundary = "---------------------------" + System.currentTimeMillis();
//
//            HttpURLConnection connection = (HttpURLConnection) new URL(API_URL).openConnection();
//            connection.setRequestMethod("POST");
//            connection.setDoOutput(true);
//            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
//            connection.setRequestProperty("Accept", "application/json");
//            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
//
//            OutputStream os = connection.getOutputStream();
//
//            String formData = "--" + boundary + "\r\n" +
//                    "Content-Disposition: form-data; name=\"init_image\"; filename=\"" + initImageFile.getName() + "\"\r\n" +
//                    "Content-Type: image/png\r\n\r\n";
//            System.out.println(formData);
//
//            os.write(formData.getBytes(StandardCharsets.UTF_8));
//            os.write(initImageBytes);
//            os.write(("\r\n--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));
//            os.flush();
//
//            int responseCode = connection.getResponseCode();
//            System.out.println(responseCode);
//
//            if (responseCode != 200) {
//                throw new IOException("Non-200 response: " + connection.getResponseMessage());
//            }
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            StringBuilder response = new StringBuilder();
//            String inputLine;
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//            JSONObject responseJSON = new JSONObject(response.toString());
//
//            JSONArray artifacts = responseJSON.getJSONArray("artifacts");
//            for (int i = 0; i < artifacts.length(); i++) {
//                JSONObject image = artifacts.getJSONObject(i);
//                String base64Data = image.getString("base64");
//                byte[] imageData = Base64.getDecoder().decode(base64Data);
//                String outputPath = outputDirectory + "img2img_" + image.getInt("seed") + ".png";
//                java.nio.file.Files.write(Paths.get(outputPath), imageData);
//            }


