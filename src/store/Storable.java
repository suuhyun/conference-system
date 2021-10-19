package store;

import java.util.ArrayList;

public interface Storable {

    /**
     * Store entity as a list
     * @return ArrayList<String>
     */
    ArrayList<String> store();
}
