package com.sp.store.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sp.store.common.Result;
import com.sp.store.entity.Book;
import com.sp.store.mapper.BookMapper;
import com.sp.store.service.IBookService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/book")
@CrossOrigin
public class BookController {

    @Resource
    private IBookService bookService;

    @Resource
    private BookMapper bookMapper;
    @PostMapping
    public Result<?> save(@RequestBody Book book) {
        bookService.save(book);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody Book book) {
        bookService.updateById(book);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        bookService.removeById(id);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result<?> getDetail(@PathVariable Integer id) {
        return Result.success(bookService.getById(id));
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNumber, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Book> wrapper = Wrappers.lambdaQuery();
        if (search != null && search.length() > 0) {
            wrapper.like(Book::getName, search).or().like(Book::getAuthor,search).or().like(Book::getPress,search).or().like(Book::getIsbn,search)
                    .or().eq(Book::getId,search);
        }
        wrapper.orderByAsc(Book::getId);
        Page<Book> bookPage=bookService.page(new Page<>(pageNumber, pageSize), wrapper);
        return Result.success(bookPage);
    }
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Book> list = bookService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        //    private String isbn;
        //    private String name;
        //    private String author;
        //    private String press;
        //    private String description;
        //    private Integer stock;
        //    private BigDecimal price;
        //    private String imageUrl;
        writer.addHeaderAlias("isbn", "isbn");
        writer.addHeaderAlias("name", "name");
        writer.addHeaderAlias("author","author");
        writer.addHeaderAlias("press","press");
        writer.addHeaderAlias("description","description");
        writer.addHeaderAlias("stock","stock");
        writer.addHeaderAlias("price","price");
        writer.addHeaderAlias("imageUrl","imageUrl");
        writer.setOnlyAlias(true);
        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);
        writer.removeHeaderAlias("id");
        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("书籍信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");


//        response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("书籍信息", "UTF-8")+".xlsx");
//        response.setContentType("application/octet-stream");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    @PostMapping("/import")
    public Result<?> imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        //通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        List<Book> books = reader.readAll(Book.class);
        bookService.saveAll(books);
        return Result.success(true);
    }
    @GetMapping("/random")
    public Result<?> random(@RequestParam(defaultValue = "32") Integer size){
        return Result.success(bookMapper.getRandom(size));
    }
    @GetMapping("/search")
    public Result<?> searchBook( @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Book> wrapper = Wrappers.lambdaQuery();
        if (search != null && search.length() > 0) {
            wrapper.like(Book::getName, search).or().like(Book::getAuthor,search).or().like(Book::getPress,search).or().like(Book::getIsbn,search)
                    .or().eq(Book::getId,search);
        }
        wrapper.orderByAsc(Book::getId);
        List<Book> list=bookService.list(wrapper);
        return Result.success(list);
    }
}
