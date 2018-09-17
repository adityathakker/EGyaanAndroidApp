package com.adityathakker.egyaan.fragments.notes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adityathakker.egyaan.BuildConfig;
import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.fragments.SettingsActivity;
import com.adityathakker.egyaan.models.NotesModel;
import com.adityathakker.egyaan.utils.AppConst;
import com.adityathakker.egyaan.utils.CommonTasks;
import com.adityathakker.egyaan.utils.FileDownloadTask;

import java.io.File;
import java.util.List;

/**
 * Created by fireion on 17/11/17.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private static final String TAG = NotesAdapter.class.getSimpleName();
    Context context;
    private List<NotesModel> notesModelsList;

    public NotesAdapter(Context notesActivity, List<NotesModel> notesModels) {
        this.context = notesActivity;
        this.notesModelsList = notesModels;
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_full_notes_view, parent, false);
        return new NotesViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final NotesViewHolder holder, int position) {
        final NotesModel notesModel = notesModelsList.get(position);

        holder.notesTitle.setText(notesModel.getTitle());
        holder.notesFileName.setText(notesModel.getFile().substring(21, notesModel.getFile().indexOf("." + notesModel.getType())));
        holder.notesFileExt.setText(notesModel.getType().toUpperCase());
        holder.notesSize.setText(notesModel.getSize());
        holder.teacherName.setText(notesModel.getName());
        holder.notesPage.setText(notesModel.getPages() + " Pages");

        if (notesModel.getType().equals("pdf")) {
            holder.imageViewFile.setImageResource(R.drawable.file_pdf);
        } else if (notesModel.getType().equals("ppt")) {
            holder.imageViewFile.setImageResource(R.drawable.file_powerpoint);
        } else if (notesModel.getType().equals("doc")) {
            holder.imageViewFile.setImageResource(R.drawable.file_document);
        } else {
            Log.e(TAG, "onBindViewHolder: Error while setting file icon");
        }

        if (checkFile(holder.notesFileName.getText(), notesModel.getType())) {
            holder.downloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CommonTasks.isDataOn(context).equals(AppConst.Extras.WIFI) || CommonTasks.isDataOn(context).equals(AppConst.Extras.MOBILE)) {
                        String switchPreferenceCheck = SettingsActivity.MainPreferenceFragment.getDefaults("switchPreference", context);
                        if (switchPreferenceCheck.equals("true") && !CommonTasks.isDataOn(context).equals(AppConst.Extras.WIFI) && CommonTasks.isDataOn(context).equals(AppConst.Extras.MOBILE)) {
                            Toast.makeText(context, "You are not connected to Wi-Fi. Change from Settings menu.", Toast.LENGTH_LONG).show();
                        } else {
                            new FileDownloadTask(context, holder.downloadButton,
                                    AppConst.URLs.NOTES_FILE_URL + "" + notesModel.getFile(),
                                    holder.notesFileName.getText() + "." + notesModel.getType());
                            openDownloadedFolder(holder.notesFileName.getText(), notesModel.getType());
                        }
                    } else {
//                        Toast.makeText(context, AppConst.Messages.NO_INTERNET, Toast.LENGTH_SHORT).show();
                        CommonTasks.showMessage(view, AppConst.Messages.NO_INTERNET);
                    }
                }
            });
        } else {
            holder.downloadButton.setVisibility(View.GONE);
            holder.linearLayoutNotesMaterial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDownloadedFolder(holder.notesFileName.getText(), notesModel.getType());
                }
            });
        }
    }

    private void openDownloadedFolder(CharSequence fileName, CharSequence fileExt) {
        //First check if SD Card is present or not
//        Log.d(TAG, "openDownloadedFolder: File Name and EXt - " + fileName + " - " + fileExt);
        if (new CommonTasks.CheckForSDCard().isSDCardPresent()) {

            //Get Download Directory File
            File fileStorage = new File(
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                            + AppConst.Extras.DOWNLOAD_DIRECTORY + "/" + AppConst.Extras.DOWNLOAD_NOTES_FILES_DIRECTORY);

            //If file is not present then display Toast
            if (!fileStorage.exists()) {
                Toast.makeText(this.context, AppConst.Messages.NO_DIRECTORY, Toast.LENGTH_SHORT).show();
            } else {
                //If directory is present Open Folder

                /** Note: Directory will open only if there is a app to open directory like File Manager, etc.  **/

                PackageManager packageManager = context.getPackageManager();
                List list = null;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("application/" + fileExt + "");
                list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

                File file = new File(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                                + AppConst.Extras.DOWNLOAD_DIRECTORY
                                + "/" + AppConst.Extras.DOWNLOAD_NOTES_FILES_DIRECTORY
                                + "/" + fileName + "." + fileExt);
                Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
                if ((list != null ? list.size() : 0) > 0) {
                    intent.setDataAndType(uri, "application/" + fileExt + "");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    context.startActivity(Intent.createChooser(intent, "Open"));
                } else {
                    Log.e(TAG, "openDownloadedFolder: Package Manager intent error");
                }
            }

        } else
            Toast.makeText(this.context, AppConst.Messages.ERROR_NO_SD_CARD, Toast.LENGTH_SHORT).show();
    }

    private boolean checkFile(CharSequence downloadFileName, String fileExt) {
        File fileStorage = null;
        File outputFile;

        if (new CommonTasks.CheckForSDCard().isSDCardPresent()) {
            fileStorage = new File(
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                            + AppConst.Extras.DOWNLOAD_DIRECTORY + "/" + AppConst.Extras.DOWNLOAD_NOTES_FILES_DIRECTORY);
        } else
            Toast.makeText(context, AppConst.Messages.ERROR_NO_SD_CARD, Toast.LENGTH_SHORT).show();


        outputFile = new File(fileStorage, String.valueOf(downloadFileName) + "." + fileExt);//Create Output file in Main File

        //Create New File if not present
        if (!outputFile.exists()) {
            return true;
        } else {
//                    Toast.makeText(context, "File " + AppConst.Messages.FILE_EXISTS, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "doInBackground: File Already Exists");
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return notesModelsList.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView notesTitle, notesFileName, notesFileExt, teacherName, notesSize, notesPage;
        Button downloadButton;
        LinearLayout linearLayoutNotesMaterial;
        ImageView imageViewFile;

        private NotesViewHolder(View itemView) {
            super(itemView);

            notesTitle = itemView.findViewById(R.id.notes_title);
            notesFileName = itemView.findViewById(R.id.file_name);
            notesFileExt = itemView.findViewById(R.id.file_type);
            teacherName = itemView.findViewById(R.id.author_name);
            notesSize = itemView.findViewById(R.id.file_size);
            notesPage = itemView.findViewById(R.id.file_pages);

            downloadButton = itemView.findViewById(R.id.file_download);
            linearLayoutNotesMaterial = itemView.findViewById(R.id.linearLayoutNotesMaterial);
            imageViewFile = itemView.findViewById(R.id.imageViewFile);
        }
    }
}
