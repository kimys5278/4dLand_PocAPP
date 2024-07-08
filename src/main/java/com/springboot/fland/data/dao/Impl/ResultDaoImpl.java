package com.springboot.fland.data.dao.Impl;

import com.springboot.fland.data.dao.ResultDao;
import com.springboot.fland.data.entity.Composition;
import com.springboot.fland.data.entity.Result;
import com.springboot.fland.data.repository.CompositionRepository;
import com.springboot.fland.data.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultDaoImpl implements ResultDao {

    private final ResultRepository resultRepository;
    private final CompositionRepository compositionRepository;

    @Override
    public void saveBlock(Result result) {
        resultRepository.save(result);
    }

    @Override
    public void saveComposition(Composition composition) {
        compositionRepository.save(composition);
    }
}
