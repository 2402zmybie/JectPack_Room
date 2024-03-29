package com.hzhztech.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao   // Database access object
public interface WordDao {

    @Insert
    void insertWords(Word... words);

    @Update
    void updateWords(Word... words);

    @Delete
    void deleteWords(Word... words);

    @Query("DELETE FROM WORD")
    void deleteAllWords();

    @Query("SELECT * FROM WORD ORDER BY ID DESC")
    List<Word> getAllWords();

    @Query("SELECT * FROM WORD ORDER BY ID DESC")
    LiveData<List<Word>> getAllWordsWithLiveData();

    @Query("SELECT * FROM word WHERE english_word LIKE :patten ORDER BY ID DESC")
    LiveData<List<Word>> findWordsWithPatten(String patten);

}
