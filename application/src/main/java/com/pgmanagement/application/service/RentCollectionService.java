package com.pgmanagement.application.service;

import com.pgmanagement.application.dao.Rent;

import java.util.List;


public interface RentCollectionService
{
    public void collectRent(Rent request);

    public void addRentReminder();

    public List<Rent> viewRent(long appUserFK);

    public List<Rent> viewAllRent();
}
