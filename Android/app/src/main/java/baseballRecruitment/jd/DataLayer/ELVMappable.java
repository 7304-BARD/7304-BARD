package baseballRecruitment.jd.DataLayer;

import android.content.Context;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import baseballRecruitment.jd.R;

public abstract class ELVMappable {

    private static final int [] group_views = {R.id.key, R.id.val2, R.id.val1};
    private static final int [] child_views = {R.id.label, R.id.value};
    private static final String [] child_keys = {"label", "value"};

    public abstract HashMap<String, String> map();
    public abstract ArrayList<HashMap<String, String>> detailMap();

    public static SimpleExpandableListAdapter apply(ExpandableListView listView, Map map) {
        SimpleExpandableListAdapter adapt = new SimpleExpandableListAdapter(map.cxt, map.mapped, map.group_view, map.group_keys, group_views, map.detailMapped, R.layout.watchlist_elv_child_view, child_keys, child_views);
        listView.setAdapter(adapt);
        return adapt;
    }

    public static Map setup(Context c, String [] gk, List<? extends ELVMappable> mps) {
        return new Map(c, gk, mps);
    }

    public static class Map {
        public Context cxt;
        public String [] group_keys;
        public ArrayList<HashMap<String, String>> mapped;
        public ArrayList<ArrayList<HashMap<String, String>>> detailMapped;
        public int group_view = R.layout.watchlist_elv_group_view;

        public Map(Context c, String [] gk, List<? extends ELVMappable> mappable) {
            cxt = c;
            group_keys = gk;
            mapped = new ArrayList<>(mappable.size());
            detailMapped = new ArrayList<>(mappable.size());
            for (ELVMappable m : mappable) {
                mapped.add(m.map());
                detailMapped.add(m.detailMap());
            }
        }
    }

}
