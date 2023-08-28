package com.example.william.my.module.opensource.greendao.dao;

import com.example.william.my.module.opensource.greendao.Note;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

public class NoteTest extends AbstractDaoTestLongPk<NoteDao, Note> {

    public NoteTest() {
        super(NoteDao.class);
    }

    @Override
    protected Note createEntity(Long key) {
        Note entity = new Note();
        entity.setId(key);
        return entity;
    }

}
