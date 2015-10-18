/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cz4031.entity;

/**
 *
 * @author GuoLong
 */
public class Authored {
    private int authorId;
    private int pubID;
    private String pubKey;

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int id) {
        this.authorId = id;
    }

    public int getPubID() {
        return pubID;
    }

    public void setPubID(int pubID) {
        this.pubID = pubID;
    }
    
    public String getPubKey() {
        return pubKey;
    }
}
