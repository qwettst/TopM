
package com.example.zz.zz.model.getAllReview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parameter {

    @SerializedName("idParameters")
    @Expose
    private Integer idParameters;
    @SerializedName("nameParameter")
    @Expose
    private String nameParameter;

    public Integer getIdParameter() {
        return idParameters;
    }

    public void setIdParameter(Integer idParameters) {
        this.idParameters = idParameters;
    }

    public String getNameParameter() {
        return nameParameter;
    }

    public void setNameParameter(String nameParameter) {
        this.nameParameter = nameParameter;
    }

}
