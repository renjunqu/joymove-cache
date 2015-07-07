package com.futuremove.cacheServer.entity;

import java.util.Date;

/**
 * Created by qurj on 15/7/6.
 */
public class CarDynProps  extends Base {

    public static int state_free = 0;
    public static int state_reserved  = 1;
    public static int state_busy = 2;
    public static int state_wait_sendcode = 3;
    //its used to two-phase commit
    public static int state_reserve_pending = 4;
    //等待开火
    public static int state_wait_poweron = 5;
    //等待关火
    public  static int state_wait_poweroff = 6;
    //等待锁车成功
    public static int state_wait_lock = 7;

    public static int state_wait_clearcode = 8;

    public String      vinNum;
    //need "2dsphere" index
    public CarLocation location;
    public Double      powerPercent;
    public Double      mileage;
    public Integer     acc;
    public Integer     lockState;
    public Integer     state;
    //车牌号
    public String      licenseNum;
    public String      owner;
    public Date        dataUpdateTime;
    public Date        stateUpdateTime;
}
