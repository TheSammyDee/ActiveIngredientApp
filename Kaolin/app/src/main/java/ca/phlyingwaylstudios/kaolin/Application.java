package ca.phlyingwaylstudios.kaolin;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by il on 23/11/2015.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Project.class);
        ParseObject.registerSubclass(Phase.class);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "tRfhiKAodhGmCI6n1Th4GIzz74NQwPllvt3obhwS", "Ag65kVQQACOnhIDf8cqcYtg4aBOhAy4vVI0wUDd5");
    }
}
