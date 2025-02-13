package com.inventory.management.ReposiotryServices;

import com.inventory.management.Dtos.ActivityDataDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.ActivityData;
import com.inventory.management.Repo.ActivityDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ActivityTrackingReposiotryService {
    private final ActivityDataRepository activityDataRepository;


    public List<ActivityData> getAllActivites() {
        return activityDataRepository.findAll();
    }

    public Optional<ActivityData> getActityByDeviceId(String deviceId) {
        return Optional.ofNullable(activityDataRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device id not found for the given activity" + deviceId)));
    }

    public ActivityData saveActivity(ActivityData activityData) {
        return activityDataRepository.save(activityData);
    }

    public ActivityData updateActivityData(String deviceId, ActivityDataDTO activityDataDTO) {
        ActivityData activityData = activityDataRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device id not Found for given Activity" + deviceId));

        Optional.ofNullable(activityDataDTO.getSteps()).ifPresent(activityData::setSteps);
        Optional.ofNullable(activityDataDTO.getSource()).ifPresent(activityData::setSource);

        return activityDataRepository.save(activityData);
    }

    public void deleteActivity(Long id) {
        ActivityData activityData = activityDataRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id not found"));

        activityDataRepository.delete(activityData);
    }
}
