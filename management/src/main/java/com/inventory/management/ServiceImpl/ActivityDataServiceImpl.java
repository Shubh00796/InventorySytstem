package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.ActivityDataDTO;
import com.inventory.management.Mapper.ActivityDataMapper;
import com.inventory.management.Model.ActivityData;
import com.inventory.management.ReposiotryServices.ActivityTrackingReposiotryService;
import com.inventory.management.ValidatorLogics.ActivtyTrackingPayloadProcessor;
import com.inventory.management.service.ActivityDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityDataServiceImpl implements ActivityDataService {
    private final ActivityDataMapper mapper;
    private final ActivityTrackingReposiotryService reposiotryService;
    private final ActivtyTrackingPayloadProcessor payloadProcessor;


    @Override
    public List<ActivityDataDTO> getAllActivityData() {
        return List.of();
    }

    @Override
    public ActivityDataDTO getActivityDataById(Long id) {
        return null;
    }

    @Override
    public ActivityDataDTO createActivityData(String source, String payload) {
        ActivityData activityData = payloadProcessor.process(source, payload);
        ActivityData savedActivityData = reposiotryService.saveActivity(activityData);

        return mapper.toDTO(savedActivityData);
    }

    @Override
    public ActivityDataDTO updateActivityData(String deviceId, ActivityDataDTO activityDataDTO) {
        ActivityData updatedActivityData = reposiotryService.updateActivityData(deviceId, activityDataDTO);


        return mapper.toDTO(updatedActivityData);
    }

    @Override
    public void deleteActivityData(Long id) {
        reposiotryService.deleteActivity(id);

    }
}
