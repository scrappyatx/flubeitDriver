package it.flube.driver.userInterfaceLayer.layoutComponents.demoOffers;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import it.flube.driver.R;
import timber.log.Timber;

/**
 * Created on 1/17/2018
 * Project : Driver
 */

public class DemoOffersLayoutComponents {
    public final static String TAG = "DemoOffersLayoutComponents";
    ///
    ///     wrapper class for the layout file:
    ///     activity_demo_offers.xml
    ///

    private TextView instructions;
    private Button offerMakeButton;
    private LottieAnimationView makeOfferWaitingAnimation;

    public DemoOffersLayoutComponents(AppCompatActivity activity){
        instructions = (TextView) activity.findViewById(R.id.demo_offers_instructions);
        makeOfferWaitingAnimation = (LottieAnimationView) activity.findViewById(R.id.generate_offer_animation);
        offerMakeButton = (Button) activity.findViewById(R.id.demo_offers_generate_button);
        setInvisible();
        Timber.tag(TAG).d("...components created");
    }

    public void setReadyToMake(){
        instructions.setVisibility(View.VISIBLE);
        offerMakeButton.setVisibility(View.VISIBLE);
        makeOfferWaitingAnimation.setVisibility(View.GONE);
        Timber.tag(TAG).d("...setReadyToClaim");
    }

    public void setTooManyOffers(){
        instructions.setVisibility(View.VISIBLE);
        offerMakeButton.setVisibility(View.INVISIBLE);
        makeOfferWaitingAnimation.setVisibility(View.GONE);
        Timber.tag(TAG).d("...setReadyToClaim");
    }

    public void offerMakeStarted(){
        offerMakeButton.setVisibility(View.INVISIBLE);

        makeOfferWaitingAnimation.setVisibility(View.VISIBLE);
        makeOfferWaitingAnimation.setProgress(0);
        makeOfferWaitingAnimation.playAnimation();
        Timber.tag(TAG).d("...offerMakeStarted");
    }

    public void setVisible(){
        instructions.setVisibility(View.VISIBLE);
        offerMakeButton.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("...set VISIBLE");
    }
    public void setInvisible(){
        instructions.setVisibility(View.INVISIBLE);
        makeOfferWaitingAnimation.setVisibility(View.INVISIBLE);
        offerMakeButton.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        instructions.setVisibility(View.GONE);
        offerMakeButton.setVisibility(View.GONE);
        makeOfferWaitingAnimation.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        instructions =null;
        offerMakeButton = null;
        makeOfferWaitingAnimation = null;
        Timber.tag(TAG).d("components closed");
    }

}
