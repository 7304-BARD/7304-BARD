package baseballRecruitment.jd.DataLayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.util.Pair;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import baseballRecruitment.jd.RandomData;

public class JPGS {

  public static class Player {
    public String id;
    String name;
    String year;
    String pos;
    String pos2;
    String age;
    String height;
    String weight;
    String bt;
    String hs;
    String town;
    String team_summer;
    String team_fall;

    public Player(String id) throws IOException {
      this.id  = id;
      Document html  = get_player(id);
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
    }

    public Player(String id, String name, String pos, String year) {
      this.id = id;
      this.name = name;
      this.pos = pos;
      this.year = year;
    }

    private String select(String sel, Document html) {
      Element e = Selector.selectFirst(sel, html);
      return e == null ? "<N/A>" : e.text();
    }

    public HashMap<String, String> map() {
        HashMap<String, String> m = new HashMap<String, String>(3);
        m.put("name", name);
        m.put("year", "" + year);
        m.put("positions", pos);
        return m;
    }

    private static void addIf(ArrayList<HashMap<String, String>> list, String k, String v) {
        if (v != null)
          list.add(RandomData.detailMap(k, v));
    }

    public ArrayList<HashMap<String, String>> detailMap() {
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
  }

  private static Document get_pg(String resource) throws IOException {
    return Jsoup.connect("http://www.perfectgame.org/" + resource).get();
  }

  private static Document get_player(String id) throws IOException {
    return get_pg("Players/Playerprofile.aspx?ID=" + id);
  }

  public static ArrayList<Player> get_top50(String year) throws IOException {
    Document t50 = get_pg("Rankings/Players/NationalRankings.aspx?gyear=" + year);
    Elements rows = Selector.select("tr:has(a[href~=Playerprofile.aspx])", t50);
    ArrayList<Player> al = new ArrayList<Player>(rows.size());
    for (Element r : rows) {
      Element nameId = r.child(1).child(0);
      String href = nameId.attr("href");
      String id = href.substring(href.lastIndexOf('=') + 1);
      al.add(new Player(id, nameId.text(), r.child(2).text(), year));
    }
    return al;
  }

  public static Pair<ArrayList<Player>, ArrayList<HashMap<String, String>>> get_top50_basic(String year) {
    ArrayList<HashMap<String, String>> rv = new ArrayList(50);
    ArrayList<Player> players = new ArrayList<Player>(0);
    try {
      players = get_top50(year);
      for (Player p : players)
        rv.add(p.map());
    } catch (IOException e) {}
    return new Pair(players, rv);
  }

  public static ArrayList<Player> getPlayers(final ArrayList<Player> ids) throws IOException {
    ArrayList<Thread> ts = new ArrayList<Thread>(ids.size());
    final ArrayList<Player> ps = new ArrayList<Player>(ids.size());
    for (int i = 0; i < ids.size(); i++) {
      final int e = i;
      ps.add(null);
      Thread t = new Thread(new Runnable() {
        public void run() {
          try {
            ps.set(e, new Player(ids.get(e).id));
          }
          catch (IOException e) {}
        }
      });
      ts.add(t);
      t.start();
    }
    for (Thread t : ts)
      try {
        t.join();
      }
      catch (InterruptedException e) {}
    return ps;
  }
}
