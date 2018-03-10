package baseballRecruitment.jd.DataLayer.Account;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(indices={@Index(value={"username"}, unique=true), @Index(value={"email"}, unique=true)})
public class Account implements Serializable {
    @PrimaryKey(autoGenerate=true)
    public int uid;

    public String name;
    public String email;
    public String username;
    public String password;
    public String organization;

    public Account(String name, String email, String username, String password, String organization) {
      this.email = email;
      this.username = username;
      this.password = password;
      this.organization = organization;
      this.name = name;
    }
}
