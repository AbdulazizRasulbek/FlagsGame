package uz.pdp.findtheword.Model;

import java.util.ArrayList;
import java.util.Collections;

import uz.pdp.findtheword.Contract.GameContract;
import uz.pdp.findtheword.R;

public class Model_Impl implements GameContract.ModelInterface {

    ArrayList<GameModel> data=new ArrayList<>();

    @Override
    public ArrayList<GameModel> getData() {
        data.add(new GameModel(R.drawable.austria,"Austria"));
        data.add(new GameModel(R.drawable.argentina,"Argentina"));
        data.add(new GameModel(R.drawable.bahrain,"Bahrain"));
        data.add(new GameModel(R.drawable.belgium,"Belgium"));
        data.add(new GameModel(R.drawable.canada,"Canada"));
        data.add(new GameModel(R.drawable.hungary,"Hungary"));
        data.add(new GameModel(R.drawable.ic_france_flag,"France"));
        data.add(new GameModel(R.drawable.ic_germany_flag,"Germany"));
        data.add(new GameModel(R.drawable.indonesia,"Indonesia"));
        data.add(new GameModel(R.drawable.italy,"Italy"));
        data.add(new GameModel(R.drawable.mexico,"Mexico"));
        data.add(new GameModel(R.drawable.monaco,"Monaco"));
        data.add(new GameModel(R.drawable.new_zealand,"NewZealand"));
        data.add(new GameModel(R.drawable.peru,"Peru"));
        data.add(new GameModel(R.drawable.poland,"Poland"));
        data.add(new GameModel(R.drawable.qatar,"Qatar"));
        data.add(new GameModel(R.drawable.romania,"Romania"));
        data.add(new GameModel(R.drawable.spain,"Spain"));
        data.add(new GameModel(R.drawable.uzbekistan,"Uzbekistan"));
        data.add(new GameModel(R.drawable.vietnam,"Vietnam"));
        data.add(new GameModel(R.drawable.yemen,"Yemen"));

        Collections.shuffle(data);
        return data;
    }
}
