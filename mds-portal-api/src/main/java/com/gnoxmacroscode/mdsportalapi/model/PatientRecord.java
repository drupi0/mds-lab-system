package com.gnoxmacroscode.mdsportalapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gnoxmacroscode.mdsportalapi.interfaces.EncryptedData;
import com.gnoxmacroscode.mdsportalapi.interfaces.RecordMode;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "PatientRecord")
public class PatientRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@OneToOne
	private Patient patient;

	@OneToOne
	private Staff pathologist;

	@OneToOne
	private Staff performedBy;

	@OneToOne
	private Staff verifiedBy;

	@Lob
	@EncryptedData
	@Convert(converter = DataEncryptor.class)
	@Column(name = "data")
	private String data;

	@Column(name = "comments", columnDefinition="text")
	private String comments;

	private String specNo;
	private String accessionNo;
	private String orderingDoctor;
	private String status;
	private String specimen;
	private String ordered;

	private String collectionDateTime;
	private String receivedDateTime;

	private Long collectionDateTimeLong;
	private Long receivedDateTimeLong;
	private RecordMode mode;

	@Transient
	@JsonIgnore
	private String seed;


	@PostLoad()
	public void formatDateTimes() {
		try {
			if(!this.collectionDateTime.isEmpty()) {
				this.collectionDateTimeLong = dateParser(this.collectionDateTime).getTime();
			}

			if(!this.receivedDateTime.isEmpty()) {
				this.receivedDateTimeLong = dateParser(this.receivedDateTime).getTime();
			}


		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@PrePersist()
	public void setDateTimesAsLong() {
		try {
			if(!this.collectionDateTime.isEmpty()) {
				this.setCollectionDateTimeLong(dateParser(this.collectionDateTime).getTime());
			}

			if(!this.receivedDateTime.isEmpty()) {
				this.setReceivedDateTimeLong(dateParser(this.receivedDateTime).getTime());
			}


		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	Date dateParser(String dateString) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		return formatter.parse(dateString);
	}
}