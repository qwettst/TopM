package com.example.zz.zz;

import android.view.View;

import com.example.zz.zz.model.AllReviewData;
import com.example.zz.zz.model.ReviewData;
import com.example.zz.zz.model.SearchReview;


/**
 * Created by Pavel on 09.04.2018.
 */

public interface DataSendFragment extends View.OnClickListener {
    void sendData(AllReviewData data);
    void sendReviewData(ReviewData data);
    void sendSearchData(SearchReview data);
}
