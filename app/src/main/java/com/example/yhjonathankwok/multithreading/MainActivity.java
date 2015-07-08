package com.example.yhjonathankwok.multithreading;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class MainActivity extends ActionBarActivity {

    private ListAdapter numberAdapter;
    private String filename = "numbers.txt";
    private List<String> numberLoad = new ArrayList<String>();
    private ListView listView;
    private FileOutputStream numberOut;

    private ProgressBar progressBar;
    private int progressStatus;
    private TextView status;

    Handler loader = new Handler() {
        @Override
        public void handleMessage (Message msg) { loadList(); }
    };

    Handler progressHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            progressBar.setProgress(progressStatus);
            status.setText(progressStatus + "/" + progressBar.getMax());
        }

    };

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //numberAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, numberLoad);
        numberAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, numberLoad);
        listView = (ListView) findViewById(R.id.list);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        progressStatus = 0;
        progressBar.setProgress(0);
        status = (TextView) findViewById(R.id.textView);

        /*
        listView = (ListView)findViewById(R.id.list);
        Scanner s = null;
        try {
            s = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> list = new ArrayList<String>();
        while (s.hasNext()){
            list.add(s.next());
        }
        s.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, list);

        // Assign adapter to ListView
        listView.setAdapter(adapter);*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void create(View view) {

        Thread theThread = new Thread(new Runnable() {
            @Override
            public void run() {
                createFile();
                writeToFile();
            }
        });
        theThread.start();
        /*String string = null;
        for (int i = 1; i <= 10; i++) {
            string += i + '\n';
        }
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(250);
        Context context = getApplicationContext();
        CharSequence text = "File created!!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        */
    }

    public void createFile() {
        try {
            numberOut = openFileOutput(filename, MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile() {
        String[] numberArray = new String[10];
        int index = 0;

        for (int i = 1; i < 11; i++) {
            numberArray[index++] = "" + i;
        }

        try {
            OutputStreamWriter outWrite = new OutputStreamWriter(numberOut);

            for (int i = 0; i < 10; i++) {
                outWrite.write(numberArray[i]);
                outWrite.write("\n");
                progressStatus += 10;
                progressHandler.sendEmptyMessage(0);
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            outWrite.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(View view) throws FileNotFoundException {

        // ListView Item Click Listener
        /*listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

            }

        });*/

    }


}
