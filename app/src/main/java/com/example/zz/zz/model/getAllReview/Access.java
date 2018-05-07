
package com.example.zz.zz.model.getAllReview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Access {

    @SerializedName("idAccess")
    @Expose
    private Integer idAccess;
    @SerializedName("nameAccess")
    @Expose
    private String nameAccess;

    public Integer getIdAccess() {
        return idAccess;
    }

    public void setIdAccess(Integer idAccess) {
        this.idAccess = idAccess;
    }

    public String getNameAccess() {
        return nameAccess;
    }

    public void setNameAccess(String nameAccess) {
        this.nameAccess = nameAccess;
    }

}
