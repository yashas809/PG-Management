package com.pgmanagement.application.service;


import com.pgmanagement.application.dao.AdvertisementInfo;

import java.util.List;

public interface IAdvertisementService {

    public boolean add(AdvertisementInfo request);

    public boolean update(long advertisementId, AdvertisementInfo request);

    public List<AdvertisementInfo> getAvailableAdvertisement();

    public List<AdvertisementInfo> getAllAdvertisment();

    public AdvertisementInfo getAdvertisment(long id);
}
