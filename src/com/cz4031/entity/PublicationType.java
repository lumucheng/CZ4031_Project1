/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cz4031.entity;

/**
 *
 * @author Mucheng
 */
public enum PublicationType {
    ARTICLE(1), BOOK(2), INCOLLECTION(3), INPROCEEDINGS(4);
    
    private final int value;
    
    private PublicationType(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    public static PublicationType getType(int value) {
        
        if (value == 1) {
            return ARTICLE;
        }
        else if (value == 2) {
            return BOOK;
        }
        else if (value == 3) {
            return INCOLLECTION;
        }
        else {
            return INPROCEEDINGS;
        }
    }
}
