package com.level.tech.service;

import com.level.tech.entity.Document;
import com.level.tech.enums.EntityType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {

    List<Document> saveDocuments(List<MultipartFile> documents, EntityType type, Long entityId);

    Document saveDocument(String documentName, String documentNumber, String name, MultipartFile document, EntityType type, Long userId);

    byte[] downloadDocument(EntityType type, Long userId, String documentName);

    String getDocumentExtension(String documentName);

    void deleteByDocumentName(String name);

    void deleteByDocumentId(Long documentId);

    void deleteByDocumentIds(List<Long> documentIds);

}
