
package com.example.zz.zz.model.getAllReview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewsParameter {

    @SerializedName("parameter")
    @Expose
    private Parameter parameter;
    @SerializedName("value")
    @Expose
    private float value;
    @SerializedName("idReviewParameters")
    @Expose
    private Integer idReviewParameters;

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public float getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getIdReviewParameters() {
        return idReviewParameters;
    }

    public void setIdReviewParameters(Integer idReviewParameters) {
        this.idReviewParameters = idReviewParameters;
    }

}
