package com.github.jlcool.shorebird.services;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

import java.io.File;
import java.io.IOException;

public class ProgressRequestBody extends RequestBody {
    private File file;
    private UploadCallback callback;

    public ProgressRequestBody(File file, UploadCallback callback) {
        this.file = file;
        this.callback = callback;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse("application/vnd.android.package-archive");
    }

    @Override
    public long contentLength() {
        return file.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        Source source = Okio.source(file);

        long total = contentLength();
        long totalRead = 0;

        long read;

        while ((read = source.read(sink.getBuffer(), 2048)) != -1) {
            totalRead += read;
            int progress = (int) ((totalRead * 100) / total);

            if (callback != null)
                callback.onProgressUpdate(progress);
        }
    }

    public interface UploadCallback {
        void onProgressUpdate(int percentage);
    }
}