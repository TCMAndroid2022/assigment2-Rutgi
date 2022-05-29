package cat.tecnocampus.mobileapps.practica2.francescboixaburrad.rogerrodriguezmendez.claudiomaioranaarvelo;

import android.os.Parcel;
import android.os.Parcelable;

public class Todo implements Parcelable {

    private String nickname, puntuacio, numero_partides;


    public Todo(String nickname, String puntuacio, String numero_partides) {
        this.nickname = nickname;
        this.puntuacio = puntuacio;
        this.numero_partides = numero_partides;
    }

    protected Todo(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Creator<Todo> CREATOR = new Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel in) {
            return new Todo(in);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };

    private void readFromParcel(Parcel in) {
        nickname = in.readString();
        puntuacio = in.readString();
        numero_partides = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nickname);
        parcel.writeString(puntuacio);
        parcel.writeString(numero_partides);
    }



    public String getNickname() {
        return nickname;
    }

    public String getPuntuacio() {
        return puntuacio;
    }

    public String getNumero_partides() {
        return numero_partides;
    }
}
