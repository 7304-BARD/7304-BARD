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

        //public Tournament() {}

        //public Tournament() throws IOException {
           // populate();
        //}

        public void populate() throws IOException {
            Document html = JPGS.get_tournaments();
            //title = select()
        }
    }


