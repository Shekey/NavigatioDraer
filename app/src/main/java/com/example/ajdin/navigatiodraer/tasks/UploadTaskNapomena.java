package com.example.ajdin.navigatiodraer.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.WriteMode;
import com.example.ajdin.navigatiodraer.Fragments.MenuFragment;
import com.example.ajdin.navigatiodraer.R;
import com.example.ajdin.navigatiodraer.helpers.DatabaseHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ajdin on 17.3.2018..
 */

public class UploadTaskNapomena extends AsyncTask {

    private DbxClientV2 dbxClient;
    private File file;
    private Context context;

    public UploadTaskNapomena(DbxClientV2 dbxClient, File file, Context context) {
        this.dbxClient = dbxClient;
        this.file = file;
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            // Upload to Dropbox
            InputStream inputStream = new FileInputStream(file);
            dbxClient.files().uploadBuilder("/Napomene/" + file.getName()) //Path in the user's Dropbox to save the file.
                    .withMode(WriteMode.OVERWRITE) //always overwrite existing file
                    .uploadAndFinish(inputStream);
            Log.d("Upload Status", "Success");
        } catch (DbxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        DatabaseHelper db=new DatabaseHelper(context);
        db.setStacked(file.getPath());
        Toast.makeText(context, "File uploaded successfully", Toast.LENGTH_SHORT).show();

    }

}