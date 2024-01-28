package com.gnoxmacroscode.mdsportalapi.model;

import com.gnoxmacroscode.mdsportalapi.utils.Encryption;
import jakarta.persistence.AttributeConverter;

public class DataEncryptor implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return null;
        }

        try {
            return Encryption.encrypt(attribute);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        try {
            return Encryption.decrypt(dbData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
