package com.example.zz.zz;

import com.example.zz.zz.model.AllReviewData;
import com.example.zz.zz.model.ReviewData;


/**
 * Created by Pavel on 09.04.2018.
 */

public interface DataSendFragment {
    void sendData(AllReviewData data);
    void sendReviewData(ReviewData data);
}
