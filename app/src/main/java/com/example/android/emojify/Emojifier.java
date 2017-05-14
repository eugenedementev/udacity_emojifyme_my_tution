package com.example.android.emojify;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

/**
 * Created by Eugene on 14.05.2017.
 */

public class Emojifier {

    private static final String LOG_TAG = Emojifier.class.getSimpleName();

    // Create the face detector, disable tracking and enable classifications
    public static void detectFaces(Context context, Bitmap bitmap){
        FaceDetector faceDetector = new FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();
        // Build the frame
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();

        //Face detection
        SparseArray<Face> faces = faceDetector.detect(frame);

        Log.d(LOG_TAG,"There are :" + faces.size() + " faces");
        Toast.makeText(context, LOG_TAG,Toast.LENGTH_SHORT).show();

        if (faces.size() == 0){
            Toast.makeText(context, R.string.no_faces_detected_message,Toast.LENGTH_SHORT).show();
        }

        faceDetector.release();
        faces.clear();
    }
}
