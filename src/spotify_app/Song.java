package spotify_app;

public class Song {
    
    private final String title;
    private final String artist;
    private final String style;
    private final int length;
    private final String language;

    public Song(String title, String artist, String style, int length, String language) {
        this.title = title;
        this.artist = artist;
        this.style = style;
        this.length = length;
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getStyle() {
        return style;
    }

    public int getLength() {
        return length;
    }

    public String getLanguage() {
        return language;
    }
}
