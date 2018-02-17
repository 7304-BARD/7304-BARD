package baseballRecruitment.jd;

/**
 * Created by hmgut_000 on 2/14/2018.
 */

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Selector;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import baseballRecruitment.jd.DataLayer.JPGS;


@Entity
    public class TournamentsData implements Serializable {
    @PrimaryKey
    @NonNull

    public String title;
    public String date;
    public String location;

    @Ignore
    boolean populate = false;

    public TournamentsData() throws IOException {
        populate();
    }

    public void populate() throws IOException {
        Document html = JPGS.get_tournaments();
        title = select("#ContentPlaceHolder1_repSchedule_h1EventName_0", html);
        date = select("#ContentPlaceHolder1_repSchedule_h1EventDate_0", html);
        location = select("#ContentPlacerHolder1_repSchedule_h1EventLocation_0", html);
        populate = true;
    }

    public TournamentsData(String title, String date, String location) {
        this.title = title;
        this.date = date;
        this.location = location;
        populate = false;
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


    private static void addIf(ArrayList<HashMap<String, String>> list, String k, String v) {
        if (v != null)
            list.add(detailMap(k, v));
    }

    public ArrayList<HashMap<String, String>> detailMap() {
        ArrayList<HashMap<String, String>> details = new ArrayList<>(3);
        addIf(details, "event name", title);
        addIf(details, "event date", date);
        addIf(details, "event location", location);
        return details;
    }

    public String toString() {
        return title + " " + date + " " + location;
    }

    public static ArrayList<HashMap<String, String>> tournamentMaps(List<TournamentsData> ts) {
        ArrayList<HashMap<String, String>> tms = new ArrayList<>(ts.size());
        for (int i = 0; i < ts.size(); i++)
            tms.add(ts.get(i).map());
        return tms;
    }

    public static HashMap<String, String> detailMap(String label, String value) {
        HashMap<String, String> map = new HashMap<>(2);
        map.put("label", label);
        map.put("value", value);
        return map;
    }

    public static ArrayList<ArrayList<HashMap<String, String>>> detailedTournamentMaps(List<TournamentsData> ts)
    {
        ArrayList<ArrayList<HashMap<String, String>>> list = new ArrayList<>(ts.size());
        for (int i = 0; i < ts.size(); i++)
            list.add(ts.get(i).detailMap());
        return list;
    }
}

