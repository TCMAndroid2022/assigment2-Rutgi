package cat.tecnocampus.mobileapps.practica2.francescboixaburrad.rogerrodriguezmendez.claudiomaioranaarvelo;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithGames {
    @Embedded public User user;
    @Relation(
            parentColumn = "nickname",
            entityColumn = "nickownergame"
    )

    public List<Game> games;
}
