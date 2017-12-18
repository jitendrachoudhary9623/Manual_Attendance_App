package com.example.dheeraj.teacher;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.qoppa.android.pdf.PDFException;
import com.qoppa.android.pdfProcess.PDFCanvas;
import com.qoppa.android.pdfProcess.PDFDocument;
import com.qoppa.android.pdfProcess.PDFFontStandard;
import com.qoppa.android.pdfProcess.PDFPage;
import com.qoppa.android.pdfProcess.PDFPaint;
import com.qoppa.android.pdfViewer.fonts.StandardFontTF;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.itextpdf.text.pdf.PdfName.DEST;
import static com.itextpdf.text.pdf.PdfName.PDF;

public class PDFAcitivity extends AppCompatActivity {
    private File pdfFile;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    //
    ArrayList<String> names;
    ArrayList<String> registers;
    ArrayList<Integer> Roll;
    List<String> Sem;
    ArrayList<String> defaultlist;
    ArrayList<String> defaultlist_name;
    Activity defaulter_activity = this;
    //Spinner spinner;
    float at;

    Cursor[] cur = new Cursor[100000];
    Cursor[] cur1 = new Cursor[100000];
    Cursor[] cu = new Cursor[100000];

EditText pdfName;
    //
    public void generate(View view) {
        Toasty.error(this, "Wait Till The Data Is exported to PDF", Toast.LENGTH_SHORT).show();
        try {
            createPdfWrapper();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void createPdfWrapper() throws FileNotFoundException, DocumentException {

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        } else {
            createPdf();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        createPdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toasty.error(this, "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i("PDF", "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(), pdfName.getText().toString()+".pdf");

        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();


        names = new ArrayList<>();
        Roll = new ArrayList<>();
        registers = new ArrayList<>();

        defaultlist = new ArrayList<>();
        defaultlist_name = new ArrayList<>();
        Sem = new ArrayList<String>();
        Sem.addAll(AppBase.divisions);
        //
        PdfWriter.getInstance(document, output);
        document.open();
        String index;
        index = null;
        for (int b = 0; b < Sem.size(); b++) {
            index = getData(b);

            if (index != null) {
                document.add(new Paragraph("Semester " + Sem.get(b) + "\n\n\n" + index));
            } else {
                //  Toasty.warning(this, "No Defaulter in "+ Sem.get(b) , Toast.LENGTH_LONG).show();
            }
            //document.add(new Paragraph(getData(1)));


        }
        Toasty.success(this,"Data Saved Successfully",Toast.LENGTH_LONG).show();
        document.close();
         previewPdf();
    }

    public String getData(int index) {
        String qu = "SELECT * FROM STUDENT WHERE cl = '" + Sem.get(index).toString() + "' " +
                "ORDER BY ROLL";

        defaultlist.clear();
        defaultlist_name.clear();

        names.clear();
        Roll.clear();
        registers.clear();
        Cursor cursor = AppBase.handler.execQuery(qu);

        if (cursor == null || cursor.getCount() == 0) {

        } else {
            int ctr = 0;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                registers.add(cursor.getString(2));
                names.add(cursor.getString(0));
                Roll.add(cursor.getInt(4));
                cursor.moveToNext();
                ctr++;
            }
        }
        String op = "";
        if (names.isEmpty())
            return null;
/////////////////////////////////////////////////////////////////////////

        String actualOp = "";
        ArrayList<Float> attendance = new ArrayList<>();
        for (int i = 0; i < registers.size(); i++) {
            String qc = "SELECT * FROM ATTENDANCE WHERE register = '" + registers.get(i).toString().toUpperCase() + "';";
            String qd = "SELECT * FROM ATTENDANCE WHERE register = '" + registers.get(i).toString().toUpperCase() + "' AND isPresent = 1";

            at = 0f;


            cur[i] = AppBase.handler.execQuery(qc);
            cur1[i] = AppBase.handler.execQuery(qd);
            if (cur[i] == null) {
                Log.d("profile", "cur null");
            }
            if (cur1[i] == null) {
                Log.d("profile", "cur1 null");
            }
            if (cur[i] != null && cur1[i] != null) {
                cur[i].moveToFirst();
                cur1[i].moveToFirst();
                try {
                    at = ((float) cur1[i].getCount() / cur[i].getCount()) * 100;

                    cur1[i].close();
                    cur[i].close();
                    if (at < 75.00) {
                        defaultlist.add(registers.get(i));
                        attendance.add(at);
                    } else break;

                } catch (Exception e) {
                    at = -1;
                }
            }

            //defaulter_activity.this.finish();

        }


        for (int j = 0; j < defaultlist.size(); j++) {
            String qu1 = "SELECT * FROM STUDENT WHERE regno = '" + defaultlist.get(j).toString().toUpperCase() + "' ";
            cu[j] = AppBase.handler.execQuery(qu1);
            cu[j].moveToFirst();

            defaultlist_name.add(cu[j].getString(0) + "\nRoll No "+ cu[j].getInt(4) );

        }
        if (!defaultlist.isEmpty()) {
            for (int m = 0; m < defaultlist_name.size(); m++) {
                actualOp += "Name " + defaultlist_name.get(m) + "\nAttendance " + attendance.get(m) + "\n\n";

            }
          //  Toast.makeText(this, actualOp, Toast.LENGTH_SHORT).show();
        }
       actualOp+="\n\n\n";
        return actualOp;
    }

    private void previewPdf() {

        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");

            startActivity(intent);
        } else {
            Toast.makeText(this, "Download a PDF Viewer to see the generated PDF", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfacitivity);
        pdfName=(EditText)findViewById(R.id.pdfName);
    }


}




