package baseballRecruitment.jd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public abstract class RandomData {
    static Random random = new Random();

    public static int randomInt(int lower, int upper)
    {
        return random.nextInt(upper - lower) + lower;
    }

    public static String randomName() {
        return "Tyler";
    }

    public static Player randomPlayer() {
        return new Player(randomName(), randomInt(2050, 2110), (short) randomInt(0, 1024), true);
    }

    public static ArrayList<Player> randomPlayers(int count) {
        ArrayList<Player> list = new ArrayList<Player>(count);
        for (int i = 0; i < count; i++)
            list.add(randomPlayer());
        return list;
    }

    private static HashMap<String, String> mapForPlayer(Player p)
    {
        HashMap<String, String> m = new HashMap<String, String>();
        m.put("name", p.name);
        m.put("year", "" + p.year);
        m.put("positions", Player.posToString(p.positions));
        return m;
    }

    public static ArrayList<HashMap<String, String>> playerMaps(List<Player> ps) {
        ArrayList<HashMap<String, String>> pms = new ArrayList<HashMap<String, String>>(ps.size());
        for (int i = 0; i < ps.size(); i++)
            pms.add(mapForPlayer(ps.get(i)));
        return pms;
    }

    private static HashMap<String, String> detailMap(String label, String value) {
        HashMap<String, String> map = new HashMap<String, String>(2);
        map.put("label", label);
        map.put("value", value);
        return map;
    }

    private static ArrayList<HashMap<String, String>> playerDetails(Player p) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>(4);
        list.add(detailMap("rank", "" + randomInt(1, 200)));
        list.add(detailMap("batting average", "" + random.nextFloat()));
        list.add(detailMap("team", "Ocean City Mustang Ranchers"));
        list.add(detailMap("uniform number", "" + randomInt(0, 41)));
        return list;
    }

    public static ArrayList<ArrayList<HashMap<String, String>>> detailedPlayerMaps(List<Player> ps)
    {
        ArrayList<ArrayList<HashMap<String, String>>> list = new ArrayList<ArrayList<HashMap<String, String>>>(ps.size());
        for (int i = 0; i < ps.size(); i++)
            list.add(playerDetails(ps.get(i)));
        return list;
    }

}
