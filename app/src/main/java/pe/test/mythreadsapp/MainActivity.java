package pe.test.mythreadsapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    private AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.progress_title);
        progressDialog.setMessage(getString(R.string.progress_message));
        progressDialog.setCancelable(false);

        findViewById(R.id.activity_main_Button_main_thread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeOnMainThread();
            }
        });

        findViewById(R.id.activity_main_Button_Worker_thread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeOnWorkerThread();
            }
        });

        findViewById(R.id.activity_main_Button_async_Task).setOnClickListener(new View.OnClickListener() {
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
        progressDialog.show();
        longTask();
        progressDialog.dismiss();
    }

    private void executeOnWorkerThread() {
        //se ejecuta la señal de activacion en el hilo proncipal pero debe de ocultarse el progreess en el hilo aparte
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                longTask();
                progressDialog.dismiss();
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
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                longTask();
                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressDialog.dismiss();
            }

        }.execute();
    }
}




