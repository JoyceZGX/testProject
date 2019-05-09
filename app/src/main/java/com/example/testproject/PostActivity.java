package com.example.testproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

/**
 * PostActivity that allows users to post rating, comment, and photos.
 * and save these data into FireBase
 *
 * @author Genxing Zhan
 * @version 1.0
 */

public class PostActivity extends AppCompatActivity {

    private ImageView addPhoto;
    RatingBar mRatingBar; //rating
    EditText addComment; //added comment
    TextView post; // button pressed to post
    TextView dish; // dish displayed
    private Uri imageUri = null;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        addPhoto = (ImageView) findViewById(R.id.addImage);
        mRatingBar = findViewById(R.id.ratingBar);
        dish = findViewById(R.id.dish_name);
        post = findViewById(R.id.post);
        addComment = findViewById(R.id.add_comment);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Post");

        mProgress = new ProgressDialog(this);

        Intent n1 = getIntent();
        String newDish = n1.getStringExtra("DishName");
        dish.setText(newDish);

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent (Intent.ACTION_GET_CONTENT);
                albumIntent.setType("image/*");
                startActivityForResult(albumIntent,GALLERY_REQUEST);
            }
        });

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).rotate(90).into(addPhoto);
        }
    }

    /**
     * upload() method when got clicked, save all existing data into firebase
     */
    private void upload(){

        mProgress.setMessage("Posting...");
        mProgress.show();

        final String comment_val= addComment.getText().toString().trim();

        if (!TextUtils.isEmpty(comment_val)&& imageUri!=null) {

            final StorageReference filepath = mStorage.child("Post_Image").child(imageUri.getLastPathSegment());

            filepath.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
            {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>()
            {
                @Override
                public void onComplete(@NonNull Task<Uri> task)
                {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();

                        DatabaseReference newPost = mDatabase.push();
                        newPost.child("comment").setValue(comment_val);
                        int numStars = (int) mRatingBar.getRating();
                        newPost.child("rating").setValue(numStars);
                        newPost.child("image").setValue(downloadUri.toString());
                        newPost.child("dishName").setValue(dish.getText());

                        mProgress.dismiss();
                        finish();
                    }
                }
            });
        }

        else if (!TextUtils.isEmpty(comment_val)&& imageUri==null){

            DatabaseReference newPost = mDatabase.push();
            newPost.child("comment").setValue(comment_val);
            newPost.child("rating").setValue(mRatingBar.getRating());
            newPost.child("dishName").setValue(dish.getText());

            finish();
        }
        else if (TextUtils.isEmpty(comment_val)&& imageUri!=null){
            final StorageReference filepath = mStorage.child("Post_Image").child(imageUri.getLastPathSegment());

            filepath.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
            {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>()
            {
                @Override
                public void onComplete(@NonNull Task<Uri> task)
                {
                    if (task.isSuccessful())
                    {
                        Uri downloadUri = task.getResult();

                        DatabaseReference newPost = mDatabase.push();
                        newPost.child("rating").setValue(mRatingBar.getRating());
                        newPost.child("image").setValue(downloadUri.toString());
                        newPost.child("dishName").setValue(dish.getText());

                        mProgress.dismiss();
                        finish();
                        startActivity(new Intent(PostActivity.this,DishActivity.class));

                    }
                }
            });
        }


    }
}
