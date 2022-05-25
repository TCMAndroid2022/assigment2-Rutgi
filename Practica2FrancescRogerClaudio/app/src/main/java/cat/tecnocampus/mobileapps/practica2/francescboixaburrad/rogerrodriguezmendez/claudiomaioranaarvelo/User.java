package cat.tecnocampus.mobileapps.practica2.francescboixaburrad.rogerrodriguezmendez.claudiomaioranaarvelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @NonNull
    @PrimaryKey
    public String nickname;

    @ColumnInfo(name = "score")
    public int totalscore;


    public User(){

    }

    public User(String nickName, int totalScore) {
        this.nickname = nickName;
        this.totalscore = totalScore;
    }

    public String getNickName() {
        return nickname;
    }

    public int getTotalScore() {
        return totalscore;
    }
}
