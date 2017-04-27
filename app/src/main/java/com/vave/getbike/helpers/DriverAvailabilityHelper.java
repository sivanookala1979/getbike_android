package com.vave.getbike.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.vave.getbike.R;
import com.vave.getbike.model.PromotionsBanner;
import com.vave.getbike.syncher.BaseSyncher;
import com.vave.getbike.syncher.LoginSyncher;
import com.vave.getbike.syncher.RideSyncher;

/**
 * Created by ram koti on 30/3/17.
 */

public class DriverAvailabilityHelper {

    static PromotionsBanner promotionsBanner;

    public static void setDriverAvailabilityForTrue(Context context) {
        new GetBikeAsyncTask(context) {

            @Override
            public void process() {
                LoginSyncher loginSyncher = new LoginSyncher();
                loginSyncher.setDriverAvailabilityTrue();
            }

            @Override
            public void afterPostExecute() {

            }
        }.execute();
    }

    public static void setDriverAvailabilityForFalse(Context context) {
        new GetBikeAsyncTask(context) {

            @Override
            public void process() {
                LoginSyncher loginSyncher = new LoginSyncher();
                loginSyncher.setDriverAvailabilityFalse();
            }

            @Override
            public void afterPostExecute() {

            }
        }.execute();
    }

    public static void storeTutorialCompletedStatus(Context context) {
        new GetBikeAsyncTask(context) {

            @Override
            public void process() {
                LoginSyncher loginSyncher = new LoginSyncher();
                loginSyncher.storeTutorialCompletedStatus();
            }

            @Override
            public void afterPostExecute() {

            }
        }.execute();
    }

    public static void showPromotionsBannerForSize(final Context context,final float resolutionDensity) {
        Log.d("Promotions Screen", "device screen size is " + resolutionDensity);
        if (resolutionDensity == 0.75) {
            DriverAvailabilityHelper.showPromotionsBanner(context,"ldpi",240,175);
        } else if (resolutionDensity == 1.0) {
            DriverAvailabilityHelper.showPromotionsBanner(context,"mdpi",320,234);
        } else if (resolutionDensity == 2.0) {
            DriverAvailabilityHelper.showPromotionsBanner(context,"xhdpi",640,467);
        } else if (resolutionDensity == 3.0) {
            DriverAvailabilityHelper.showPromotionsBanner(context,"xxhdpi",960,351);
        } else {
            DriverAvailabilityHelper.showPromotionsBanner(context,"hdpi",480,351);
        }
    }


    public static void showPromotionsBanner(final Context context, final String resolution, final int size1, final int size2) {
        //Code for promotional banner;
        final AlertDialog.Builder promotionsBuilder = new AlertDialog.Builder(context);
        final ImageView image1 = new ImageView(context);
        LinearLayout imageLayout = new LinearLayout(context);
        imageLayout.setOrientation(LinearLayout.VERTICAL);
        image1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageLayout.addView(image1);
        promotionsBuilder.setView(imageLayout);
        promotionsBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                promotionsBuilder.create().dismiss();
            }
        });
        new GetBikeAsyncTask(context) {

            @Override
            public void process() {
                promotionsBanner = new RideSyncher().getPromotionalBannerWithUrl(resolution);
            }

            @Override
            public void afterPostExecute() {
                if (promotionsBanner != null) {
                    System.out.println("Testing phase promotional banner object is:"+promotionsBanner);
                    if (promotionsBanner.getImageName() != null) {
                        Picasso.with(context).load(BaseSyncher.BASE_URL + "/uploads/" + promotionsBanner.getImageName()).placeholder(R.drawable.picture).resize(size1, size2).into(image1);
                        promotionsBuilder.show();
                    }
                }
            }
        }.execute();
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+promotionsBanner.getImageUrl()));
                context.startActivity(i);
            }
        });
    }

}
