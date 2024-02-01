package com.example.william.my.module.database.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Keep;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 注意：只支持Java类。如果您更喜欢像Kotlin这样的另一种语言，那么实体类必须仍然是Java。
 * Note: only Java classes are supported. If you prefer another language like Kotlin, your entity classes must still be Java.
 */
@Entity(
        // Define indexes spanning multiple columns here.
        indexes = {
                @Index(value = "text, date DESC", unique = true)
        }
)
public class GreendaoNote {

    @Id(autoincrement = true) // 自增长
    private Long id;

    private String text;

    private Date date;

    @Keep()
    public GreendaoNote() {

    }

    @Keep()
    public GreendaoNote(String text) {
        this.text = text;
        this.date = new Date();
    }

    @Keep()
    public GreendaoNote(Long id, String text, Date date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
