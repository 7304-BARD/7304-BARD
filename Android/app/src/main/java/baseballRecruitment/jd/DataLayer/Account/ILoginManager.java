package baseballRecruitment.jd.DataLayer.Account;

/**
 * Created by sergeyscottnall on 11/27/17.
 */

public interface ILoginManager {
    boolean checkCredentials(String username, String password);
}
