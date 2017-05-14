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

        if (faces.size() == 0){
            Toast.makeText(context, R.string.no_faces_detected_message,Toast.LENGTH_SHORT).show();
        }else {
            for (int i=0;i<faces.size();i++){
                getClassifications(faces.valueAt(i));
            }
        }

        faceDetector.release();
    }
    private static void getClassifications(Face face){
        // Log probabilities
        float getIsSmilingProbability = face.getIsSmilingProbability();
        float getIsLeftEyeOpenProbability = face.getIsLeftEyeOpenProbability();
        float getIsRightEyeOpenProbability = face.getIsRightEyeOpenProbability();
        Log.d(LOG_TAG,"getClassifications: smilingProb: "+ getIsSmilingProbability);
        Log.d(LOG_TAG,"getClassifications: leftEyeOpenProb: "+ getIsLeftEyeOpenProbability);
        Log.d(LOG_TAG,"getClassifications: rightEyeOpenProb: "+ getIsRightEyeOpenProbability);
    }
}