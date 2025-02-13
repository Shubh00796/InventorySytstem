package com.inventory.management.service;

import com.inventory.management.Dtos.ActivityDataDTO;
import com.inventory.management.Model.ActivityData;

import java.util.List;

public interface ActivityDataService {
    List<ActivityDataDTO> getAllActivityData();
    ActivityDataDTO getActivityDataById(Long id);
    ActivityDataDTO createActivityData(String source , String payload);
    ActivityDataDTO updateActivityData(String deviceId, ActivityDataDTO activityDataDTO);
    void deleteActivityData(Long id);
}