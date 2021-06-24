package com.kwetter.frits.timelineservice.interfaces;

import org.springframework.validation.annotation.Validated;

@Validated
public interface NotifyLogic {
    void notifyFollowers(String username);
}
