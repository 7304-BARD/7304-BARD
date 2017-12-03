package baseballRecruitment.jd;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class NewMeeting extends AppCompatActivity {

    Calendar date;
    Calendar start;
    Calendar end;
    String details = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);
        date = Calendar.getInstance();
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        ((EditText) findViewById(R.id.meeting_info)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                details = editable.toString();
            }
        });
    }

    void submit(View view) {
        Intent result = new Intent();
        result.putExtra("date", date);
        result.putExtra("start", start);
        result.putExtra("end", end);
        result.putExtra("details", details);
        setResult(Activity.RESULT_OK, result);
        finish();
    }

    public void setDate(final View view) {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date.set(Calendar.YEAR, year);
                date.set(Calendar.MONTH, month);
                date.set(Calendar.DAY_OF_MONTH, day);
                displayDate((EditText) view, date);
            }
        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void setTime(View v, Calendar cal) {
        new TimePickerDialog(this, new OTSL((EditText) v, cal), 0, 0, false).show();
    }

    public void setStartTime(View v) {
        setTime(v, start);
    }

    public void setEndTime(View v) {
        setTime(v, end);
    }

    void displayTime(EditText et, Calendar cal) {
        et.setText(DateFormat.getTimeFormat(this).format(cal.getTime()));
    }

    void displayDate(EditText et, Calendar cal) {
        et.setText(DateFormat.getDateFormat(this).format(cal.getTime()));
    }

    private class OTSL implements TimePickerDialog.OnTimeSetListener {
        EditText et;
        Calendar cal;

        OTSL(EditText et, Calendar cal) {
            this.et = et;
            this.cal = cal;
        }

        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            cal.set(Calendar.HOUR, hour);
            cal.set(Calendar.HOUR, minute);
            displayTime(et, cal);
        }
    }
}
