package uz.pdp.findtheword.Presenter;

import android.view.View;

import java.util.ArrayList;

import uz.pdp.findtheword.Contract.GameContract;
import uz.pdp.findtheword.Model.GameModel;

public class Presenter implements GameContract.Presenter {

    private GameContract.ModelInterface model;
    private GameContract.View view;

    public Presenter(GameContract.ModelInterface model, GameContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public ArrayList<GameModel> getData() {
        return model.getData();
    }

    @Override
    public void loadDataToView(GameModel data) {
        view.loadDataToView(data);
    }

    @Override
    public void variantClick(View view) {
        this.view.variantClick(view);
    }

    @Override
    public void answerClick(View view) {
        this.view.answerClick(view);
    }

    @Override
    public boolean checkResult() {
        return view.checkResultAndLoadNext();
    }

    @Override
    public void nextLevel() {
        view.nextLevel();
    }


}
