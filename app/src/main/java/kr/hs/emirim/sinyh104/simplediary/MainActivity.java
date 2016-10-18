package kr.hs.emirim.sinyh104.simplediary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    DatePicker datePic;
    EditText editDiary;
    Button butSave;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datePic=(DatePicker)findViewById(R.id.date_picker);
        editDiary=(EditText)findViewById(R.id.edit_content);
        butSave=(Button)findViewById(R.id.but_save);

        //현재 날짜 구하기
        Calendar calendar=Calendar.getInstance();
        int nowYear=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int date=calendar.get(Calendar.DATE);

        //DatePicker에 현재 날짜 설정

        datePic.init(nowYear,month,date, new DatePicker.OnDateChangedListener() {
            /**
             * Called upon a date change.
             *
             * @param view        The view associated with this listener.
             * @param year        The year that was set.
             * @param monthOfYear The month that was set (0-11) for compatibility
             *                    with {@link Calendar}.
             * @param dayOfMonth  The day of the month that was set.
             */
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fileName=year+"_"+(monthOfYear+1)+"_"+dayOfMonth+".txt";
                String content= null;
                try {
                    content = readDiary(fileName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                editDiary.setText(content);
                butSave.setEnabled(true);
            }
        });

    }
    String readDiary(String fileName) throws FileNotFoundException {
        String diaryContents=null;
        try {
            FileInputStream in=openFileInput(fileName);
            byte[] txt=new byte[500];
            in.read(txt);
            in.close();
            diaryContents=new String(txt);
            butSave.setText("수정하기");
        } catch (IOException e) {
            editDiary.setHint("읽어올 일기가 없음");
            butSave.setText("새로 저장");
        }
        return diaryContents;
    }
}
