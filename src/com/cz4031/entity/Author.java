/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cz4031.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Author {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((name == null) ? 0 : name.hashCode());
//        return result;
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
                // if deriving: appendSuper(super.hashCode()).
                append(name).
                toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        
//        final Author other = (Author)obj;
//        if (other.name == null) {
//            return false;
//        }
//        else if (!name.equals(other.name)) {
//            return false;
//        }
//        return true;
        if (!(obj instanceof Author)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Author rhs = (Author) obj;
        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                append(name, rhs.name).
                isEquals();
    }
}
