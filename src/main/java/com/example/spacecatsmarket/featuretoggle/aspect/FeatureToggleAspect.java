package com.example.spacecatsmarket.featuretoggle.aspect;

import com.example.spacecatsmarket.config.FeatureToggleProperties;
import com.example.spacecatsmarket.exception.FeatureNotAvailableException;
import com.example.spacecatsmarket.service.featuretoggle.FeatureToggleService;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import com.example.spacecatsmarket.featuretoggle.annotation.FeatureToggle;
import com.example.spacecatsmarket.featuretoggle.FeatureToggles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.aspectj.lang.ProceedingJoinPoint;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {

    private final FeatureToggleProperties toggleProps;


    @Around(value = "@annotation(featureToggle)")
    public Object checkFeatureToggleAnnotation(ProceedingJoinPoint joinPoint, FeatureToggle featureToggle) throws Throwable {
        return checkToggle(joinPoint, featureToggle);
    }

    private Object checkToggle(ProceedingJoinPoint joinPoint, FeatureToggle featureToggle) throws Throwable {
        FeatureToggles toggle = featureToggle.value();
        if (toggleProps.check(toggle.getFeatureName())) {
            return joinPoint.proceed();
        }

        log.warn("Feature toggle {} is not enabled!", toggle.getFeatureName());
        throw new FeatureNotAvailableException(toggle.getFeatureName());
    }

}