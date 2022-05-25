package cat.tecnocampus.mobileapps.practica2.francescboixaburrad.rogerrodriguezmendez.claudiomaioranaarvelo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Game {
    @PrimaryKey
    public int gameid;

    public int numintents;
    public int scoregame;
    public String nickownergame;


    public Game(){

    }
    public Game(int id, int numintents, int scoregame, String nickOwnerGame) {
        this.gameid = id;
        this.numintents = numintents;
        this.scoregame = scoregame;
        this.nickownergame = nickOwnerGame;
    }

    public int getNumIntents() {
        return numintents;
    }

    public int getScoreGame() {
        return scoregame;
    }

    public String getNickOwner() {return nickownergame;}
}
