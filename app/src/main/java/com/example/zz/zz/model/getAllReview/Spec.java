
package com.example.zz.zz.model.getAllReview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Spec {

    @SerializedName("idSpec")
    @Expose
    private Integer idSpec;
    @SerializedName("nameSpec")
    @Expose
    private String nameSpec;

    public Integer getIdSpec() {
        return idSpec;
    }

    public void setIdSpec(Integer idSpec) {
        this.idSpec = idSpec;
    }

    public String getNameSpec() {
        return nameSpec;
    }

    public void setNameSpec(String nameSpec) {
        this.nameSpec = nameSpec;
    }

}
