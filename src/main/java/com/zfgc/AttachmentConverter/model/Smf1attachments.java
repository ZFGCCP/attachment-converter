package com.zfgc.AttachmentConverter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name="smf_1attachments")
@Entity
public class Smf1attachments {

	@Id @Column private Integer idAttach;
	@Column private String filename;
	@Column private String fileHash;
	@Column private String fileext;
	
}
