package lagames.com.mathgames;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Level1 extends AppCompatActivity {

    private int count = 0;
    Dialog dialog;
    private int a, b, c;
    private Chronometer mTimer;
    private boolean resume = false;
    private long elapsedTime;
    private final String DEBUG_TAG = "TAG";

    private TextView textAns1, textAns2, textAns3, textAns4;
    private TextView point11;

    private final Random random = new Random();

    //Массив для прогресса начало
    final int[] progress = {R.id.point1, R.id.point2, R.id.point3, R.id.point4, R.id.point5, R.id.point6, R.id.point7, R.id.point8, R.id.point9, R.id.point10};

    private void init() {
        setContentView(R.layout.universal);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        textAns1 = findViewById(R.id.textAns1);
        textAns2 = findViewById(R.id.textAns2);
        textAns3 = findViewById(R.id.textAns3);
        textAns4 = findViewById(R.id.textAns4);
        point11 = findViewById(R.id.point11);

        //создание переменной text_levels
        TextView text_levels = findViewById(R.id.text_levels);
        text_levels.setText(R.string.level1);//установлен текст
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        createDialogWindow();
        setOnChronometerTickListener();
        setBackButtonListener();
        setQuestion();
        createAnswerButtons();
    }


    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(Level1.this, GameLevels.class);
            startActivity(intent);
            finish();
        } catch (Exception ignored) {
        }
    }

    private void createAnswerButtons() {
        //Обработка нажатия кнопок с ответами начало
        ArrayList<Integer> answer = new ArrayList<>();
        int aa = random.nextInt(5);
        int bb = random.nextInt(5);
        int dd = random.nextInt(5);
        while (aa == c) {
            aa = random.nextInt(5);
            while (bb == c) {
                bb = random.nextInt(5);
                while (dd == c) {
                    dd = random.nextInt(5);
                }
            }
        }
        answer.add(aa);
        answer.add(bb);
        answer.add(dd);
        answer.add(c);
        Collections.shuffle(answer);
        textAns1.setText(String.valueOf(answer.get(0)));
        textAns2.setText(String.valueOf(answer.get(1)));
        textAns3.setText(String.valueOf(answer.get(2)));
        textAns4.setText(String.valueOf(answer.get(3)));

        //Обработка нажатия кнопок с ответами конец
        MyOnTouchListener myOnTouchListener = new MyOnTouchListener();
        textAns1.setOnTouchListener(myOnTouchListener);
        textAns2.setOnTouchListener(myOnTouchListener);
        textAns3.setOnTouchListener(myOnTouchListener);
        textAns4.setOnTouchListener(myOnTouchListener);
    }

    private void createDialogWindow() {
        //создаём новое диалоговое окно
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.perviewdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        //закрытие диалогового окна начало
        TextView btnclose = dialog.findViewById(R.id.bottomclose);
        btnclose.setOnClickListener(v -> {
            //Обработка нажтия кнопки начало
            try {
                //Вернутся к выбору уровня начало
                Intent intent = new Intent(Level1.this, GameLevels.class);
                startActivity(intent);
                finish();
                //Вернутся к выбору уровня конец
            } catch (Exception ignored) {
            }
            dialog.dismiss();
            //Обработка нажтия кнопки конец
        });

        //закрытие диалогового окна конец
        //кнопка продолжить начало
//        Button btncontinue = dialog.findViewById(R.id.btncontinue);
//        btncontinue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!resume) {
//                    mTimer.setBase(SystemClock.elapsedRealtime());
//                    mTimer.start();
//                } else {
//                    mTimer.start();
//                }
//                dialog.dismiss();
//            }
//        });
        //кнопка продолжить конец
        dialog.show();
    }

    private void setOnChronometerTickListener() {
        mTimer = findViewById(R.id.time);
        mTimer.setOnChronometerTickListener(chronometer -> {
            if (!resume) {
                long minutes = ((SystemClock.elapsedRealtime() - mTimer.getBase()) / 1000) / 60;
                long seconds = ((SystemClock.elapsedRealtime() - mTimer.getBase()) / 1000) % 60;
                elapsedTime = SystemClock.elapsedRealtime();
                Log.d(DEBUG_TAG, "onChronometerTick: " + minutes + " : " + seconds);
            } else {
//                long minutes = ((elapsedTime - mTimer.getBase()) / 1000) / 60;
//                long seconds = ((elapsedTime - mTimer.getBase()) / 1000) % 60;
                elapsedTime = elapsedTime + 100;
            }
        });
    }

    private void setBackButtonListener() {
        Button button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(Level1.this, GameLevels.class);
                startActivity(intent);
                finish();
            } catch (Exception ignored) {
            }
        });
    }

    private void setQuestion() {
        a = random.nextInt(10);
        b = 2;
        c = a * b;
        point11.setText(b + "*" + a + "=" + "?");

    }

    private class MyOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //условие косания картинки начало
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //Если каснулся начало
                point11.setText(b + "*" + a + "=" + c);
                //Если каснулся конец
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                //Если убрал начало
                if (textAns4.getText().equals(String.valueOf(c))) {
                    if (count < 9) {
                        count++;
                    } else {
                        dialog.show();
                        count = 0;
                    }
                    //закрашиваем прогресс
                    for (int i = 0; i < 10; i++) {
                        TextView tv = findViewById(progress[i]);
                        tv.setBackgroundResource(R.drawable.style_points);
                    }
                    //Закрашиваем прогресс конец
                    //определение правильности ответа и закрашивание начало
                    for (int i = 0; i < count; i++) {
                        TextView tv = findViewById(progress[i]);
                        tv.setBackgroundResource(R.drawable.style_points_green);

                    }
                    //определение правильности ответа и закрашивание конец
                } else {
                    if (count > 0) {
                        if (count == 1) {
                            count = 0;
                        } else {
                            count = count - 2;
                        }
                    }
                    for (int i = 0; i < 9; i++) {
                        TextView tv = findViewById(progress[i]);
                        tv.setBackgroundResource(R.drawable.style_points);
                    }
                    //Закрашиваем прогресс конец
                    //определение правильности ответа и закрашивание начало
                    for (int i = 0; i < count; i++) {
                        TextView tv = findViewById(progress[i]);
                        tv.setBackgroundResource(R.drawable.style_points_green);
                    }
                }
                a = random.nextInt(10);
                b = 2;
                c = a * b;
                point11.setText(b + "*" + a + "=" + "?");
                ArrayList<Integer> answer = new ArrayList<>();
                int aa = a + random.nextInt(5);
                int bb = b + random.nextInt(5);
                int dd = c + random.nextInt(5);
                while (aa == c) {
                    aa = a + random.nextInt(5);
                    while (bb == c) {
                        bb = b + random.nextInt(5);
                        while (dd == c) {
                            dd = c + random.nextInt(5);
                        }
                    }
                }
                answer.add(aa);
                answer.add(bb);
                answer.add(dd);
                answer.add(c);
                Collections.shuffle(answer);
                textAns1.setText(String.valueOf(answer.get(0)));
                textAns2.setText(String.valueOf(answer.get(1)));
                textAns3.setText(String.valueOf(answer.get(2)));
                textAns4.setText(String.valueOf(answer.get(3)));
                ////Если убрал конец
            }
            //условие косания кнопки конец
            return true;
        }
    }
}