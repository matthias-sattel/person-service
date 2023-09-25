/*
package org.goafabric.personservice.repository.extensions.config;

import org.springframework.boot.actuate.autoconfigure.data.mongo.MongoHealthContributorAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "spring.profiles.active", havingValue = "jpa", matchIfMissing = true)
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration.class, org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration.class, org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration.class, MongoHealthContributorAutoConfiguration.class})
public class JpaConfiguration {}

 */