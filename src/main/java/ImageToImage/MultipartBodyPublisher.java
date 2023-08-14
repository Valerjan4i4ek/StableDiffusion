package ImageToImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.http.HttpRequest;

public class MultipartBodyPublisher {
    private static final String CRLF = "\r\n";
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final String boundary;
    private boolean closed = false;

    public MultipartBodyPublisher() {
        boundary = "===" + System.currentTimeMillis() + "===";
    }

    public MultipartBodyPublisher addFormField(String name, String value) {
        if (closed) throw new IllegalArgumentException("Body is closed");
        writeBoundary();
        writeLine("Content-Disposition: form-data; name=\"" + name + "\"");
        writeLine();
        writeLine(value);
        return this;
    }

    public MultipartBodyPublisher addFilePart(String fieldName, String fileName, byte[] fileBytes) {
        if (closed) throw new IllegalArgumentException("Body is closed");
        writeBoundary();
        writeLine("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + fileName + "\"");
        writeLine("Content-Type: application/octet-stream");
        writeLine();
        try {
            outputStream.write(fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public HttpRequest.BodyPublisher build() {
        if (!closed) {
            writeBoundary();
            writeLine("--");
            closed = true;
        }
        return HttpRequest.BodyPublishers.ofByteArray(outputStream.toByteArray());
    }

    private void writeBoundary() {
        writeLine("--" + boundary);
    }

    private void writeLine() {
        write(CRLF);
    }

    private void writeLine(String line) {
        write(line + CRLF);
    }

    private void write(String value) {
        try {
            outputStream.write(value.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
