package baseballRecruitment.jd.DataLayer.Account;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AccountsDao {

  @Query("SELECT * FROM account")
  public List<Account> getAccounts();

  @Insert
  public void insertAccounts(Account... accounts);

  @Query("SELECT * FROM account WHERE username = :username AND password = :password")
  public Account[] lookupAccount(String username, String password);

  @Query("SELECT * FROM account WHERE email = :email AND password = :password")
  public Account[] lookupAccountEmail(String email, String password);
}
