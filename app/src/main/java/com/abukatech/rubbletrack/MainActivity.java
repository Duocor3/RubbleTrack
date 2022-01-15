package com.abukatech.rubbletrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements LocationListener {

    Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12;

    double lat;
    double lng;

    String color;

    boolean delay = false;

    ArrayList<String[]> dataArray = new ArrayList<>();

    LocationManager locationManager;

    int permissionCheck;

    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        button10 = findViewById(R.id.button10);
        button11 = findViewById(R.id.button11);
        button12 = findViewById(R.id.button12);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLocation(button1);
                color = "1";
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLocation(button2);
                color = "2";
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLocation(button3);
                color = "3";
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLocation(button4);
                color = "4";
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLocation(button5);
                color = "5";
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLocation(button6);
                color = "6";
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLocation(button7);
                color = "7";
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLocation(button8);
                color = "8";
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLocation(button9);
                color = "9";
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLocation(button10);
                color = "10";
            }
        });

        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLocation(button11);
                color = "11";
            }
        });

        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLocation(button12);
                color = "12";
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION : {
                // If request is cancelled, the result arrays are empty.
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
    }

    private void displayLocation(final Button b) {
        TextView txtLat = findViewById(R.id.location);
        String msg = "(" + lat + ", " + lng + ")" + " " + b.getText().toString();
        txtLat.setText(msg);

        File root = new File(this.getFilesDir().getAbsolutePath(), "Location Data");
        try {
            if (!root.exists()) {
                root.mkdirs();
            }

            if (!isConnectedToWifi()) {
                delay = true;
            }

            File gpxfile = new File(root, "data.txt");
            FileWriter writer;

            if (!isConnectedToWifi() || delay) {
                writer = new FileWriter(gpxfile, true);

                if (isConnectedToWifi()) {
                    delay = false;
                }
            } else {
                writer = new FileWriter(gpxfile, false);
            }

            writer.append(msg + "\n");
            writer.flush();
            writer.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

            TextView d = findViewById(R.id.data);
            d.setText(read_file(getApplicationContext(), "data.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        dataArray = new ArrayList<>();

        String[] newlineSeparate = read_file(getApplicationContext(), "data.txt").split("\n");

        for (int i = 0; i < newlineSeparate.length; i++) {
            String[] spaceSeparate = newlineSeparate[i].split(" ");

            spaceSeparate[0] = spaceSeparate[0].substring(1, spaceSeparate[0].length() - 1);
            spaceSeparate[1] = spaceSeparate[1].substring(0, spaceSeparate[1].length() - 1);

            dataArray.add(spaceSeparate);
        }

        if (isConnectedToWifi()) {
            for (int i = 0; i < dataArray.size(); i++) {
                lat = Double.parseDouble(dataArray.get(i)[0]);
                lng = Double.parseDouble(dataArray.get(i)[1]);
                color = dataArray.get(i)[2];

                new AsyncCaller().execute();
            }
        }

        /*
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        File filelocation = new File(root, "data.txt");
        Uri path = Uri.fromFile(filelocation);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
// set the type to 'email'
        emailIntent .setType("vnd.android.cursor.dir/email");
        String to[] = {"tsegazeab12@gmail.com"};
        emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
// the attachment
        emailIntent .putExtra(Intent.EXTRA_STREAM, path);
// the mail subject
        emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Data File");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                read_file(getApplicationContext(), "data.txt"));
        startActivity(Intent.createChooser(emailIntent , "Send email..."));*/

        /*Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    sendResultToServer(lat, lng, b.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new NullPointerException();
                }
            }
        });

        thread.start();*/
    }

    private boolean isConnectedToWifi() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else {
            connected = false;
        }

        return connected;
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tSending...");
            pdLoading.show();
        }
        @Override
        protected Void doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
            File root = new File(MainActivity.this.getFilesDir().getAbsolutePath(), "Location Data");
            File filelocation = new File(root, "data.txt");
            Uri path = Uri.fromFile(filelocation);

            /*try {
                GMailSender sender = new GMailSender("tsega@mocohacks.org", "nb*w2D%M7AM4S1b");
                sender.sendMail("Data File",
                        read_file(getApplicationContext(), "data.txt"),
                        "tsega@mocohacks.org",
                        "tsegazeab12@gmail.com");
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "broken 1", Toast.LENGTH_SHORT).show();
            }*/

            /*SheetsQuickstart update = new SheetsQuickstart();
            try {
               update.updateToSpreadsheet();
            } catch (IOException e) {
                //Toast.makeText(getApplicationContext(), "broken 2", Toast.LENGTH_SHORT).show();
                Log.e("EXCEPTION 1:",  e.getMessage());
            } catch (GeneralSecurityException e) {
                //Toast.makeText(getApplicationContext(), "broken 3", Toast.LENGTH_SHORT).show();
                Log.e("EXCEPTION 2:",  e.getMessage());
            }*/

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://docs.google.com/forms/d/e/")
                    .build();
            final QuestionsSpreadsheetWebService spreadsheetWebService = retrofit.create(QuestionsSpreadsheetWebService.class);

            String nameInput = String.valueOf(lat);
            String catQuestionInput = String.valueOf(lng);
            Call<Void> completeQuestionnaireCall = spreadsheetWebService.completeQuestionnaire(nameInput, catQuestionInput, color);
            completeQuestionnaireCall.enqueue(callCallback);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.check).setTitle("Sent successfully").setMessage("Thank you! Your report has been sent to the agency. (እናመሰግናለን። ሪፖርትዎ ለኤጀንሲው በትክክል ተልኳል)");
            final AlertDialog alert = dialog.create();
            alert.show();

// Hide after some seconds
            final Handler handler  = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (alert.isShowing()) {
                        alert.dismiss();
                    }
                }
            };

            alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    handler.removeCallbacks(runnable);
                }
            });

            handler.postDelayed(runnable, 3000);

            //this method will be running on UI thread
            pdLoading.dismiss();
        }

    }

    private final Callback<Void> callCallback = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            Log.d("XXX", "Submitted. " + response);
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            Log.e("XXX", "Failed", t);
        }
    };

    public String read_file(Context context, String filename) {
        try {
            File root = new File(this.getFilesDir().getAbsolutePath(), "Location Data");
            File filelocation = new File(root, filename);

            FileInputStream fis = new FileInputStream(filelocation);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            return "file not found";
        } catch (UnsupportedEncodingException e) {
            return "uhh...";
        } catch (IOException e) {
            return "UHH...";
        }
    }

    private void sendResultToServer(double lat, double lng, String tag) throws IOException {
        // try 1
        String msg = "(" +lat + ", " + lng + ") " + tag;

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://10.0.2.2");
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("id", "12345"));
            nameValuePairs.add(new BasicNameValuePair("message", msg));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpclient.execute(httppost);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new NullPointerException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new NullPointerException();
        }
        // try 2
        /*URL url = new URL("http://localhost/");
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("Location", "(" + lat + ", " + lng + ")" + " " + tag);

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        StringBuilder sb = new StringBuilder();
        for (int c; (c = in.read()) >= 0;)
            sb.append((char)c);
        String response = sb.toString();*/
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}
