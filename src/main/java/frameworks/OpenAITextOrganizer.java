package frameworks;

import api.Examples;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import org.json.JSONObject;
import org.json.JSONArray;
import use_cases.file_upload.interfaces.TextOrganizerInterface;

import java.io.IOException;
public class OpenAITextOrganizer implements TextOrganizerInterface {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");

    @Override
    public String organizeText(String jumbledText) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS) // Connection timeout
                .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)   // Write timeout
                .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)    // Read timeout
                .build();

        //Build the JSON payload
        //System prompt that gives the AI its "role"
        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", "You are an AI assistant that processes jumbled restaurant bills. "
                + "Extract only relevant information like dish names, quantities, and prices, and respond with the data formatted as JSON."
                + "If the quantity is not specified, assume it to be 1");


        //An example of an interaction with a desired output
        JSONObject exampleMessage = new JSONObject();
        exampleMessage.put("role", "user");
        exampleMessage.put("content", Examples.scrambledText1);
        JSONObject exampleResponse = new JSONObject();
        exampleResponse.put("role", "assistant");
        exampleResponse.put("content", Examples.desiredOutput1);

        //The actual scrambled text to be reorganized
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", jumbledText);

        JSONArray messages = new JSONArray();
        messages.put(systemMessage);
        messages.put(exampleMessage);
        messages.put(exampleResponse);
        messages.put(userMessage);

        JSONObject payload = new JSONObject();
        payload.put("model", "gpt-4"); // Specify the GPT model
        payload.put("messages", messages);

        // Create the HTTP request
        RequestBody body = RequestBody.create(payload.toString(), MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(body)
                .build();

        // Execute the request
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();

                // Parse the OpenAI response to extract the content field
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
                JsonArray choices = jsonResponse.getAsJsonArray("choices");
                JsonObject firstChoice = choices.get(0).getAsJsonObject();
                JsonObject message = firstChoice.getAsJsonObject("message");
                return message.get("content").getAsString(); // This is the actual JSON string
            } else {
                throw new IOException("Unexpected response: " + response);
            }
        }
    }
}
