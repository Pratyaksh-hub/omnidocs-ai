package com.pratyaksh.omnidocs_ai.subscription.policy;

import com.pratyaksh.omnidocs_ai.subscription.entity.SubscriptionPlan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UploadPolicyFactory {

  private final FreeUploadPolicy freeUploadPolicy;

  private final PlusUploadPolicy plusUploadPolicy;

  private final PremiumUploadPolicy premiumUploadPolicy;

  public UploadPolicy getPolicy(
      SubscriptionPlan plan
  ) {

    return switch (plan) {

      case FREE ->
          freeUploadPolicy;

      case PLUS ->
          plusUploadPolicy;

      case PREMIUM,
           ENTERPRISE ->
          premiumUploadPolicy;
    };
  }

}