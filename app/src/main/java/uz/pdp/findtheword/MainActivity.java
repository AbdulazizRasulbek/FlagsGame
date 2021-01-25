package uz.pdp.findtheword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;
import uz.pdp.findtheword.Contract.GameContract;
import uz.pdp.findtheword.Model.GameModel;
import uz.pdp.findtheword.Model.Model_Impl;
import uz.pdp.findtheword.Presenter.Presenter;

public class MainActivity extends AppCompatActivity implements GameContract.View {
    private ArrayList<Button> answers;
    private ArrayList<Button> variants;
    ArrayList<GameModel> data;
    Presenter presenter;
    int currentIndex = 0;
    StringBuilder stringBuilder;
    ImageView imageView;
    ImageButton backButton;
    TextView level, coins;
    int coinInt;


    //i 300  show 50 delete 2 80 true 10
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new Presenter(new Model_Impl(), this);
        data = presenter.getData();
        loadViews();
        presenter.loadDataToView(data.get(currentIndex));


    }


    private void loadViews() {
        findViewById(R.id.deleteLetter).setOnClickListener(this::helpClick);
        findViewById(R.id.showLetter).setOnClickListener(this::helpClick);
        level = findViewById(R.id.level);
        coins = findViewById(R.id.coins);
        backButton = findViewById(R.id.back);
        backButton.setOnClickListener(v -> finish());
        variants = new ArrayList<>();
        imageView = findViewById(R.id.image);
        answers = new ArrayList<>();
        addButtonsFromViewGroup(answers, R.id.layoutAnswers, this::answerClick);
        addButtonsFromViewGroup(variants, R.id.layoutVariant1, this::variantClick);
        addButtonsFromViewGroup(variants, R.id.layoutVariant2, this::variantClick);
    }

    private void addButtonsFromViewGroup(ArrayList<Button> list, int resId, View.OnClickListener listener) {
        ViewGroup group = findViewById(resId);
        for (int i = 0; i < group.getChildCount(); i++) {
            Button button = (Button) group.getChildAt(i);
            list.add(button);
            button.setOnClickListener(listener);
            button.setVisibility(View.VISIBLE);
        }
    }


    public void loadDataToView(GameModel data) {
        clearAnswersButton();
        showVariants();
        answers = new ArrayList<>();

        addButtonsFromViewGroup(answers, R.id.layoutAnswers, this::answerClick);

        level.setText(currentIndex + 1 + "");
        String s = generateVariants(data.getAnswer());
        imageView.setImageResource(data.getImage());
//        showAnswers(data.getAnswer().length());
        for (int i = 0; i < answers.size(); i++) {
            Button button = answers.get(i);
            button.setTextColor(getResources().getColor(R.color.colorPrimary));
            button.setBackgroundResource(R.drawable.bg_answer);
            button.setTag(null);
            if (i >= data.getAnswer().length()) {
                button.setVisibility(View.GONE);
//                Toast.makeText(this, i+"", Toast.LENGTH_SHORT).show();
            }
        }
        for (int j = 0; j < variants.size(); j++) {

            variants.get(j).setText(String.format("%s", s.charAt(j)));

        }
    }


    void helpClick(View view) {
        coinInt = getCoinInt(coins);
        String answer = data.get(currentIndex).getAnswer();
        Random random = new Random();
        if (view.getId() == R.id.showLetter) {
            if (coinInt >= 50) {
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("You will lose 50 coins to see one correct letter")
                        .setConfirmText("Yes")
                        .setConfirmClickListener(sDialog -> {
                            {

                                for (int i = 0; i < answers.size(); i++) {
//                    int randomInt = random.nextInt(answer.length());
                                    Button button = answers.get(i);
                                    if (button.getText().length() == 0 && button.getVisibility() == View.VISIBLE) {
                                        char c = answer.charAt(i);
                                        for (Button variant : variants) {
                                            if (variant.getText().toString().equals(c + "")) {
                                                variant.setVisibility(View.INVISIBLE);
                                                button.setBackgroundResource(R.drawable.bg_true);
                                                button.setText(c + "");
                                                button.setTag("tegma");
                                                button.setTextColor(getResources().getColor(android.R.color.white));
                                                coinInt -= 50;
                                                coins.setText(coinInt + "");
                                                if (checkResultAndLoadNext()) {
                                                    nextLevel();
                                                }
                                                sDialog.dismissWithAnimation();
                                                return;
                                            }
                                        }

                                    }
//                    while (button.getText().length() < 0&&randomInt==i) {
//                        if (randomInt==i) {
//                        randomInt=random.nextInt(answer.length());


                                }
                            }
                        })
                        .setCancelButton("No", SweetAlertDialog::dismissWithAnimation)
                        .show();

            } else Toast.makeText(this, "No enough money", Toast.LENGTH_SHORT).show();
        } else {
            if (coinInt >= 40) {
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("You will lose 40 coins to see one correct letter")
                        .setConfirmText("Yes")
                        .setConfirmClickListener(sDialog -> {
                            int randomInt = random.nextInt(variants.size());
                            for (int i = 0; i < variants.size(); i++) {
                                Button button = variants.get(i);
                                if (button.getText().length() > 0 && !answer.contains(button.getText().toString()) && button.getVisibility() == View.VISIBLE) {
                                    button.setVisibility(View.INVISIBLE);
                                    coinInt -= 40;
                                    coins.setText(coinInt + "");
                                    return;
                                }
                            }
                        })
                        .setCancelButton("No", SweetAlertDialog::dismissWithAnimation)
                        .show();


            } else {
                Toast.makeText(this, "No enough money", Toast.LENGTH_SHORT).show();

            }
        }
    }

    boolean showLetter() {
        return false;
    }

    private int getCoinInt(TextView coins) {
        String s = coins.getText().toString();
        return Integer.parseInt(s);
    }

    @Override
    public void variantClick(View view) {
        int newCoins;

        Button button = (Button) view;
        for (int i = 0; i < answers.size(); i++) {
            Button answer = answers.get(i);
            if (answer.getVisibility() == View.VISIBLE && answer.getText().toString().isEmpty()) {
                answer.setText(button.getText());
                button.setVisibility(View.INVISIBLE);
                if (i == data.get(currentIndex).getAnswer().length() - 1) {
                    if (!checkResultAndLoadNext()) {
                        String s = coins.getText().toString();
                        newCoins = Integer.parseInt(s) - 10;
                        coins.setText(newCoins + "");
                        Toast.makeText(this, "Incorrect. Try again", Toast.LENGTH_LONG).show();

                    } else {
                        nextLevel();
                        return;
                    }
                }

                if (checkResultAndLoadNext()) {
                    nextLevel();
                }
                return;
            }
        }


    }

    public void nextLevel() {
        String s = coins.getText().toString();
        int newCoins = Integer.parseInt(s) + 10;
        new CountDownTimer(200, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                SweetAlertDialog dialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                ++currentIndex;

                if (data.size() - 1 < currentIndex) {
                    dialog.setTitleText("\uD83D\uDC4F You win the game!! \uD83D\uDC4F");
                    dialog.setConfirmText("Main Menu").setConfirmClickListener(sweetAlertDialog -> {
                        startActivity(new Intent(MainActivity.this, MenuActivity.class));
                        finish();
                    });
                    dialog.setCancelable(false);
                    dialog.show();
                } else {
                    dialog.setTitleText("\uD83D\uDC4F Correct! \uD83D\uDC4F");
                    dialog.setContentText(data.get(currentIndex - 1).getAnswer());
                    dialog.setConfirmClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                        if (currentIndex <= data.size() - 1) {
                            presenter.loadDataToView(data.get(currentIndex));
                            coins.setText(newCoins + "");
                        }

                    });
                    dialog.setConfirmText("Continue");
                    dialog.setCancelable(false);
                    dialog.show();
                }
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showVariants() {
        for (int i = 0; i < variants.size(); i++) {
            Button button = variants.get(i);
            button.setVisibility(View.VISIBLE);
        }
    }

    private void clearAnswersButton() {
        for (int i = 0; i < answers.size(); i++) {
            Button button = answers.get(i);
            button.setText("");
            button.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void answerClick(View view) {
        Button button = (Button) view;
        String tag = "";
        if (button.getTag() != null) {
            tag = (String) button.getTag();
        }
        if (button.getText().length() > 0 && !tag.equals("tegma")) {
            for (int i = 0; i < variants.size(); i++) {
                Button variant = variants.get(i);
                if (variant.getVisibility() == View.INVISIBLE && button.getText() == variant.getText()) {
                    button.setText("");
                    variant.setVisibility(View.VISIBLE);
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);

                }
            }

        }
    }

    @Override
    public boolean checkResultAndLoadNext() {
        stringBuilder = new StringBuilder();
        for (int i = 0; i < answers.size(); i++) {
            Button button = answers.get(i);
            if (button.getText().length() > 0) {
                stringBuilder.append(button.getText());
            }
        }
        if (stringBuilder.toString().equalsIgnoreCase(data.get(currentIndex).getAnswer())) {

            return true;
        } else return false;
    }

    public static String generateVariants(String answer) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int length = answer.length();
        int l = 16 - length;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < alphabet.length(); i++) {
            list.add(String.valueOf(alphabet.charAt(i)));
        }
        Collections.shuffle(list);
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < answer.length(); i++) {
            strings.add(answer.charAt(i) + "");
        }
        for (int i = 0; i < l; i++) {
            strings.add(list.get(i));
        }
        Collections.shuffle(strings);

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            s.append(strings.get(i));
        }
        return s.toString();
    }

}
