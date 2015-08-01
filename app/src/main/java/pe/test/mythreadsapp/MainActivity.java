package pe.test.mythreadsapp;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.activity_main_Button_main_thread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeOnMainThread();
            }
        });

        findViewById(R.id.activity_main_Button_main_thread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeOnWorkerThread();
            }
        });

        findViewById(R.id.activity_main_Button_main_thread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeAsyncTask();
            }
        });
    }

    private void longTask() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void executeOnMainThread() {
        longTask();
    }

    private void executeOnWorkerThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                longTask();
            }
        }).start();
    }

    //Objeto del tipo generico
    //Params es el tipo de objeto que se recibe en el parametro para poner en el do y en background
    //progress devuelve el metodo postprogress
    //Result devuelve el objeto do in background ( en otro hilo)

    private void executeAsyncTask() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                longTask();
                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }

        }.execute();
    }
}




