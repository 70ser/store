package com.sp.store.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.store.entity.File;
import com.sp.store.mapper.FileMapper;
import com.sp.store.service.IFileService;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {
}
