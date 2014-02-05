package esir.dom13.nsoc.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import esir.dom13.nsoc.android_websockets.WebSocketClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class MainActivvity extends Activity {
    final String TAG = "Main Activity LOG ::  ";
    WebSocketClient client;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        /**
         * BUTTON
         */
        com.beardedhen.androidbootstrap.BootstrapButton button_scenario_entree = (com.beardedhen.androidbootstrap.BootstrapButton)findViewById(R.id.button_scenario_entree);
        com.beardedhen.androidbootstrap.BootstrapButton button_scenario_sortie = (com.beardedhen.androidbootstrap.BootstrapButton)findViewById(R.id.button_scenario_sortie);
        com.beardedhen.androidbootstrap.BootstrapButton button_volet_up = (com.beardedhen.androidbootstrap.BootstrapButton)findViewById(R.id.button_volet_up);
        com.beardedhen.androidbootstrap.BootstrapButton button_volet_down = (com.beardedhen.androidbootstrap.BootstrapButton)findViewById(R.id.button_volet_down);
        com.beardedhen.androidbootstrap.BootstrapButton button_lampe_on = (com.beardedhen.androidbootstrap.BootstrapButton)findViewById(R.id.button_lampe_on);
        com.beardedhen.androidbootstrap.BootstrapButton button_lampe_off = (com.beardedhen.androidbootstrap.BootstrapButton)findViewById(R.id.button_lampe_off);


        button_scenario_entree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject object = new JSONObject();

                try {
                    object.put("type", "scenario_entree");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                client.send(object.toString());
            }
        });

        button_scenario_sortie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject object = new JSONObject();

                try {
                    object.put("type", "scenario_sortie");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                client.send(object.toString());
            }
        });

        button_volet_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject object = new JSONObject();

                try {
                    object.put("type", "volet");
                    object.put("value",false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                client.send(object.toString());
            }
        });

        button_volet_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject object = new JSONObject();

                try {
                    object.put("type", "volet");
                    object.put("value",true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                client.send(object.toString());
            }
        });

        button_lampe_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject object = new JSONObject();

                try {
                    object.put("type", "lampe");
                    object.put("value",true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                client.send(object.toString());
            }
        });

        button_lampe_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject object = new JSONObject();

                try {
                    object.put("type", "lampe");
                    object.put("value",false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                client.send(object.toString());
            }
        });



        List<BasicNameValuePair> extraHeaders = Arrays.asList(
                new BasicNameValuePair("Cookie", "session=abcd")
        );

        client = new WebSocketClient(URI.create("ws://192.168.1.100:9999/socket"), new WebSocketClient.Listener() {
            @Override
            public void onConnect() {
            }

            @Override
            public void onMessage(String message) {
                Log.d(TAG, String.format("Got string message! %s", message));
              //  Toast.makeText(getApplication(),message,Toast.LENGTH_LONG).show();
       //         makeToast(message);

            }

            @Override
            public void onMessage(byte[] data) {
                Log.d(TAG, String.format("Got binary message! %s", data));
            }

            @Override
            public void onDisconnect(int code, String reason) {
                Log.d(TAG, String.format("Disconnected! Code: %d Reason: %s", code, reason));
            }

            @Override
            public void onError(Exception error) {
                Log.e(TAG, "Error!", error);
            }

        }, extraHeaders);

        client.connect();

    }

    private void makeToast(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

       client.disconnect();
    }
}
