package org.goafabric.personservice.persistence.encryption;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.hibernate5.encryptor.HibernatePBEEncryptorRegistry;
import org.jasypt.iv.IvGenerator;
import org.jasypt.iv.RandomIvGenerator;
import org.jasypt.salt.RandomSaltGenerator;
import org.jasypt.salt.SaltGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.nativex.hint.TypeAccess;
import org.springframework.nativex.hint.TypeHint;

import java.util.UUID;

@Slf4j
@Configuration
@TypeHint(types = {java.text.Normalizer.class, java.text.Normalizer.Form.class}, access = {TypeAccess.DECLARED_CLASSES, TypeAccess.DECLARED_CONSTRUCTORS, TypeAccess.DECLARED_METHODS, TypeAccess.DECLARED_FIELDS})
@TypeHint(types = org.jasypt.hibernate5.type.EncryptedStringType.class, access = {TypeAccess.DECLARED_CLASSES, TypeAccess.DECLARED_CONSTRUCTORS, TypeAccess.DECLARED_METHODS, TypeAccess.DECLARED_FIELDS})
public class EncryptionConfiguration {
    @Value("${security.encryption.key:}")
    String encryptionKey;

    @Bean
    public StandardPBEStringEncryptor hibernateEncryptor() {
        final StandardPBEStringEncryptor encryptor =
                getAES256Encryptor(getEncryptionKey(), new RandomIvGenerator(), new RandomSaltGenerator());

        HibernatePBEEncryptorRegistry.getInstance()
                .registerPBEStringEncryptor("hibernateStringEncryptor", encryptor);

        return encryptor;
    }

    private StandardPBEStringEncryptor getAES256Encryptor(String configKey, IvGenerator ivGenerator, SaltGenerator saltGenerator) {
        final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        encryptor.setIvGenerator(ivGenerator);
        encryptor.setSaltGenerator(saltGenerator);
        encryptor.setPassword(configKey);
        return encryptor;
    }

    private String getEncryptionKey() {
        if ("".equals(encryptionKey)) {
            log.warn("security.encryption.key is empty, generating one for temporary usage");
        }
        return "".equals(encryptionKey) ? UUID.randomUUID().toString() : encryptionKey;
    }

}
