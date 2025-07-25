# AssemblyAI Chatbot

This project is a simple Java application that uses the AssemblyAI API to transcribe audio files and generate basic chatbot responses based on the transcribed text.

## Features

- Uploads an audio file to AssemblyAI
- Requests transcription of the audio
- Polls for transcription completion
- Generates a simple chatbot response based on the transcribed text

## Requirements

- Java 17 or higher (project is set to use JDK 23)
- AssemblyAI API key
- [org.json](https://github.com/stleary/JSON-java) library (included as `json-20230227.jar`)

## Setup

1. **Clone the repository** and open it in your IDE.
2. **Add your AssemblyAI API key**  
   Edit [`src/Main.java`](src/Main.java) and set your API key:
   ```java
   private static final String API_KEY = "YOUR_ASSEMBLYAI_API_KEY";
   ```
3. **Place your audio file**  
   Put your audio file (e.g., `Audio_path.mp3`) in the project directory and update the path in `Main.java` if needed.

4. **Build and run the project**  
   Compile and run the `AssemblyAIChatbot` class.

## Usage

The application will:
- Upload the specified audio file to AssemblyAI
- Request a transcription
- Poll until the transcription is complete
- Print the transcribed text and a chatbot response

## Notes

- The chatbot logic is very simple and only responds to greetings.
- Make sure your audio file is supported by AssemblyAI (e.g., MP3, WAV).

## License

This project is for educational purposes.
