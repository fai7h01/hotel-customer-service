package com.hotel.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.sound.sampled.*;
import java.io.*;

@Component
public class Audio {

    private volatile AudioFormat format;
    private volatile TargetDataLine microphone;

    private final File wavFile;

    public Audio() {
        this(new AudioFormat(44100.0f, 16, 1, true, true), "AudioRecordBuffer.wav");
    }

    public Audio(AudioFormat format, String wavFileName) {
        this.format = format;
        this.wavFile = new File(wavFileName);
    }

    public void startRecording() {

        new Thread(() -> {
            try {
                // Ensure clean slate for new recording
                stopRecording();
                this.microphone = AudioSystem.getTargetDataLine(this.format);
                this.microphone.open(this.format);
                this.microphone.start();
                AudioSystem.write(new AudioInputStream(this.microphone), AudioFileFormat.Type.WAVE, this.wavFile);
            } catch (Exception e) {
                throw new RuntimeException("Recording failed", e);
            }
        }).start();
    }

    public void stopRecording() {
        if (this.microphone != null) {
            this.microphone.stop();
            this.microphone.close();
            this.microphone = null;
        }
    }

    public byte[] getLastRecording() {
        try {
            return (this.wavFile.exists())
                    ? StreamUtils.copyToByteArray(new BufferedInputStream(new FileInputStream(this.wavFile)))
                    : new byte[0];
        } catch (IOException e) {
            throw new RuntimeException("Failed to read recording", e);
        }
    }

    public static void play(byte[] waveData) { // java utils to play wav audio
        try {
            // Get the audio input stream
            AudioInputStream audioInputStream = AudioSystem
                    .getAudioInputStream(new BufferedInputStream(new ByteArrayInputStream(waveData)));

            // Get the audio format
            AudioFormat format = audioInputStream.getFormat();

            // Create a normalized format that works well with GraalVM
            AudioFormat normalizedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED, // Use signed PCM encoding
                    format.getSampleRate(),
                    16, // Use 16-bit samples
                    format.getChannels(),
                    format.getChannels() * 2, // Frame size
                    format.getSampleRate(),
                    false); // Use little-endian (more widely compatible)

            // Convert to the normalized format
            AudioInputStream normalizedStream = AudioSystem.getAudioInputStream(normalizedFormat, audioInputStream);

            // Create a temporary buffer to ensure all data is valid
            byte[] audioBytes = normalizedStream.readAllBytes();

            // Create a new stream from the validated data
            AudioInputStream validatedStream = new AudioInputStream(
                    new ByteArrayInputStream(audioBytes),
                    normalizedFormat,
                    audioBytes.length / normalizedFormat.getFrameSize());

            // Play the audio
            try (Clip clip = AudioSystem.getClip()) {
                clip.open(validatedStream);
                clip.start();

                // Wait for playback to complete
                while (!clip.isRunning()) {
                    Thread.sleep(100);
                }
                while (clip.isRunning()) {
                    Thread.sleep(100);
                }

                clip.stop();
            }
        } catch (Exception e) {
            System.err.println("Audio playback error: " + e.getMessage());
            e.printStackTrace();
            // Continue without throwing to prevent application crash
        }
    }
}
