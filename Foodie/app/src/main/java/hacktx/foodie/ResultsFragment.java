package hacktx.foodie;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Andy on 9/27/2015.
 */
public class ResultsFragment extends Fragment {
    private Button pizzabutton;
    private YelpAPI yelp;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        yelp = new YelpAPI(YelpAPI.CONSUMER_KEY, YelpAPI.CONSUMER_SECRET, YelpAPI.TOKEN, YelpAPI.TOKEN_SECRET);

        pizzabutton = (Button) rootView.findViewById(R.id.pizza_button);
        pizzabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });
        return rootView;
    }

    public class BackgroundTask extends AsyncTask {
        protected Object doInBackground(Object[] params) {
            try {
                String test = yelp.searchForBusinessesByLocation("pizza", "austin");
            }catch(Exception e){
                Log.e("Background task error", "Error ", e);
            }
            return null;
        }

    }

}
