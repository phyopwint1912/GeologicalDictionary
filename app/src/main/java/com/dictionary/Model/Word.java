package com.dictionary.Model;

public class Word {
    private String word;

    public Word() {

    }

    /**
     * Constructor method to define Word with three params
     *
     * @param w    String word to define
     * @param type String type of word to define
     * @param desc String description of word to define
     */
    public Word(String w, String type, String desc, String fav) {
        this.word = w;
        this.wordType = type;
        this.desc = desc;
        this.fav = fav;
    }

    public Word(String w) {
        this.word = w;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }


    private String fav;

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }


    private String wordType;

    public String getWordType() {
        return wordType;
    }

    public void setWordType(String wordType) {
        this.wordType = wordType;
    }

    private String phonetic;

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
