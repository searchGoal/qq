package client.tools;

public class SoundServer {
    Audio audio;
    public void startRecoder(String sender,String receiver,String time) throws Exception {
        audio = new Audio(sender,receiver,time);
        audio.start();
    }
    public void endRecoder(){
        if(audio != null){
            audio.endRecord();
        }
    }
}
