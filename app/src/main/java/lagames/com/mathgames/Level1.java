package lagames.com.mathgames;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ModuleInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.RandomAccess;

public class Level1 extends AppCompatActivity {

    private int count = 0;
    Dialog dialog;
    private int a,b,c;
    private Chronometer mTimer;
    private Boolean resume = false;
    private long elapsedTime;
    private String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Random random=new Random();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //создание переменной text_levels
        TextView text_levels= findViewById(R.id.text_levels);
        text_levels.setText(R.string.level1);//установлен текст


        final TextView point11 = findViewById(R.id.point11);
        final TextView textAns1 = findViewById(R.id.textAns1);
        final TextView textAns2 = findViewById(R.id.textAns2);
        final TextView textAns3 = findViewById(R.id.textAns3);
        final TextView textAns4 = findViewById(R.id.textAns4);

        //создаём новое диалоговое окно
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.perviewdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        //закрытие диалогового окна начало
        TextView btnclose = dialog.findViewById(R.id.btnclose);

        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Обработка нажтия кнопки начало
                try {
                    //Вернутся к выбору уровня начало
                    Intent intent = new Intent(Level1.this, GameLavels.class);
                    startActivity(intent);
                    finish();
                    //Вернутся к выбору уровня конец
                } catch (Exception e){

                }
                dialog.dismiss();
                //Обработка нажтия кнопки конец
            }
        });
        //закрытие диалогового окна конец
        //кнопка продолжить начало
        Button btncontinue = dialog.findViewById(R.id.btncontinue);
        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!resume) {
                    mTimer.setBase(SystemClock.elapsedRealtime());
                    mTimer.start();
                } else {
                    mTimer.start();
                }
                dialog.dismiss();
            }
        });
        //кнопка продолжить конец
        dialog.show();

        mTimer = findViewById(R.id.time);
        mTimer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (!resume) {
                    long minutes = ((SystemClock.elapsedRealtime() - mTimer.getBase())/1000) / 60;
                    long seconds = ((SystemClock.elapsedRealtime() - mTimer.getBase())/1000) % 60;
                    elapsedTime = SystemClock.elapsedRealtime();
                    Log.d(TAG, "onChronometerTick: " + minutes + " : " + seconds);
                } else {
                    long minutes = ((elapsedTime - mTimer.getBase())/1000) / 60;
                    long seconds = ((elapsedTime - mTimer.getBase())/1000) % 60;
                    elapsedTime = elapsedTime + 100;
                }
            }
        });

        Button button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Level1.this, GameLavels.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){

                }
            }
        });

        //Массив для прогресса начало
        final int[] progerss = {R.id.point1, R.id.point2, R.id.point3, R.id.point4, R.id.point5, R.id.point6, R.id.point7, R.id.point8, R.id.point9, R.id.point10};
        //Массив для прогресса конец
        a = random.nextInt(10);
        b = 2;
        c = a*b;
        point11.setText(b+"*"+a+"="+"?");
        //Обработка нажатия кнопок с ответами начало
            ArrayList<Integer> answer = new ArrayList();
            int aa=random.nextInt(5);
            int bb=random.nextInt(5);
            int dd=random.nextInt(5);
            while(aa==c){
                aa= random.nextInt(5);
                while(bb==c){
                    bb= random.nextInt(5);
                    while(dd==c){
                        dd= random.nextInt(5);
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
            textAns1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //условие косания картинки начало
                    if(event.getAction()==MotionEvent.ACTION_DOWN){
                        //Если каснулся начало
                        point11.setText(b+"*"+a+"="+c);
                        //Если каснулся конец
                    } else if (event.getAction()==MotionEvent.ACTION_UP){
                        //Если убрал начало
                        if(textAns1.getText().equals(String.valueOf(c))){
                            if(count<9){
                                count++;
                            } else {
                                dialog.show();
                                count=0;
                            }
                            //закрашиваем прогресс
                            for(int i=0;i<10;i++){
                                TextView tv =findViewById(progerss[i]);
                                tv.setBackgroundResource(R.drawable.style_points);
                            }
                            //Закрашиваем прогресс конец
                            //определение правильности ответа и закрашивание начало
                            for(int i=0;i<count;i++){
                                TextView tv =findViewById(progerss[i]);
                                tv.setBackgroundResource(R.drawable.style_points_green);
                            }
                            //определение правильности ответа и закрашивание конец
                        } else {
                            if(count>0){
                                if(count==1){
                                    count=0;
                                } else {
                                    count=count-2;
                                }
                            }
                            for(int i=0;i<9;i++){
                                TextView tv =findViewById(progerss[i]);
                                tv.setBackgroundResource(R.drawable.style_points);
                            }
                            //Закрашиваем прогресс конец
                            //определение правильности ответа и закрашивание начало
                            for(int i=0;i<count;i++){
                                TextView tv =findViewById(progerss[i]);
                                tv.setBackgroundResource(R.drawable.style_points_green);
                            }
                        }
                        a = random.nextInt(10);
                        b = 2;
                        c=a*b;
                        point11.setText(b+"*"+a+"="+"?");
                        ArrayList<Integer> answer = new ArrayList();
                        int aa=a+random.nextInt(5);
                        int bb=b+random.nextInt(5);
                        int dd=c+random.nextInt(5);
                        while(aa==c){
                            aa= random.nextInt(5);
                            while(bb==c){
                                bb= random.nextInt(5);
                                while(dd==c){
                                    dd= random.nextInt(5);
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
                    //условие косания картинки конец
                    return true;
                }
            });
            textAns2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //условие косания картинки начало
                    if(event.getAction()==MotionEvent.ACTION_DOWN){
                        //Если каснулся начало
                        point11.setText(b+"*"+a+"="+c);
                        //Если каснулся конец
                    } else if (event.getAction()==MotionEvent.ACTION_UP){
                        //Если убрал начало
                        if(textAns2.getText().equals(String.valueOf(c))){
                            if(count<9){
                                count++;
                            } else {
                                dialog.show();
                                count=0;
                            }
                            //закрашиваем прогресс
                            for(int i=0;i<10;i++){
                                TextView tv =findViewById(progerss[i]);
                                tv.setBackgroundResource(R.drawable.style_points);
                            }
                            //Закрашиваем прогресс конец
                            //определение правильности ответа и закрашивание начало
                            for(int i=0;i<count;i++){
                                TextView tv =findViewById(progerss[i]);
                                tv.setBackgroundResource(R.drawable.style_points_green);

                            }
                            //определение правильности ответа и закрашивание конец
                        } else {
                            if(count>0){
                                if(count==1){
                                    count=0;
                                } else {
                                    count=count-2;
                                }
                            }
                            for(int i=0;i<9;i++){
                                TextView tv =findViewById(progerss[i]);
                                tv.setBackgroundResource(R.drawable.style_points);
                            }
                            //Закрашиваем прогресс конец
                            //определение правильности ответа и закрашивание начало
                            for(int i=0;i<count;i++){
                                TextView tv =findViewById(progerss[i]);
                                tv.setBackgroundResource(R.drawable.style_points_green);
                            }
                        }
                        a = random.nextInt(10);
                        b = 2;
                        c=a*b;
                        point11.setText(b+"*"+a+"="+"?");
                        ArrayList<Integer> answer = new ArrayList();
                        int aa=a+random.nextInt(5);
                        int bb=b+random.nextInt(5);
                        int dd=c+random.nextInt(5);
                        while(aa==c){
                            aa= random.nextInt(5);
                            while(bb==c){
                                bb= random.nextInt(5);
                                while(dd==c){
                                    dd= random.nextInt(5);
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
                    //условие косания картинки конец
                    return true;
                }
            });
            textAns3.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //условие косания картинки начало
                    if(event.getAction()==MotionEvent.ACTION_DOWN){
                        //Если каснулся начало
                        point11.setText(b+"*"+a+"="+c);
                        //Если каснулся конец
                    } else if (event.getAction()==MotionEvent.ACTION_UP){
                        //Если убрал начало
                        if(textAns3.getText().equals(String.valueOf(c))){
                            if(count<9){
                                count++;
                            } else {
                                dialog.show();
                                count=0;
                            }
                            //закрашиваем прогресс
                            for(int i=0;i<10;i++){
                                TextView tv =findViewById(progerss[i]);
                                tv.setBackgroundResource(R.drawable.style_points);
                            }
                            //Закрашиваем прогресс конец
                            //определение правильности ответа и закрашивание начало
                            for(int i=0;i<count;i++){
                                TextView tv =findViewById(progerss[i]);
                                tv.setBackgroundResource(R.drawable.style_points_green);

                            }
                            //определение правильности ответа и закрашивание конец
                        } else {
                            if(count>0){
                                if(count==1){
                                    count=0;
                                } else {
                                    count=count-2;
                                }
                            }
                            for(int i=0;i<9;i++){
                                TextView tv =findViewById(progerss[i]);
                                tv.setBackgroundResource(R.drawable.style_points);
                            }
                            //Закрашиваем прогресс конец
                            //определение правильности ответа и закрашивание начало
                            for(int i=0;i<count;i++){
                                TextView tv =findViewById(progerss[i]);
                                tv.setBackgroundResource(R.drawable.style_points_green);
                            }
                        }
                        a = random.nextInt(10);
                        b = 2;
                        c=a*b;
                        point11.setText(b+"*"+a+"="+"?");
                        ArrayList<Integer> answer = new ArrayList();
                        int aa=a+random.nextInt(5);
                        int bb=b+random.nextInt(5);
                        int dd=c+random.nextInt(5);
                        while(aa==c){
                            aa= random.nextInt(5);
                            while(bb==c){
                                bb= random.nextInt(5);
                                while(dd==c){
                                    dd= random.nextInt(5);
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
                    //условие косания картинки конец
                    return true;
                }
            });
            textAns4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //условие косания картинки начало
                    if(event.getAction()==MotionEvent.ACTION_DOWN){
                        //Если каснулся начало
                        point11.setText(b+"*"+a+"="+c);
                        //Если каснулся конец
                    } else if (event.getAction()==MotionEvent.ACTION_UP){
                        //Если убрал начало
                        if(textAns4.getText().equals(String.valueOf(c))){
                            if(count<9){
                                count++;
                            } else {
                                dialog.show();
                                count=0;
                            }
                            //закрашиваем прогресс
                            for(int i=0;i<10;i++){
                                TextView tv =findViewById(progerss[i]);
                                tv.setBackgroundResource(R.drawable.style_points);
                            }
                            //Закрашиваем прогресс конец
                            //определение правильности ответа и закрашивание начало
                            for(int i=0;i<count;i++){
                                TextView tv =findViewById(progerss[i]);
                                tv.setBackgroundResource(R.drawable.style_points_green);

                            }
                            //определение правильности ответа и закрашивание конец
                        } else {
                            if(count>0){
                                if(count==1){
                                    count=0;
                                } else {
                                    count=count-2;
                                }
                            }
                            for(int i=0;i<9;i++){
                                TextView tv =findViewById(progerss[i]);
                                tv.setBackgroundResource(R.drawable.style_points);
                            }
                            //Закрашиваем прогресс конец
                            //определение правильности ответа и закрашивание начало
                            for(int i=0;i<count;i++){
                                TextView tv =findViewById(progerss[i]);
                                tv.setBackgroundResource(R.drawable.style_points_green);
                            }
                        }
                        a = random.nextInt(10);
                        b = 2;
                        c=a*b;
                        point11.setText(b+"*"+a+"="+"?");
                        ArrayList<Integer> answer = new ArrayList();
                        int aa=a+random.nextInt(5);
                        int bb=b+random.nextInt(5);
                        int dd=c+random.nextInt(5);
                        while(aa==c){
                            aa= a+random.nextInt(5);
                            while(bb==c){
                                bb= b+random.nextInt(5);
                                while(dd==c){
                                    dd= c+random.nextInt(5);
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
            });

        //Обработка нажатия кнопок с ответами конец
    }

    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(Level1.this, GameLavels.class);
            startActivity(intent);
            finish();
        }catch (Exception e){

        }
    }
}