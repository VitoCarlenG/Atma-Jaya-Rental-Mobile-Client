package com.vitocarlengiovanni.ajr.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerResponse {
    private String message;

    @SerializedName("customer")
    private List<Customer> customerList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }
}
