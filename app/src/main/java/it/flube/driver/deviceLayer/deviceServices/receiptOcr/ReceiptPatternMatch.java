/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.receiptOcr;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

/**
 * Created on 3/2/2019
 * Project : Driver
 */
public class ReceiptPatternMatch {
    public static final String TAG = "ReceiptPatternMatch";

    private final String stringToSearch;

    public ReceiptPatternMatch(String stringToSearch){
        this.stringToSearch = stringToSearch;
        Timber.tag(TAG).d("    stringToSearch -> %s", stringToSearch);
    }

    public ArrayList<String> matchPatternRequestIgnoreSentinals(String patternMatch){
        Timber.tag(TAG).d("matchPatternRequest");
        Timber.tag(TAG).d("    patternMatch   -> %s", patternMatch);

        Matcher matcher = Pattern.compile(patternMatch).matcher(stringToSearch);
        ArrayList<String> matchList = new ArrayList<String>();

        while (matcher.find()){
            Timber.tag(TAG).d("***** match found ****");
            Timber.tag(TAG).d("   startIndex -> %s", matcher.start());
            Timber.tag(TAG).d("   endIndex   -> %s", matcher.end());
            Timber.tag(TAG).d("   match      -> %s", matcher.group());

            matchList.add(matcher.group());
        }

        return matchList;
    }

    //// returns matching String if found in search string, returns null if not found
    public String matchPatternRequest(String startSentinal, String endSentinal, String patternMatch){
        Timber.tag(TAG).d("matchPatternRequest");
        Timber.tag(TAG).d("    startSentinal  -> %s", startSentinal);
        Timber.tag(TAG).d("    endSentinal    -> %s", endSentinal);
        Timber.tag(TAG).d("    patternMatch   -> %s", patternMatch);

        int startIndex;
        int endIndex;
        String candidateString;
        boolean matchFound = false;

        if (stringToSearch.contains(startSentinal)){
            startIndex = stringToSearch.indexOf(startSentinal);
            Timber.tag(TAG).d("startSentinal FOUND at position %s",startIndex);

            if (stringToSearch.contains(endSentinal)){
                endIndex = stringToSearch.indexOf(endSentinal);
                Timber.tag(TAG).d("endSentinal FOUND at position %s",endIndex);

                if ((endIndex - startIndex) > 0) {
                    candidateString = stringToSearch.substring(startIndex, endIndex).trim();
                    Timber.tag(TAG).d("candidateString -> %s", candidateString);

                    // now see if there is a pattern match in our candidate string
                    Matcher matcher = Pattern.compile(patternMatch).matcher(candidateString);


                    while (matcher.find()){
                        Timber.tag(TAG).d("***** match found ****");
                        Timber.tag(TAG).d("   startIndex -> %s", matcher.start());
                        Timber.tag(TAG).d("   endIndex   -> %s", matcher.end());
                        Timber.tag(TAG).d("   match      -> %s", matcher.group());
                        matchFound = true;
                    }

                    ///we'll always use the first match
                    if (matchFound){
                        Timber.tag(TAG).d("first match -> %s", matcher.group(1));
                        return matcher.group(1);
                    } else {
                        Timber.tag(TAG).d("no match found");
                        return null;
                    }


                } else {
                    Timber.tag(TAG).d(" indexes don't make sense");
                    return null;
                }
            } else {
                Timber.tag(TAG).d("endSentinal NOT FOUND");
                return null;
            }
        } else {
            Timber.tag(TAG).d("startSentinal NOT FOUND");
            return null;
        }
    }


}
