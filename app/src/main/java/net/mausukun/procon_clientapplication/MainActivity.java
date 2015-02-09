package net.mausukun.procon_clientapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends ActionBarActivity implements Runnable{
    private CustomListViewAdapter myAdapter;
    private Handler handler = new Handler();
    private ListView listView;
    private Button sendButton;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myAdapter = new CustomListViewAdapter(this);

        listView = (ListView)findViewById(R.id.listView);
        editText = (EditText)findViewById(R.id.editText);
        sendButton = (Button) findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(editText.getText().toString());
                editText.setText("");
            }
        });

        /*for(int i=0;i<100;i++){
            Random rand = new Random();
            myAdapter.add(new UserData(i, i+"あいうえお", "Hello World!", new GeoLocation(rand.nextDouble(), rand.nextDouble())));
        }*/

        listView.setAdapter(myAdapter);
    }

    @Override
    public void onStart(){
        super.onStart();
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void onStop(){
        super.onStop();
        close();
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

    private static String host = "mausukun.net";
    private static int port = 10625;

    private Socket socket;
    @Override
    public void run(){
        try {
            try {
                socket = new Socket(host, port);
            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            while(!socket.isClosed()){

                final String message = reader.readLine();
                if(message == null) break;

                analyzeMessage(message);
            }
        } catch (IOException e) {
            //ソケットがクローズされた時
            //e.printStackTrace();
        }
        //ソケットクローズ -> スレッド終了
    }

    //サーバーからの受信メッセージを解析する。
    private void analyzeMessage(final String message){
        //メッセージに応じた処理を行う
        showMessage(message);
    }

    public void close(){
        try {
            Log.d("MainActivity", "Socket Closing...");
            sendMessage(":q");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(final String message){
        try {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output);

            writer.println(message);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMessage(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                myAdapter.add(new UserData(-1, "jacobi & faultaro", message, new GeoLocation(10, 20)));
                myAdapter.notifyDataSetChanged();

                if(listView.getFirstVisiblePosition() < myAdapter.getCount()-10){
                    if(listView.getChildAt(0) != null){
                        int yOffset = listView.getChildAt(0).getTop();
                        int position = listView.getFirstVisiblePosition();
                        listView.setSelectionFromTop(position, yOffset);
                    }
                }else{
                    listView.setSelection(myAdapter.getCount());
                }

            }
        });
    }
}
