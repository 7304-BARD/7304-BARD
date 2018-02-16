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

}


