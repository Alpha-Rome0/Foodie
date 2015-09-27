package hacktx.foodie;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import org.json.JSONObject;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.Map;



/**
 * Created by Andy on 9/27/2015.
 */
public class ResultsFragment extends Fragment {
    private Button pizzabutton;
    private YelpAPI yelp;
    ArrayList<String> locations=new ArrayList<>();
    ArrayAdapter<String> adapter;


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

        locations.add("testing");
        adapter=new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.list_item_textview,locations);
        ListView listView=(ListView) rootView.findViewById(R.id.restaurants);
        listView.setAdapter(adapter);
        return rootView;
    }

    public class BackgroundTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                JSONObject json=new JSONObject(yelp.searchForBusinessesByLocation("pizza", "austin"));

                String country = " ";
                JSONObject jObj = new JSONObject(yelp.searchForBusinessesByLocation("pizza", "austin"));

                JSONArray jArray = jObj.getJSONArray("businesses");


                for(int j=0; j <jArray.length(); j++) {
                    JSONObject sys = jArray.getJSONObject(j);
                    locations.add(sys.getString("snippet_text"));
                    //country += "  " + sys.getString("location");
                }

                // Toast.makeText(this, country, Toast.LENGTH_LONG).show();

                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, country, );



            }catch(Exception e){
                Log.e("Background task error", "Error ", e);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void v) {
            adapter.notifyDataSetChanged();
        }


    }


}
