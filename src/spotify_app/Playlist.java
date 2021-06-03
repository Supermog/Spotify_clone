package spotify_app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Playlist {
    
    private final ArrayList<Song> playlist;
    private String name;

    public String getName() {
        return name;
    }

    public ArrayList<Song> getPlaylist() {
        return playlist;
    }
    
    
    public Playlist(String fajlnev, String name) throws FileNotFoundException, IOException{ //constructor reads the songs out of the given txt file(fajlnev)
        this.name = name;
        playlist = new ArrayList();
        File fajl = new File(fajlnev);
        BufferedReader in = new BufferedReader(new FileReader(fajlnev));
        String line = new String();
        while(in.ready()){
            line = in.readLine();
            String[] data = line.split(";");
            playlist.add(new Song(data[0], data[1], data[2], Integer.parseInt(data[3]), data[4]));
        }
    }
    public void play(int i){ // plays one song
        System.out.println(playlist.get(i).getTitle() + " by " + playlist.get(i).getArtist()+ " " +playlist.get(i).getLength() + " seconds long " + " style: " + playlist.get(i).getStyle() + " in " + playlist.get(i).getLanguage() + " track played");
    }
    
    public void playSongs(){ //plays all songs in the current playlist in the order they are stored in
        if(playlist.isEmpty()){
            System.out.println("There are no songs in this playlist!");
        } else{
            for(int i = 0; i < playlist.size(); i++){
            play(i);
            }
        }
    }
    
    public void randomOrderPlay(){ //plays songs in a random order in the current playlist
        if(playlist.isEmpty()){
            System.out.println("There are no songs in this playlist!");
        } else{
        Random rand = new Random();
        ArrayList played = new ArrayList();
        ArrayList all = new ArrayList();
        for(int i = 0; i < playlist.size(); i++){
            all.add(i);
        }
        while(true){
        int number = rand.nextInt(playlist.size());
        if(!played.contains(number)){
            play(number);
            played.add(number);
            }
        if(played.containsAll(all)){
            break;
                }
            }
        }
    }
    
    public void playByArtist(String artist){ //plays every song by a selected artist
        boolean playedAny = false;
        for(int i = 0; i < playlist.size(); i++){
            if(artist.equals(playlist.get(i).getArtist())){
                play(i);
                playedAny = true;
            }
        }
        if(playedAny == false){
            System.out.println("Couldn't find the selected artist. Check the spelling and try again!");
        }
    }
    
    public void listArtists(){ //lists every artist in alphabetical order
        ArrayList<Song> temp = new ArrayList();
        for(int i = 0; i < playlist.size(); i++){
            temp.add(playlist.get(i));
        }
        Collections.sort(temp, (o1, o2) -> {return o1.getArtist().compareTo(o2.getArtist());});
        for(int i = 0; i < temp.size(); i++){
            if(i == 0){
                System.out.println(temp.get(i).getArtist());
            } else{
            if(!temp.get(i).getArtist().equals(temp.get(i-1).getArtist())){
                System.out.println(temp.get(i).getArtist());
                }
            }
        }
    }
    
    public void listStyles(){ //lists every style in alphabetical order
        ArrayList<Song> temp = new ArrayList();
        for(int i = 0; i < playlist.size(); i++){
            temp.add(playlist.get(i));
        }
        Collections.sort(temp, (o1, o2) -> {return o1.getStyle().compareTo(o2.getStyle());});
        for(int i = 0; i < temp.size(); i++){
            if(i == 0){
                System.out.println(temp.get(i).getStyle());
            } else{
            if(!temp.get(i).getStyle().equals(temp.get(i-1).getStyle())){
                System.out.println(temp.get(i).getStyle());
                }
            }
        }
    }
    
    public void deleteSong(int idx) throws IOException{ //deletes a song from the playlist's ArrayList and also deletes it from the .txt file
        playlist.remove(idx);
        FileWriter writer = new FileWriter(name + ".txt");
        for(int i = 0; i < playlist.size(); i++){
            writer.write(playlist.get(i).getTitle() + ";" + playlist.get(i).getArtist() + ";" + playlist.get(i).getStyle() + ";" + playlist.get(i).getLength() + ";" + playlist.get(i).getLanguage() + "\n");
        }
        writer.close();
    }
    
    
}