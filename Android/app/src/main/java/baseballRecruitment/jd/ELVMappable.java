package baseballRecruitment.jd;

import android.content.Context;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

abstract class ELVMappable {

    private static final int [] group_views = {R.id.key, R.id.val2, R.id.val1};
    private static final int [] child_views = {R.id.label, R.id.value};
    private static final String [] child_keys = {"label", "value"};

    public abstract HashMap<String, String> map();
    public abstract ArrayList<HashMap<String, String>> detailMap();

    static SimpleExpandableListAdapter apply(ExpandableListView listView, Map map) {
        SimpleExpandableListAdapter adapt = new SimpleExpandableListAdapter(map.cxt, map.mapped, R.layout.watchlist_elv_group_view, map.group_keys, group_views, map.detailMapped, R.layout.watchlist_elv_child_view, child_keys, child_views);
        listView.setAdapter(adapt);
        return adapt;
    }

    static Map setup(Context c, String [] gk, List<? extends ELVMappable> mps) {
        return new Map(c, gk, mps);
    }

    static class Map {
        Context cxt;
        String [] group_keys;
        ArrayList<HashMap<String, String>> mapped;
        ArrayList<ArrayList<HashMap<String, String>>> detailMapped;

        Map(Context c, String [] gk, List<? extends ELVMappable> mappable) {
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
