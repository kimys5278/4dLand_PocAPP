package com.springboot.fland.data.dao.Impl;

import com.springboot.fland.data.dao.ResultDao;
import com.springboot.fland.data.entity.Result;
import com.springboot.fland.data.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultDaoImpl implements ResultDao {
    @Autowired
    private ResultRepository resultRepository;

    @Override
    public void save(Result result) {
        resultRepository.save(result);
    }
}
