package baseballRecruitment.jd.DataLayer;

import java.io.IOException;
import java.util.ArrayList;

import android.util.Pair;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import baseballRecruitment.jd.Player;
import baseballRecruitment.jd.TournamentsData;

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

  public static Document get_tournaments() throws IOException {
    return get_pg("Schedule/Default.aspx?Type=Tournaments");
  }

  private static Pair<String, String> getIdName(Element e) {
    String href = e.attr("href");
    return new Pair<>(href.substring(href.lastIndexOf('=') + 1), e.text());
  }

  private static Elements getPlayerKeyedTableRows(Document d) {
    return Selector.select("tr:has(a[href~=Playerprofile.aspx])", d);
  }

  private static Elements getEventBoxes(Document d) {
    return Selector.select("div.EventBox", d);
  }

  private static TournamentsData getEventData(Element ebox) {
    Element dateElem = Selector.selectFirst("div[style=\"font-weight:bold; float:left\"]", ebox);
    String date = dateElem == null ? "ERROR" : dateElem.text();

    Element titleLocElem = Selector.selectFirst("center", ebox);
    Element titleElem = Selector.selectFirst("strong", titleLocElem);
    String title = titleElem == null ? "ERROR" : titleElem.text();
    String location = titleLocElem.textNodes().get(0).text();
    return new TournamentsData(title, date, location);
  }

  public static ArrayList<TournamentsData> getTournamentsData() {
    ArrayList<TournamentsData> tds = new ArrayList<>();
    try {
      for (Element e: getEventBoxes(get_tournaments()))
        tds.add(getEventData(e));
    } catch (IOException e) {}
    return tds;
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

  public static ArrayList<Player> get_top50_basic(String year) {
    ArrayList<Player> players = new ArrayList<>(0);
    try {
      players = get_top50(year);
    } catch (IOException e) {}
    return players;
  }
}
