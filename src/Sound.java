import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound implements Runnable{
    public Sound() {}

    @Override
    public void run() {
        File file;

        AudioInputStream audio;
        AudioFormat format;

        SourceDataLine auline = null;
        DataLine.Info info;

        while (true) {
            // Let the music loop!!! So long as you are alive, don't stop the music!!! :)
            try {
                // load the file
                file = new File("Sound.wav");
                // get the audio input stream
                audio = AudioSystem.getAudioInputStream(file);
                // get the format of the audio
                format = audio.getFormat();
                // construct the info object containing the information about the audio
                info = new DataLine.Info(SourceDataLine.class, format);

                auline = (SourceDataLine) AudioSystem.getLine(info);
                auline.open(format);
                auline.start();

                int nBytesRead = 0;
                byte[] abData = new byte[524288];

                // read each info stream of the audio
                while (nBytesRead != -1) {
                    nBytesRead = audio.read(abData, 0, abData.length);

                    if (nBytesRead >= 0) { auline.write(abData, 0, nBytesRead); }

                }
            } catch (IOException e) {
                // System.out.println(e.getMessage());
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                // drains data from the queued line
                auline.drain();
                auline.close();
            }
        }

    }
}