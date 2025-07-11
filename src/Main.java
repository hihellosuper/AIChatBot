import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.Scanner;
import org.json.JSONObject;

class AssemblyAIChatbot {

    // Put your AssemblyAI API Key here
    private static final String API_KEY = " ";

    public static void main(String[] args) throws Exception {
        // Path to your audio file
        String audioFilePath = "Audio_path.mp3";

        // 1. Upload the audio file
        String uploadUrl = uploadAudioFile(audioFilePath);
        System.out.println("Uploaded audio URL: " + uploadUrl);

        // 2. Request transcription
        String transcriptId = requestTranscription(uploadUrl);
        System.out.println("Transcript ID: " + transcriptId);

        // 3. Poll for completion
        String transcribedText = pollTranscription(transcriptId);
        System.out.println("Transcribed text: " + transcribedText);

        // 4. Pass to chatbot logic (simple echo here)
        String chatbotResponse = generateChatbotResponse(transcribedText);
        System.out.println("Chatbot says: " + chatbotResponse);
    }

    private static String uploadAudioFile(String filePath) throws Exception {
        byte[] fileBytes = Files.readAllBytes(new File(filePath).toPath());

        URL url = new URL("https://api.assemblyai.com/v2/upload");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("authorization", API_KEY);
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(fileBytes);
        }

        InputStream responseStream = conn.getInputStream();
        String response = new String(responseStream.readAllBytes());
        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.getString("upload_url");
    }

    private static String requestTranscription(String audioUrl) throws Exception {
        URL url = new URL("https://api.assemblyai.com/v2/transcript");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("authorization", API_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        JSONObject requestBody = new JSONObject();
        requestBody.put("audio_url", audioUrl);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(requestBody.toString().getBytes());
        }

        InputStream responseStream = conn.getInputStream();
        String response = new String(responseStream.readAllBytes());
        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.getString("id");
    }

    private static String pollTranscription(String transcriptId) throws Exception {
        URL url = new URL("https://api.assemblyai.com/v2/transcript/" + transcriptId);
        while (true) {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("authorization", API_KEY);

            InputStream responseStream = conn.getInputStream();
            String response = new String(responseStream.readAllBytes());
            JSONObject jsonResponse = new JSONObject(response);

            String status = jsonResponse.getString("status");
            if ("completed".equals(status)) {
                return jsonResponse.getString("text");
            } else if ("failed".equals(status)) {
                throw new RuntimeException("Transcription failed.");
            }

            System.out.println("Transcription status: " + status);
            Thread.sleep(5000); // wait before polling again
        }
    }
    private static String generateChatbotResponse(String input) {
        String lower = input.toLowerCase();
        if (lower.contains("hello") ) {
            return "Hi there! I'm your friendly assistant. What can I do for you today?";
        } else {
            return "Hmm... I didn't quite get that. Could you say it differently? ";
        }
    }
}
