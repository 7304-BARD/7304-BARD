package baseballRecruitment.jd.DataLayer.Tournament;

/**
 * Created by hmgut_000 on 2/14/2018.
 */

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Selector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import baseballRecruitment.jd.DataLayer.ELVMappable;

@Entity
    public class Tournament extends ELVMappable implements Serializable {
      public static final String [] keys = {"title", "location", "date"};

    @PrimaryKey(autoGenerate=true)
    int id;

    public String title;
    public String date;
    public String location;

    public Tournament(String title, String date, String location) {
        this.title = title;
        this.date = date;
        this.location = location;
    }

    private String select(String sel, Document html) {
        Element e = Selector.selectFirst(sel, html);
        return e == null ? "<N/A>" : e.text();
    }

    public HashMap<String, String> map() {
        HashMap<String, String> m = new HashMap<>(3);
        m.put("title", title);
        m.put("date", "" + date);
        m.put("location", location);
        return m;
    }

    public ArrayList<HashMap<String, String>> detailMap() {
        return new ArrayList<>(0);
    }

    public String toString() {
        return title + " " + date + " " + location;
    }

    public static HashMap<String, String> detailMap(String label, String value) {
        HashMap<String, String> map = new HashMap<>(2);
        map.put("label", label);
        map.put("value", value);
        return map;
    }
}

