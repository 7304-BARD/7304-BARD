package baseballRecruitment.jd;

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

import baseballRecruitment.jd.DataLayer.JPGS;

@Entity
  public class Player extends ELVMappable implements Serializable {

      static final String [] player_keys = {"name", "positions", "year"};

    @PrimaryKey
    @NonNull
    public String pgid;

    public String name;
    public String year;
    public String pos;
    public String pos2;
    public String age;
    public String height;
    public String weight;
    public String bt;
    public String hs;
    public String town;
    public String team_summer;
    public String team_fall;

    boolean watchlist;

    @Ignore
    boolean populated = false;

    public Player() {
        populated = true;
    }

    public Player(String id) throws IOException {
        this.pgid = id;
        populate();
    }

    public void populate() throws IOException {
      Document html  = JPGS.get_player(pgid);
      name = select("#ContentPlaceHolder1_Bio1_lblName", html);
      year = select("#ContentPlaceHolder1_Bio1_lblGradYear", html);
      pos = select("#ContentPlaceHolder1_Bio1_lblPrimaryPosition", html);
      pos2 = select("#ContentPlaceHolder1_Bio1_lblOtherPositions", html);
      age = select("#ContentPlaceHolder1_Bio1_lblAgeNow", html);
      height = select("#ContentPlaceHolder1_Bio1_lblHeight", html);
      weight = select("#ContentPlaceHolder1_Bio1_lblWeight", html);
      bt = select("#ContentPlaceHolder1_Bio1_lblBatsThrows", html);
      hs = select("#ContentPlaceHolder1_Bio1_lblHS", html);
      town = select("#ContentPlaceHolder1_Bio1_lblHomeTown", html);
      team_summer = select("#ContentPlaceHolder1_Bio1_lblSummerTeam", html);
      team_fall = select("#ContentPlaceHolder1_Bio1_lblFallTeam", html);
      populated = true;
    }

    public Player(String id, String name, String pos, String year) {
      this.pgid = id;
      this.name = name;
      this.pos = pos;
      this.year = year;
      populated = false;
    }

    private String select(String sel, Document html) {
      Element e = Selector.selectFirst(sel, html);
      return e == null ? "<N/A>" : e.text();
    }

    public HashMap<String, String> map() {
        HashMap<String, String> m = new HashMap<>(3);
        m.put("name", name);
        m.put("year", "" + year);
        m.put("positions", pos);
        return m;
    }

    private static void addIf(ArrayList<HashMap<String, String>> list, String k, String v) {
        if (v != null)
          list.add(detailMap(k, v));
    }

    public ArrayList<HashMap<String, String>> detailMap() {
        if (populated) {
          ArrayList<HashMap<String, String>> details = new ArrayList<>(9);
          addIf(details, "other positions", pos2);
          addIf(details, "age", age);
          addIf(details, "height", height);
          addIf(details, "weight", weight);
          addIf(details, "bats/throws", bt);
          addIf(details, "high school", hs);
          addIf(details, "home town", town);
          addIf(details, "summer team", team_summer);
          addIf(details, "fall team", team_fall);
          return details;
        }
        else {
          return new ArrayList<>(0);
        }
    }

    public String toString() {
       return name + " " + pos + " " + year;
    }

    public static HashMap<String, String> detailMap(String label, String value) {
        HashMap<String, String> map = new HashMap<>(2);
        map.put("label", label);
        map.put("value", value);
        return map;
    }
}
