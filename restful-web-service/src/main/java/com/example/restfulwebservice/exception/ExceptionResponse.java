package com.example.restfulwebservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
// 모든 로직에 대한 에러 핸들러 클래스
public class ExceptionResponse {

    private Date timeStamp;
    private String msg;
    private String detail;



}
