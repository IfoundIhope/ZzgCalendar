package com.example.zzgcalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zzgcalendar.dialog.DateDayDialog;
import com.example.zzgcalendar.util.MessageTools;
import com.example.zzgcalendar.util.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView tvSelectBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvSelectBirthday = findViewById(R.id.tv_select_birthday);
        tvSelectBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDateDialog.show();
            }
        });

        initTimeDate();
    }

    /**
     * 日期选择
     */
    DateDayDialog timeDateDialog;
    String time;

    private void initTimeDate() {
        String birthdayStr = tvSelectBirthday.getText().toString().trim();
        if (birthdayStr != null && birthdayStr.length() > 3) {
            birthdayStr = birthdayStr.substring(0, birthdayStr.length() - 2);
        }
        timeDateDialog = new DateDayDialog(this, birthdayStr, new DateDayDialog.DateDialogValue() {

            @Override
            public boolean findDateDialogValue(final String date) {
                String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                Long diffDate = TimeUtils.getDaysOfTowDiffDate(date, today);
                if (diffDate < 0) {
                    MessageTools.showToast(MainActivity.this, "请设置今天之前的日期");
                    return false;
                }
                time = date + ":00:00";
                tvSelectBirthday.setText(date + " 时");
                return true;
            }
        }).builder().setPositiveButton("确定", null).setNegativeButton("取消", null);
    }
}
