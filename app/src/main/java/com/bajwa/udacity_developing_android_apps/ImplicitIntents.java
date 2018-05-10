package com.bajwa.udacity_developing_android_apps;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bajwa.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImplicitIntents extends AppCompatActivity {

    // For getting request of Image capture button
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.udacity_dap_activity_implicit_intents);
    }

    // Link to common intents
    // http://developer.android.com/guide/components/intents-common.html
/*
Implicit intents have two parts, action and data
action: what you want to do
data: data to be passed to action
 */
    // Actions on Button click
    public void onClickOpenWebpageButton(View v) {
        String urlAsString = "http://www.udacity.com";
        openWebPage(urlAsString);
    }


    public void onClickOpenAddressButton(View v) {
        String addressString = "1600 Amphitheatre Parkway, CA";

        // All strings passed to the Google Maps Intents must be URI encoded. For example, the string "1st & Pike, Seattle" should become 1st%20%26%20Pike%2C%20Seattle
        // Spaces in the string can be encoded with %20 or replaced with the plus sign (+)

        /*
        Use the geo: intent to display a map at a specified location and zoom level.    geo:latitude,longitude?z=zoom
         */

        // We can use Uri.parse(String) methed here, but it is more error prone
        // thats why we will use below method to create our Uri
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("geo")
                .path("0,0")
                .query(addressString);  // If there is query parameter for street or business then lat and lan must be 0, 0
        Uri addressUri = builder.build();
        Toast.makeText(ImplicitIntents.this, "Searching location\n" + addressUri.toString(), Toast.LENGTH_LONG).show();
        showMap(addressUri);
    }


    public void onClickShareTextButton(View v) {
        /* Create the String that you want to share */
        String textThatYouWantToShare =
                "Sharing the coolest thing I've learned so far. You should " +
                        "check out Udacity and Google's Android Nanodegree!";

        /* Send that text to our method that will share it. */
        shareText(textThatYouWantToShare);
    }

    /**
     * This is where you will create and fire off your own implicit Intent. Yours will be very
     * similar to what I've done above. You can view a list of implicit Intents on the Common
     * Intents page from the developer documentation.
     *
     * @param v Button that was clicked.
     * @see <http://developer.android.com/guide/components/intents-common.html/>
     */
    public void createYourOwn(View v) {
        // We are going to implement capture image intent
        /*
        To open a camera app and receive the resulting photo or video, use the ACTION_IMAGE_CAPTURE or ACTION_VIDEO_CAPTURE action.
        Also specify the URI location where you'd like the camera to save the photo or video, in the EXTRA_OUTPUT extra.
         */
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // The name of the Intent-extra used to indicate a content resolver Uri to be used to
        // store the requested image or video.
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(createImageFile().getAbsolutePath()));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }

    }

    // Overriding method to display image thumbnail as toast when we take image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            /*
            Note: When you use ACTION_IMAGE_CAPTURE to capture a photo, the camera may also return a downscaled copy
            (a thumbnail) of the photo in the result Intent, saved as a Bitmap in an extra field named "data".
             */
            Bitmap thumbnail = data.getParcelableExtra("data");
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(thumbnail);

            // Create toast and display image in it
            Toast toast = new Toast(this);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(imageView);
            toast.show();
        }
    }

    /**
     * This method fires off an implicit Intent to open a webpage.
     *
     * @param url Url of webpage to open. Should start with http:// or https:// as that is the
     *            scheme of the URI expected with this Intent according to the Common Intents page
     */
    private void openWebPage(String url) {
        /*
         * We wanted to demonstrate the Uri.parse method because its usage occurs frequently. You
         * could have just as easily passed in a Uri as the parameter of this method.
         * Uri is like a address to data, that we are planning to pass thorough to the intended action
         */
        Uri webpage = Uri.parse(url);

        /*
         * Here, we create the Intent with the action of ACTION_VIEW. This action allows the user
         * to view particular content. In this case, our webpage URL.
         */
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        /*
         * This is a check we perform with every implicit Intent that we launch. In some cases,
         * the device where this code is running might not have an Activity to perform the action
         * with the data we've specified. Without this check, in those cases your app would crash.
         */
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method will fire off an implicit Intent to view a location on a map.
     * <p>
     * When constructing implicit Intents, you can use either the setData method or specify the
     * URI as the second parameter of the Intent's constructor,
     * as I do in {@link #openWebPage(String)}
     *
     * @param geoLocation The Uri representing the location that will be opened in the map
     */
    private void showMap(Uri geoLocation) {
        /*
         * Again, we create an Intent with the action, ACTION_VIEW because we want to VIEW the
         * contents of this Uri.
         */
        Intent intent = new Intent(Intent.ACTION_VIEW);

        /*
         * Using setData to set the Uri of this Intent has the exact same affect as passing it in
         * the Intent's constructor. This is simply an alternate way of doing this.
         */

        /*
        A Uri (Uniform Resource Identifier) is a string of character that identifies a resource
        A URL is a URI that identifies a web or network resource but it can also describe geo location in case of google maps

        full form of a URI is
        scheme:[//[user:password@]host[:port]][/]path[?query][#fragment]
        The scheme describes what type of resource we're pointing to.
        Popular schemes on the web are HTTP and HTTPS, mailto, FTP, file, and geo, but there are many more.
        Depending on the particular scheme, it might be followed by two slashes and an authority part.

        The authority indicates an optional username and password to log in, a host name, which could be a domain name or
        an IP address, and an optional port.

        Despite its name, the query isn't always about searching.
        Officially, it has no size limit, but in practice, it can range from about 8,000 characters to over 190,000 characters,
        depending on the browser being used.

        The last element of the URI is the fragment. This is preceded by a hash and indicates secondary data that the path resource will use.
         */
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method shares text and allows the user to select which app they would like to use to
     * share the text. Using ShareCompat's IntentBuilder, we get some really cool functionality for
     * free. The chooser that is started using the {@link ShareCompat.IntentBuilder#startChooser()} method will
     * create a chooser when more than one app on the device can handle the Intent. This happens
     * when the user has, for example, both a texting app and an email app. If only one Activity
     * on the phone can handle the Intent, it will automatically be launched.
     *
     * @param textToShare Text that will be shared
     */
    private void shareText(String textToShare) {
        /*
         * You can think of MIME types similarly to file extensions. They aren't the exact same,
         * but MIME types help a computer determine which applications can open which content. For
         * example, if you double click on a .pdf file, you will be presented with a list of
         * programs that can open PDFs. Specifying the MIME type as text/plain has a similar affect
         * on our implicit Intent. With text/plain specified, all apps that can handle text content
         * in some way will be offered when we call startActivity on this particular Intent.
         */
        String mimeType = "text/plain";

        /* This is just the title of the window that will pop up when we call startActivity */
        String title = "Learning How to Share";

        /* ShareCompat.IntentBuilder provides a fluent API for creating Intents */

        ShareCompat.IntentBuilder
                /* The from method specifies the Context from which this share is coming from */
                .from(this)
                .setType(mimeType)
                .setChooserTitle(title)
                .setText(textToShare)
                .startChooser();
    }


    // Method to create file on Specified location
    private File createImageFile() {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            /*
            Creates a new empty file in the specified directory, using the
            given prefix and suffix strings to generate its name.  If this method
            returns successfully then it is guaranteed that
             */
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    //////////----------NOTES--------///////////////////////
    // We can also use
    // Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS); to create File with Directory Download
    // To access individual files in this directory use either File.list() or File.listFiles()
    // To read and write to android storage
    // https://stackoverflow.com/questions/17674634/saving-and-reading-bitmaps-images-from-internal-memory-in-android
    //
    // For more about files and storage https://developer.android.com/training/data-storage/files
    // More about taking picture    https://developer.android.com/training/camera/photobasics
    //
    ////////////////////////////////////////////////////////

}
