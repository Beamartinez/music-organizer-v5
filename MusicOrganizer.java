import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Collections;

/**
 * A class to hold details of audio tracks.
 * Individual tracks may be played.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class MusicOrganizer
{
    // An ArrayList for storing music tracks.
    private ArrayList<Track> tracks;
    // A player for the music tracks.
    private MusicPlayer player;
    // A reader that can read music files and load them as tracks.
    private TrackReader reader;
    
    private boolean playing;
    //Numero aleatorio
    private int azar;

    /**
     * Create a MusicOrganizer
     */
    public MusicOrganizer()
    {
        tracks = new ArrayList<Track>();
        player = new MusicPlayer();
        reader = new TrackReader();
        readLibrary("miMusica");
        System.out.println("Music library loaded. " + getNumberOfTracks() + " tracks.");
        System.out.println();
    }
    
    /**
     * Add a track file to the collection.
     * @param filename The file name of the track to be added.
     */
    public void addFile(String filename)
    {
        tracks.add(new Track(filename));
    }
    
    /**
     * Add a track to the collection.
     * @param track The track to be added.
     */
    public void addTrack(Track track)
    {
        tracks.add(track);
    }
    
    /**
     * Play a track in the collection.
     * @param index The index of the track to be played.
     */
    public void playTrack(int index)
    {
        if(indexValid(index)) {
            Track track = tracks.get(index);
            player.startPlaying(track.getFilename());
            track.incrementCount();
            System.out.println("Now playing: " + track.getArtist() + " - " + track.getTitle());
        }
    }
    
    /**
     * Return the number of tracks in the collection.
     * @return The number of tracks in the collection.
     */
    public int getNumberOfTracks()
    {
        return tracks.size();
    }
    
    /**
     * List a track from the collection.
     * @param index The index of the track to be listed.
     */
    public void listTrack(int index)
    {
        System.out.print("Track " + index + ": ");
        Track track = tracks.get(index);
        System.out.println(track.getDetails());
    }
    
    /**
     * Show a list of all the tracks in the collection.
     */
    public void listAllTracks()
    {
        System.out.println("Track listing: ");

        for(Track track : tracks) {
            System.out.println(track.getDetails());
        }
        System.out.println();
    }
    
    /**
     * List all tracks by the given artist.
     * @param artist The artist's name.
     */
    public void listByArtist(String artist)
    {
        for(Track track : tracks) {
            if(track.getArtist().contains(artist)) {
                System.out.println(track.getDetails());
            }
        }
    }
    
    /**
     * Remove a track from the collection.
     * @param index The index of the track to be removed.
     */
    public void removeTrack(int index)
    {
        if(indexValid(index)) {
            tracks.remove(index);
        }
    }
    
    /**
     * Play the first track in the collection, if there is one.
     */
    public void playFirst()
    {
        if(tracks.size() > 0) {
            player.startPlaying(tracks.get(0).getFilename());
            tracks.get(0).incrementCount();
        }
    }
    
    /**
     * Stop the player.
     */
    public void stopPlaying()
    {
        player.stop();
    }

    /**
     * Determine whether the given index is valid for the collection.
     * Print an error message if it is not.
     * @param index The index to be checked.
     * @return true if the index is valid, false otherwise.
     */
    private boolean indexValid(int index)
    {
        // The return value.
        // Set according to whether the index is valid or not.
        boolean valid;
        
        if(index < 0) {
            System.out.println("Index cannot be negative: " + index);
            valid = false;
        }
        else if(index >= tracks.size()) {
            System.out.println("Index is too large: " + index);
            valid = false;
        }
        else {
            valid = true;
        }
        return valid;
    }
    
    private void readLibrary(String folderName)
    {
        ArrayList<Track> tempTracks = reader.readTracks(folderName, ".mp3");

        // Put all thetracks into the organizer.
        for(Track track : tempTracks) {
            addTrack(track);
        }
    }
    
    /*
     * M�todo que muestre por pantalla la informaci�n de los tracks que contienen dicha cadena en el titulo de la cacion.
     */
    public void findInTitle(String song)
    {
        for(Track track : tracks) {
            if(track.getTitle().contains(song)) {
                System.out.println(track.getDetails());
            }
        }
    }
    
    /*
     * Cambia el genero de la cancion y si la cancion no existe nos lo hace saber
     */
    public void setTrackGenero(int index, String genero)
    {
        if (index >= 0 && index < tracks.size())
        {
            tracks.get(index).setGenero(genero);
            System.out.println("El genero ha cambiado");
        }
        else
        {
            System.out.println("Esa cancion no existe");
        }
    }
    
    /*
     * Nos dice si esta sonando alguna cancion o no
     */
    public void isPlaying()
    {
        if (playing == true)
        {
            System.out.println("En este momento hay una cancion reproduciendose");
        }
        else
        {
            System.out.println("El reproductor esta en stop");
        }
    }
    
    /*
     * Muestra los detalles de todos los tracks almacenaos en un organizador
     */
    public void listAllTrackWithIterator()
    {
        Iterator<Track> it = tracks.iterator();
        while (it.hasNext())
        {
            Track song = it.next();
            System.out.println(song.getDetails());
        }
    }
    
    /*
     * Elimina del organizador tracks que contengan un determinado artista
     */
    public void removeByArtist(String artist)
    {
        Iterator<Track> it = tracks.iterator();
        while (it.hasNext())
        {
            Track song = it.next();
            if (song.getArtist().contains(artist))
            {
                it.remove();
            }
        }
    }
    
    /*
     * Elimina del organizador tracks que contengan una determinada cancion
     */
    public void removeByTitle(String title)
    {
        Iterator<Track> it = tracks.iterator();
        while (it.hasNext())
        {
            Track song = it.next();
            if (song.getTitle().contains(title))
            {
                it.remove();
            }
        }
    }
    
    /*
     * Reproduce canciones al azar
     */
    public void playRandom()
    {
       Random azar = new Random();
       playTrack(azar.nextInt(tracks.size()));
    }
    
    /*
     * reproduce los primeros segundos de cada cancion en orden aleatorio
     */
    public void playShuffle()
    {
        ArrayList<Track> trackShuffle = new ArrayList<Track>();
        trackShuffle = (ArrayList)tracks.clone();
        Collections.shuffle(trackShuffle);
        Iterator<Track> iteTracks = trackShuffle.iterator();
        while(iteTracks.hasNext())
        {
            Track song = iteTracks.next();
            System.out.println(song.getDetails());
            song.incrementCount();
            player.playSample(song.getFilename());
        }
    }
    
    /*
     * reproduce los primeros segundos de cada cancion en orden aleatorio
     */
    public void playShuffle2(){
        ArrayList<Track> copia = new ArrayList<Track>();
        copia = (ArrayList)tracks.clone();
        int countPlayer = 0;
        while(countPlayer < tracks.size()){
            Random random = new Random();
            int trackShuffle = random.nextInt(copia.size());
            Track song = copia.get(trackShuffle);
            System.out.println(song.getDetails());
            song.incrementCount();
            player.playSample(song.getFilename());
            copia.remove(trackShuffle);
            countPlayer++;
        }
    }
}
