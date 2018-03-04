package com.example.vuanhlevis.orderfoods.models;

import java.util.List;

/**
 * Created by vuanhlevis on 04/03/2018.
 */

public class Request {
    private String name, phone, address, total;
    private String status;
    private List<Order> listfoods;

    public Request(String name, String phone, String address, String total, List<Order> listfoods) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.total = total;
        this.listfoods = listfoods;
        this.status = "0";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getListfoods() {
        return listfoods;
    }

    public void setListfoods(List<Order> listfoods) {
        this.listfoods = listfoods;
    }

    public Request() {
    }
}
