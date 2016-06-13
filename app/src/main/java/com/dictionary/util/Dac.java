package com.dictionary.util;


import android.content.Context;
import android.util.Log;

import com.dictionary.Model.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Dac {
    private static List<Word> lstword = new ArrayList();
    private static List<String> strlstword = new ArrayList();
    static Context mContext;
    private static DB_helper dbhelper;
    private static Dac dac;

    public Dac instance(Context c) {
        mContext = c;
        if (dac == null) {

            dac = new Dac();
        }
        return dac;
    }

    public static List<Word> getLikelyWords(Word o, String TableName) {
        DB_helper db = new DB_helper(mContext);
        String sql = "SELECT word,desc,type,IsFavourite FROM " + TableName + " WHERE word LIKE '" + o.getWord().toString() + "%'";
        Log.i("after query", "after query");
        ArrayList<HashMap<String, String>> alist = null;
        List<Word> wordlist = new ArrayList<>();
        alist = db.getDataTable(sql);
        Log.i("alist", String.valueOf(alist.size()));
        for (int i = 0; i < alist.size(); i++) {
            HashMap tablerow = (HashMap) alist.get(i);
            Word w = new Word();
            w.setWord(tablerow.get("word").toString().toLowerCase());
            w.setDesc(tablerow.get("desc").toString().toLowerCase());
            w.setWordType(tablerow.get("type").toString().toLowerCase());
            w.setFav(tablerow.get("IsFavourite").toString().toLowerCase());
            wordlist.add(w);
        }
        return wordlist;
    }

    public boolean InsertWord(Word o, String TableName) {

        DB_helper db = new DB_helper(mContext);
        String sql = "INSERT INTO " + TableName + " (word,type,desc) values ('" + o.getWord().toLowerCase() + "','" + o.getWordType() + "','" + o.getDesc() + "');";
        try {
            //db.(sql);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean UpdateWord(Word o, String TableName) {

        DB_helper db = new DB_helper(mContext);
        String sql = "UPDATE  " + TableName + " SET word='" + o.getWord().toLowerCase() + "',type='" + o.getWordType() + "',desc='" + o.getDesc() + "'WHERE word='" + o.getWord() + "';";
        try {
            //db.Excute(sql);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean UpdateFav(Word o, String TableName, String fav) {

        DB_helper db = new DB_helper(mContext);
        String sql = "UPDATE " + TableName + " SET IsFavourite = '" + fav + "' WHERE word = '" + o.getWord() + "';";
        Log.e("sqlLog", sql);
        try {
            db.executeQuery(sql);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean DeleteWord(Word o, String TableName) {

        DB_helper db = new DB_helper(mContext);
        String sql = "DELETE FROM " + TableName + " WHERE word='" + o.getWord() + "';";
        try {
            //db(sql);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public Word getByWord(String inputWord) {
        Character firstCharacter;
        ArrayList aList;
        DB_helper db = new DB_helper(mContext);
        firstCharacter = inputWord.charAt(0);
        ComFunction com = new ComFunction();
        String TableName = com.gettableName(firstCharacter);
        String sql = "SELECT * FROM " + TableName + " where word = '" + inputWord + "'";
        aList = db.getDataRow(sql);
        HashMap tableRow = (HashMap) aList.get(0);
        Word w = new Word();
        if (tableRow.size() != 0) {
            w.setWord(tableRow.get("word").toString().toLowerCase());
            w.setDesc(tableRow.get("desc").toString());
            w.setWordType(tableRow.get("type").toString().toLowerCase());
            w.setFav(tableRow.get("IsFavourite").toString().toLowerCase());
            return w;
        } else {
            return w;
        }
    }
}
