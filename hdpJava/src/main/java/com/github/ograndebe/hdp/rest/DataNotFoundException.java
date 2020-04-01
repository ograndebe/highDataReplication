package com.github.ograndebe.hdp.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Page/Table not found in memory")
public class DataNotFoundException extends Exception{
}
