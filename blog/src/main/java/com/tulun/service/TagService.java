package com.tulun.service;

import com.tulun.dao.TagMapper;
import com.tulun.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagMapper tagMapper;

    //获取所有的标签
    public List<Tag> loadAllTag() {
        return tagMapper.selectAllTag();
    }
}
