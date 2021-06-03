package spotify_app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Spotify_App {

    public static String noTxt(String file){ //cuts off .txt from the end of Strings
        return file.substring(0, file.length()-4);
    }
    
    public static boolean txt(String file){ //checks if file is .txt
        boolean isItTxt = false;
        if(file.substring(file.length()-4, file.length()).equals(".txt"))
        {
            isItTxt = true;
        }
        return isItTxt;
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
//loads the starting playlists (those that are stored in the directory as .txt files)
        File[] starting = new File("D:\\Netbeans\\projects\\Spotify_App").listFiles();
        ArrayList<String> startingPlaylists = new ArrayList();
        for(int i = 0; i < starting.length; i++){
            if(!starting[i].isDirectory()){
                if(txt(starting[i].getName())){
                startingPlaylists.add(starting[i].getName());
                }
            }
        }
//------------------------------------------------------------------------------------------------
//initializes the necessary variables
        int select;
        boolean quit = false;
        
        ArrayList<Playlist> playlists = new ArrayList();
        Scanner scanNumber = new Scanner(System.in);
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        for(int i = 0; i < startingPlaylists.size(); i++){
            Playlist temp = new Playlist(startingPlaylists.get(i),noTxt(startingPlaylists.get(i)));
            playlists.add(temp);
        }
        String currentPlaylistName = "playlist1";
        int startIdx = 0;
        for(int i = 0; i < playlists.size(); i++){
            if(playlists.get(i).getName().equals("playlist1")){
                startIdx = i;
            }
        }
        int currentPlaylist = startIdx;
//------------------------------------------------------------------------------------------------
//starts the loop and prints out the menu
        while(true){
            System.out.println("current playlist: " + currentPlaylistName);
            System.out.println("1. play tracks");
            System.out.println("2. play tracks in random order");
            System.out.println("3. play tracks by the selected artist");
            System.out.println("4. list artists");
            System.out.println("5. list styles");
            System.out.println("6. add new playlist");
            System.out.println("7. change current playlist");
            System.out.println("8. add songs to current playlist");
            System.out.println("9. delete songs from current playlist");
            System.out.println("0. Quit");
            select = scanNumber.nextInt(); //this variable will iterate through the menu
//------------------------------------------------------------------------------------------------
//implementation of the menu
            switch(select){
                case 1: playlists.get(currentPlaylist).playSongs(); break;
                case 2: playlists.get(currentPlaylist).randomOrderPlay(); break;
                case 3: System.out.println("Artist: ");
                        String artist = sc.readLine();
                        playlists.get(currentPlaylist).playByArtist(artist); break;
                case 4: playlists.get(currentPlaylist).listArtists(); break;
                case 5: playlists.get(currentPlaylist).listStyles(); break;
                case 6: System.out.println("Name of the new playlist: ");
                        String name = sc.readLine();
                        String newPlaylistFileName = name + ".txt";
                        File newPlaylistFile = new File(newPlaylistFileName);
                        if(!newPlaylistFile.exists()){
                        newPlaylistFile.createNewFile();
                        Playlist newPlaylist = new Playlist(newPlaylistFileName, name);
                        playlists.add(newPlaylist);
                        } else {
                            System.out.println("Playlist couldn't be created!");
                        }
                        break;
                case 7: System.out.println("Available playlists: ");
                        for(int i = 0; i < playlists.size(); i++){
                            System.out.println(playlists.get(i).getName());
                        }
                        System.out.println("New current playlist's name: ");
                        String newPlaylistName = sc.readLine();
                        String whatItWas = currentPlaylistName;
                        for(int i = 0; i < playlists.size(); i++){
                            if(playlists.get(i).getName().equals(newPlaylistName)){
                                currentPlaylist = i;
                                currentPlaylistName = playlists.get(i).getName();
                                    }
                                }
                        if(currentPlaylistName.equals(whatItWas)){
                            System.out.println("The playlist couldn't be changed!");
                        }
                        break;
                case 8: System.out.println("Title of song: ");
                        String newTitle = sc.readLine();
                        System.out.println("Name of artist: ");
                        String newArtist = sc.readLine();
                        System.out.println("Style: ");
                        String newStyle = sc.readLine();
                        System.out.println("Length in seconds(number): ");
                        String newLength = sc.readLine();
                        System.out.println("Language: ");
                        String newLanguage = sc.readLine();
                        FileWriter playlistWriter = new FileWriter(currentPlaylistName + ".txt");
                        for(int i = 0; i < playlists.get(currentPlaylist).getPlaylist().size(); i++){
                        playlistWriter.write(playlists.get(currentPlaylist).getPlaylist().get(i).getTitle()+";"+  playlists.get(currentPlaylist).getPlaylist().get(i).getArtist() + ";" + playlists.get(currentPlaylist).getPlaylist().get(i).getStyle() + ";" + playlists.get(currentPlaylist).getPlaylist().get(i).getLength() + ";" + playlists.get(currentPlaylist).getPlaylist().get(i).getLanguage() + "\n");
                                }
                        playlists.get(currentPlaylist).getPlaylist().add(new Song(newTitle,newArtist,newStyle,Integer.parseInt(newLength),newLanguage));
                        playlistWriter.write(newTitle + ";" + newArtist + ";" + newStyle + ";" + newLength + ";" + newLanguage + "\n");
                        playlistWriter.close();
                        break;
                case 9: System.out.println("Available songs in current playlist: ");
                        for(int i = 0; i < playlists.get(currentPlaylist).getPlaylist().size(); i++){
                            System.out.println(i+1 + "." + playlists.get(currentPlaylist).getPlaylist().get(i).getTitle() + " by " + playlists.get(currentPlaylist).getPlaylist().get(i).getArtist() + " style: " + playlists.get(currentPlaylist).getPlaylist().get(i).getStyle() + " " + playlists.get(currentPlaylist).getPlaylist().get(i).getLength() + " seconds long in " + playlists.get(currentPlaylist).getPlaylist().get(i).getLanguage());
                        }
                        System.out.println("Delete song: (index)");
                        int delete = scanNumber.nextInt()-1;
                        if(playlists.get(currentPlaylist).getPlaylist().size() > delete && delete >= 0){
                            playlists.get(currentPlaylist).deleteSong(delete);
                        } else {
                            System.out.println("Couldn't find a song with that index");
                        } break;
                case 0: System.out.println("Program closes...");
                        quit = true;
                        break;
                default: System.out.println("Unknown command!"); break; //if given a number other than 0-9, program won't do anything
            }
//------------------------------------------------------------------------------------------------
//checking if quit was pressed or not
            if(quit == true){
                break;
            }
        }
    }
}
