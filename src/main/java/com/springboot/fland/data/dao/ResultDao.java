package com.springboot.fland.data.dao;

import com.springboot.fland.data.entity.Composition;
import com.springboot.fland.data.entity.Result;

public interface ResultDao {
    void saveBlock(Result result);
    void saveComposition(Composition composition);
}
