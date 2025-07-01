package com.zfgc.AttachmentConverter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zfgc.AttachmentConverter.model.Smf1attachments;

public interface Smf1attachmentsRepo extends JpaRepository<Smf1attachments, Integer> {
	Smf1attachments findByFileHashEquals(String hash);
}
