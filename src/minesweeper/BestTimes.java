package minesweeper;

import java.util.*;

/**
 * Player times.
 */
public class BestTimes implements Iterable<BestTimes.PlayerTime> {
    /** List of best player times. */
    private List<PlayerTime> playerTimes = new ArrayList<PlayerTime>();

    /**
     * Returns an iterator over a set of  best times.
     * @return an iterator
     */
    public Iterator<PlayerTime> iterator() {
        return playerTimes.iterator();
    }

    /**
     * Adds player time to best times.
     * @param name name ot the player
     * @param time player time in seconds
     */
    public void addPlayerTime(String name, int time) {
       playerTimes.add(new PlayerTime(name,time));
        Collections.sort(playerTimes); //po pridani sa zoradia hodnoty

    }

    void reset(){
        for(PlayerTime pl : playerTimes){
            playerTimes.remove(pl);
        }
    }

    /**
     * Returns a string representation of the object.
     * @return a string representation of the object
     */
    public String toString() {
        Formatter f = new Formatter();
        for(PlayerTime pl: playerTimes) {
            f.format("%s    %s     %s%n", playerTimes.iterator(),pl.getTime(),pl.getName());
            //throw new UnsupportedOperationException("Method toString not yet implemented");
        }
        return f.toString();
    }

    /**
     * Player time.
     */
    public static class PlayerTime implements Comparable<PlayerTime> {
        /**
         * Player name.
         */
        private final String name;

        /**
         * Playing time in seconds.
         */
        private final int time;

        /**
         * Constructor.
         *
         * @param name player name
         * @param time playing game time in seconds
         */
        public PlayerTime(String name, int time) {
            this.name = name;
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public int getTime() {
            return time;
        }

        @Override
        public int compareTo(PlayerTime o) {
            return Integer.compare(this.time, o.getTime());
        }
    }

    //vnorene triedy nakoniec, predtym overridnute, a predtym normalne
}