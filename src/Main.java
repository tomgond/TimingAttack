/**
 * Created by tomgond on 4/16/2017.
 */
import timer.URLTimer;

public class Main {

    final static String URL = "http://132.72.64.31:8080?user={user}&password={password}";

    public static void main(String[] args) {

        URLTimer url_t = new URLTimer(URL, "tom");
        url_t.findLength();

    }
}
