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

import baseballRecruitment.jd.Player;

public class JPGS {

  private static Document get_pg(String resource) throws IOException {
    return Jsoup.connect("http://www.perfectgame.org/" + resource).get();
  }

  private static Document get_search(String query) throws IOException {
    return get_pg("Search.aspx?search=" + query);
  }

  public static Document get_player(String id) throws IOException {
    return get_pg("Players/Playerprofile.aspx?ID=" + id);
  }

  private static Pair<String, String> getIdName(Element e) {
    String href = e.attr("href");
    return new Pair<>(href.substring(href.lastIndexOf('=') + 1), e.text());
  }

  private static Elements getPlayerKeyedTableRows(Document d) {
    return Selector.select("tr:has(a[href~=Playerprofile.aspx])", d);
  }

  public static ArrayList<Player> searchPlayers(String query) throws IOException {
    ArrayList<Player> al = new ArrayList<>();
    for (Element r: getPlayerKeyedTableRows(get_search(query))) {
      Pair<String, String> idName = getIdName(r.child(0).child(0));
      al.add(new Player(idName.first, idName.second, r.child(1).text(), r.child(2).text()));
    }
    return al;
  }

  public static ArrayList<Player> get_top50(String year) throws IOException {
    ArrayList<Player> al = new ArrayList<>();
    for (Element r : getPlayerKeyedTableRows(get_pg("Rankings/Players/NationalRankings.aspx?gyear=" + year))) {
      Pair<String, String> idName = getIdName(r.child(1).child(0));
      al.add(new Player(idName.first, idName.second, r.child(2).text(), year));
    }
    return al;
  }

  public static Pair<ArrayList<Player>, ArrayList<HashMap<String, String>>> get_top50_basic(String year) {
    ArrayList<HashMap<String, String>> rv = new ArrayList<>(50);
    ArrayList<Player> players = new ArrayList<>(0);
    try {
      players = get_top50(year);
      for (Player p : players)
        rv.add(p.map());
    } catch (IOException e) {}
    return new Pair<>(players, rv);
  }

  public static ArrayList<Player> getPlayers(final ArrayList<Player> ids) {
    ArrayList<Thread> ts = new ArrayList<>(ids.size());
    final ArrayList<Player> ps = new ArrayList<>(ids.size());
    for (int i = 0; i < ids.size(); i++) {
      final int e = i;
      ps.add(null);
      Thread t = new Thread(new Runnable() {
        public void run() {
          try {
            ps.set(e, new Player(ids.get(e).pgid));
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
