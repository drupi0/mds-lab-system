package com.gnoxmacroscode.mdsportalapi.interfaces;

import com.gnoxmacroscode.mdsportalapi.model.Patient;

import java.util.UUID;

public interface PatientRecordPartial {
    UUID getId();
    String getCollectionDateTime();
    String getReceivedDateTime();
    Patient getPatient();
    String getOrdered();
    String getOrderingDoctor();
    String getSpecimen();
    String getStatus();
    String getSpecNo();

    Long getCollectionDateTimeLong();
    Long getReceivedDateTimeLong();
}
