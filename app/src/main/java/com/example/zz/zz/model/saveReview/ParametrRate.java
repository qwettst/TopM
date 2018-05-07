package com.example.zz.zz.model.saveReview;

import com.example.zz.zz.model.getAllReview.GetReview;

/**
 * Created by Pavel on 07.05.2018.
 */

public class ParametrRate {

    private String parameterName;
    private float value;
    private Long idReviewsParameters;


    public ParametrRate() {
    }

    public ParametrRate(String parameterName, Integer value) {
        this.parameterName = parameterName;
        this.value = value;
    }

    public Long getIdReviewsParameters() {
        return idReviewsParameters;
    }

    public void setIdReviewsParameters(Long idReviewsParameters) {
        this.idReviewsParameters = idReviewsParameters;
    }


    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
