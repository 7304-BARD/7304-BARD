package baseballRecruitment.jd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.ArrayList;

import baseballRecruitment.jd.DataLayer.JPGS;

@EActivity(R.layout.activity_search)
public class SearchActivity extends AppCompatActivity {
    static final String extraKeyNewPlayer = "38e11729-f6e8-4be7-90a5-50c2e5b30044";

    @ViewById
    SearchView searchView;

    @ViewById
    ListView searchResults;

    ArrayList<JPGS.Player> results = new ArrayList<>(0);

    @AfterViews
    protected void init() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                search(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra(extraKeyNewPlayer, results.get(i));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Background
    protected void search(String s) {
        try {
            results = JPGS.searchPlayers(s);
        } catch (IOException e) {}
        setResults();
    }

    @UiThread
    protected void setResults() {
        searchResults.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, results));
    }
}
