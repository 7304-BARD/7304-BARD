package baseballRecruitment.jd;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;

@EActivity(R.layout.activity_new_meeting)
public class NewMeeting extends AppCompatActivity {

    Calendar date;
    Calendar start;
    Calendar end;
    String details = "";

    @ViewById
    EditText meeting_info;

    @AfterViews
    protected void load() {
        date = Calendar.getInstance();
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        meeting_info.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {}
            public void onTextChanged(CharSequence cs, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                details = editable.toString();
            }
        });
    }

    private static void copyFrom(Calendar dst, Calendar src, int field) {
        dst.set(field, src.get(field));
    }

    private static void updateDate(Calendar dst, Calendar src) {
        copyFrom(dst, src, Calendar.YEAR);
        copyFrom(dst, src, Calendar.MONTH);
        copyFrom(dst, src, Calendar.DAY_OF_MONTH);
    }

    public void submit(View view) {
        Intent result = new Intent();
        updateDate(start, date);
        updateDate(end, date);
        result.putExtra("start", start);
        result.putExtra("end", end);
        result.putExtra("details", details);
        setResult(Activity.RESULT_OK, result);
        finish();
    }

    @Click
    public void meeting_date(final EditText et) {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date.set(Calendar.YEAR, year);
                date.set(Calendar.MONTH, month);
                date.set(Calendar.DAY_OF_MONTH, day);
                displayDate(et, date);
            }
        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setTime(final EditText et, final Calendar cal) {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                cal.set(Calendar.HOUR_OF_DAY, hour);
                cal.set(Calendar.MINUTE, minute);
                displayTime(et, cal);
            }
        }, 0, 0, false).show();
    }

    @Click
    public void start_time(EditText et) {
        setTime(et, start);
    }

    @Click
    public void end_time(EditText et) {
        setTime(et, end);
    }

    void displayTime(EditText et, Calendar cal) {
        et.setText(DateFormat.getTimeFormat(this).format(cal.getTime()));
    }

    void displayDate(EditText et, Calendar cal) {
        et.setText(DateFormat.getDateFormat(this).format(cal.getTime()));
    }
}
