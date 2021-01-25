package uz.pdp.findtheword.Contract;

import android.view.View;

import java.util.ArrayList;

import uz.pdp.findtheword.Model.GameModel;

public interface GameContract {
    interface ModelInterface {
        ArrayList<GameModel> getData();
    }
    interface View{

        void loadDataToView(GameModel data);

        void variantClick(android.view.View view);

        void answerClick(android.view.View view);

        boolean checkResultAndLoadNext();

        void nextLevel();

    }
    interface Presenter{
        ArrayList<GameModel> getData();

        void loadDataToView(GameModel data);

        void variantClick(android.view.View view);

        void answerClick(android.view.View view);

        boolean checkResult();

        void nextLevel();
    }
}
