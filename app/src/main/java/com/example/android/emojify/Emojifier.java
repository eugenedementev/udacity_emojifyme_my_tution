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

    private static final double SMILING_PROB_THRESHOLD = .15;
    private static final double EYE_OPEN_PROB_THRESHOLD = .5;

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

        // If there are no faces detected, show a Toast message
        if(faces.size() == 0){
            Toast.makeText(context, R.string.no_faces_detected_message, Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < faces.size(); ++i) {
                Face face = faces.valueAt(i);

                // Log the classification probabilities for each face.
                Log.d(LOG_TAG,"Face " + i + ":");
                whichEmoji(face);
            }
        }
        // Release the detector
        faceDetector.release();
    }


    /**
     * Method for logging the classification probabilities.
     *
     * @param face The face to get the classification probabilities.
     */
    private static void whichEmoji(Face face){
        // Log all the probabilities
        Log.d(LOG_TAG, "whichEmoji: smilingProb = " + face.getIsSmilingProbability());
        Log.d(LOG_TAG, "whichEmoji: leftEyeOpenProb = "
                + face.getIsLeftEyeOpenProbability());
        Log.d(LOG_TAG, "whichEmoji: rightEyeOpenProb = "
                + face.getIsRightEyeOpenProbability());

        boolean isSmiling = face.getIsSmilingProbability() > SMILING_PROB_THRESHOLD;
        boolean isLeftEyeOpen = face.getIsLeftEyeOpenProbability() > EYE_OPEN_PROB_THRESHOLD;
        boolean isRightEyeOpen = face.getIsRightEyeOpenProbability() > EYE_OPEN_PROB_THRESHOLD;

        Emoji emoji;

        if (isSmiling){
            if (!isLeftEyeOpen && isRightEyeOpen){
                emoji = Emoji.LEFT_WINK;
            }else if (!isRightEyeOpen && isLeftEyeOpen){
                emoji = Emoji.RIGHT_WINK;
            }else if (!isLeftEyeOpen){
                emoji = Emoji.CLOSED_EYE_SMILE;
            }else {
                emoji = Emoji.SMILE;
            }
        }else {
            if (!isLeftEyeOpen && isRightEyeOpen){
                emoji = Emoji.LEFT_WINK_FROWN;
            }else if (!isRightEyeOpen && isLeftEyeOpen){
                emoji = Emoji.RIGHT_WINK_FROWN;
            }else if (!isLeftEyeOpen){
                emoji = Emoji.CLOSED_EYE_FROWN;
            }else {
                emoji = Emoji.FROWN;
            }
        }
        Log.d(LOG_TAG,"whichEmoji: emoji"+emoji.name());
    }

    private enum Emoji{
        SMILE,
        FROWN,
        LEFT_WINK,
        RIGHT_WINK,
        LEFT_WINK_FROWN,
        RIGHT_WINK_FROWN,
        CLOSED_EYE_SMILE,
        CLOSED_EYE_FROWN
    }
}
