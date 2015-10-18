/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cz4031;

import com.cz4031.entity.Author;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Mucheng
 */
public class AuthorList {

    private static final AuthorList singleton = new AuthorList();
    private HashMap<Author, Set<Integer>> authorList = null;

    private AuthorList() {
        authorList = new HashMap<>();
    }

    public static AuthorList getInstance() {
        return singleton;
    }

    protected HashMap<Author, Set<Integer>> getAuthorList() {
        return authorList;
    }
}
