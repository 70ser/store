package com.sp.store.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Result<T> {
    private String code;
    private String msg;
    @NonNull
    private T data;
    public static Result<?> success() {
        Result<Object> result = new Result<>();
        result.setCode("0");
        result.setMsg("success");
        return result;
    }
    public static <T> Result<?> success(T data) {
        Result<T> result = new Result<>(data);
        result.setCode("0");
        result.setMsg("success");
        return result;
    }
    public static Result<?> error(String code,String msg) {
        Result<Object> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
