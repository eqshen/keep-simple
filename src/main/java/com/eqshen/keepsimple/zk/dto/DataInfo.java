package com.eqshen.keepsimple.zk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author eqshen
 * @description
 * @date 2021/2/3
 */
@Data
@AllArgsConstructor
public class DataInfo {
    private int version;
    private String data;
}
