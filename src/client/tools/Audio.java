package client.tools;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public  class Audio extends Thread {


    TargetDataLine line;
    AudioFileFormat.Type targetType;
    public String sender,receiver,time;
    AudioFormat audioFormat;

    public Audio(String sender,String receiver,String time) throws Exception {
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
        File file = new File("D:\\idea\\QQ\\libr\\AudioFile"+sender);
        if(!file.exists()){
            file.mkdirs();
        }
        audioFormat = new AudioFormat(8000F,16,2,true,false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
        line = (TargetDataLine) AudioSystem.getLine(info);
    }

    public void endRecord() {
        System.out.println("录音结束");
        line.stop();
        line.close();
    }

    @Override
    public void run() {
        try {
            targetType = AudioFileFormat.Type.WAVE;
            File audioFile = new File("D:\\idea\\QQ\\libr\\AudioFile"+sender+"\\"+time+".wav");
            line.open(audioFormat);
            line.start();
            AudioSystem.write( new AudioInputStream(line), targetType,audioFile);
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
