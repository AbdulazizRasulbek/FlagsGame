package uz.pdp.findtheword.Model;

public class GameModel {
    int image;
    String answer;

    public GameModel(int image, String answer) {
        this.image = image;
        this.answer = answer;
    }

    public int getImage() {
        return image;
    }

    public String getAnswer() {
        return answer;
    }
}
