package baseballRecruitment.jd.DataLayer;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

public class JPGS {
  private static class Player {
    String id;
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

    private String select(String sel, Document html) {
      Element e = Selector.selectFirst(sel, html);
      return e == null ? "<N/A>" : e.text();
    }
  }

  private static Document get_pg(String resource) throws IOException {
    return Jsoup.connect("http://www.perfectgame.org/" + resource).get();
  }

  private static Document get_player(String id) throws IOException {
    return get_pg("Players/Playerprofile.aspx?ID=" + id);
  }

  private static ArrayList<String> get_top50(String year) throws IOException {
    Elements t50 = Selector.select("a[href~=Playerprofile.aspx]", get_pg("Rankings/Players/NationalRankings.aspx?gyear=" + year));
    ArrayList<String> al = new ArrayList<String>(t50.size());
    for (Element e : t50) {
      String href = e.attr("href");
      al.add(href.substring(href.lastIndexOf('=') + 1));
    }
    return al;
  }

  private static Player [] get_top50_players(String year) throws IOException {
    final ArrayList<String> ids = get_top50(year);
    ArrayList<Thread> ts = new ArrayList<Thread>(ids.size());
    final Player [] ps = new Player [ids.size()];
    for (int i = 0; i < ids.size(); i++) {
      final int e = i;
      Thread t = new Thread(new Runnable() {
        public void run() {
          try {
            ps[e] = new Player(ids.get(e));
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
